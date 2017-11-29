package zn2.ft.aj.cheepot;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import zn2.ft.aj.cheepot.data.MD5;
import zn2.ft.aj.cheepot.data.User;

import static android.app.DatePickerDialog.*;
import static android.graphics.Color.TRANSPARENT;

public class SignUpActivity extends Activity implements OnClickListener {
    private FirebaseAuth mAuth;
    private Button buttonRegister;
    private EditText editName;
    private EditText editFamilyName;
    private TextView dateDeNaissance;
    private ImageButton calendar;
    private OnDateSetListener DateSetListener;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editRePassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private int year;
    private int month;
    private int day;
    private Spinner spinner;
    private int gender;
    private TextView textViewConditions;
    private String[] plants = new String[]{
            "Sexe", "Homme", "Femme"
    };
    Dialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editName = (EditText) findViewById(R.id.editName);
        editFamilyName = (EditText) findViewById(R.id.editFamilyName);
        spinner = (Spinner) findViewById(R.id.spinner);
        dateDeNaissance = (TextView) findViewById(R.id.date);
        calendar = (ImageButton) findViewById(R.id.calendar);
        calendar.setOnClickListener(this);
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                dateDeNaissance.setText(String.format("Date de naissance: %d/%d/%d", dd, mm + 1, yy));
                year = yy;
                month = mm + 1;
                day = dd;
            }
        };

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editRePassword = (EditText) findViewById(R.id.editRePassword);

        textViewConditions = (TextView) findViewById(R.id.textViewConditions);
        textViewConditions.setOnClickListener(this);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        year = 0;

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == calendar) {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    SignUpActivity.this,
                    android.R.style.Theme_DeviceDefault_Dialog_MinWidth, DateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } else if (view == buttonRegister) {
            registerUser();
        } else if (view == textViewConditions) {

            termsDialg();

        } else if (view == textViewSignin) {
            Intent goToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(goToLogin);
            finish();
        }
    }

    private void termsDialg() {
        dialog = new Dialog(SignUpActivity.this);
        dialog.setContentView(R.layout.dialog_terms);
        dialog.show();
    }

    private void registerUser() {
        final String name = editName.getText().toString().trim();
        final String familyName = editFamilyName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        final String password;

        try {
            password = MD5.crypt(editPassword.getText().toString());
        } catch (Exception e) {
            Toast.makeText(SignUpActivity.this, "Erreur de sécurité, Réessayez", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!valideRegister()) {
            return;
        }
        progressDialog.setMessage("Tic Tac... Tic Tac ...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "registered successfully", Toast.LENGTH_SHORT).show();
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users");
                            DatabaseReference currentUserDb = myRef.child(mAuth.getCurrentUser().getUid());
                            User user = new User(name, familyName, String.format("%d/%d/%d", day, month, year), password, plants[gender]);
                            currentUserDb.setValue(user);
                            Intent homeIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(homeIntent);
                            finish();

                        } else {
                            Toast.makeText(SignUpActivity.this, "Vous etes déjà inscrit", Toast.LENGTH_SHORT).show();
                        }
                    }


                });

    }

    private boolean valideRegister() {
        String name = editName.getText().toString().trim();
        String familyName = editFamilyName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String Repassword = editRePassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Entrez votre nom SVP", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(familyName)) {
            Toast.makeText(this, "Entrez votre prenom SVP", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender == 0) {
            Toast.makeText(this, "Précisez votre sexe", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (year == 0) {
            Toast.makeText(this, "Entrez votre date de naissance SVP", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Entrez votre mail SVP", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Entrez un mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "mot de passe trés court,  enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Repassword.equals(password)) {
            Toast.makeText(this, "Veuillez réecrire votre mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}




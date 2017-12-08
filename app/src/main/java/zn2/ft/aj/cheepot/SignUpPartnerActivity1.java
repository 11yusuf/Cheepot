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
import java.util.Date;
import java.util.List;

import zn2.ft.aj.cheepot.data.Partner;

import static android.app.DatePickerDialog.*;
import static android.graphics.Color.TRANSPARENT;

public class SignUpPartnerActivity1 extends Activity implements OnClickListener {
    private EditText editName;
    private EditText editFamilyName;
    private TextView dateDeNaissance;
    private ImageButton calendar;
    private OnDateSetListener DateSetListener;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editRePassword;
    private TextView textViewSignin;
    private Button buttonSuivant;
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
        setContentView(R.layout.activity_sign_up_partner1);
        buttonSuivant = (Button) findViewById(R.id.buttonSuivant);
        editName = (EditText) findViewById(R.id.editNamePartner);
        editFamilyName = (EditText) findViewById(R.id.editFamilyNamePartner);
        spinner = (Spinner) findViewById(R.id.spinnerPartner);
        dateDeNaissance = (TextView) findViewById(R.id.datePartner);
        calendar = (ImageButton) findViewById(R.id.calendarPartner);
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
        editEmail = (EditText) findViewById(R.id.editEmailPartner);
        editPassword = (EditText) findViewById(R.id.editPasswordPartner);
        editRePassword = (EditText) findViewById(R.id.editRePasswordPartner);
        textViewSignin = (TextView) findViewById(R.id.textViewSignInPartner);

        buttonSuivant.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
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

    /*  @Override
     public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }*/

    @Override
    public void onClick(View view) {
        Intent nextIntent;

        if (view == calendar) {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    SignUpPartnerActivity1.this,
                    android.R.style.Theme_DeviceDefault_Dialog_MinWidth, DateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } else if (view == textViewConditions) {

            termsDialg();

        } else if (view == textViewSignin) {
            Intent goToLogin = new Intent(SignUpPartnerActivity1.this, LoginActivity.class);
            startActivity(goToLogin);
            finish();
        }
        if (view == buttonSuivant) {
            // sign up partenaire
            if (validSuivant()) {
                Partner partner = new Partner();
                nextIntent = new Intent(SignUpPartnerActivity1.this, SignUpPartnerActivity2.class);
                nextIntent.putExtra("partner", partner);
                nextIntent.putExtra("email",editEmail.getText().toString());
                nextIntent.putExtra("password",editPassword.getText().toString());
                startActivity(nextIntent);
                finish();
            }
        }
    }

    private void termsDialg() {
        dialog = new Dialog(SignUpPartnerActivity1.this);
        dialog.setContentView(R.layout.dialog_terms);
        dialog.show();
    }


    private boolean validSuivant() {
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
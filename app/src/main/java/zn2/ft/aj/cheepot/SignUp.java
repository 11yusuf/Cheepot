package zn2.ft.aj.cheepot;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import static android.app.DatePickerDialog.*;
import static android.graphics.Color.TRANSPARENT;

public class SignUp extends AppCompatActivity implements  OnClickListener {

    private FirebaseAuth mAuth;
    private Button buttonRegister;
    private EditText editName ;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        year = 0;
        setContentView(R.layout.activity_sign_up);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editName = (EditText) findViewById(R.id.editName);
        editFamilyName = (EditText) findViewById(R.id.editFamilyName);
        dateDeNaissance = (TextView) findViewById(R.id.date);
        calendar = (ImageButton) findViewById(R.id.calendar) ;
        calendar.setOnClickListener(this);

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateDeNaissance.setText(String.format("%d/%d/%d", day, month, year));
            }
        };

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editRePassword = (EditText) findViewById(R.id.editRePassword);




        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }*/

    @Override
    public void onClick(View view) {
        if (view == calendar) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    SignUp.this,
            android.R.style.Theme_DeviceDefault_Dialog_MinWidth, DateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }


        else if (view == buttonRegister) {
            registerUser();
        } else if (view == textViewSignin) {
            // will open login activity
        }
    }



    private void registerUser() {

        final String name =  editName.getText().toString().trim();
        final String familyName = editFamilyName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();

        if (!valideRegister()){
            return;
        }
        progressDialog.setMessage("Tic Tac... Tic Tac ...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "registered successfully", Toast.LENGTH_SHORT).show();
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users");
                            DatabaseReference currentUserDb = myRef.child(mAuth.getCurrentUser().getUid());
                            currentUserDb.child("name").setValue(name);
                            currentUserDb.child("family name").setValue(familyName);
                            currentUserDb.child("dateDeNaissance").setValue(dateDeNaissance.getText().toString());
                            currentUserDb.child("password").setValue(password);
                            Intent homeIntent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "Vous etes déjà inscrit", Toast.LENGTH_SHORT).show();
                        }
                    }


                });

    }

    private boolean valideRegister(){
        String name =  editName.getText().toString().trim();
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

        if ( year == 0 ){
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
        if ( !Repassword.equals(password)) {
            Toast.makeText(this, "Veuillez réecrire votre mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}




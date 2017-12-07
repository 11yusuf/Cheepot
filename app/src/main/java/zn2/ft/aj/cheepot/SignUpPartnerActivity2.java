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
import zn2.ft.aj.cheepot.data.Partner;
import zn2.ft.aj.cheepot.data.User;

import static android.app.DatePickerDialog.*;
import static android.graphics.Color.TRANSPARENT;

public class SignUpPartnerActivity2 extends Activity implements OnClickListener {
    private FirebaseAuth mAuth;
    private Button buttonRegister;
    private EditText editFirmName;
    private EditText editMatricule;
    private EditText editCompteBancaire;
    private Partner partner;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up_partner2);
        mAuth = FirebaseAuth.getInstance();
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editFirmName = (EditText) findViewById(R.id.editFirmName);
        editMatricule = (EditText) findViewById(R.id.editMatricule);
        editCompteBancaire = (EditText) findViewById(R.id.editCompteBancaire);
        partner = (Partner) getIntent().getSerializableExtra("partner");
        buttonRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }
    }


    private void registerUser() {
        final String FirmName = editFirmName.getText().toString().trim();
        final String Matricule = editMatricule.getText().toString().trim();
        final String CompteBancaire = editCompteBancaire.getText().toString().trim();
        String email = getIntent().getStringExtra("email");
        final String password;

        try {
            password = MD5.crypt(getIntent().getStringExtra("password"));
        } catch (Exception e) {
            Toast.makeText(SignUpPartnerActivity2.this, "Erreur de sécurité, Réessayez", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SignUpPartnerActivity2.this, "registered successfully", Toast.LENGTH_SHORT).show();
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("partners");
                            DatabaseReference currentUserDb = myRef.child(mAuth.getCurrentUser().getUid()).child("patnerInfo");
                            partner.setter(FirmName, Matricule, CompteBancaire);
                            currentUserDb.setValue(partner);
                            Intent homeIntent = new Intent(SignUpPartnerActivity2.this, LoginActivity.class);
                            startActivity(homeIntent);
                            finish();

                        } else {
                            Toast.makeText(SignUpPartnerActivity2.this, "Vous etes déjà inscrit", Toast.LENGTH_SHORT).show();
                        }
                    }


                });

    }

    private boolean valideRegister() {
        String FirmName = editFirmName.getText().toString().trim();
        String Matricule = editMatricule.getText().toString().trim();
        String CompteBancaire = editCompteBancaire.getText().toString().trim();

        if (TextUtils.isEmpty(FirmName)) {
            Toast.makeText(this, "Entrez votre nom d'entreprise SVP", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(Matricule)) {
            Toast.makeText(this, "Entrez votre Matricule SVP", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(CompteBancaire)) {
            Toast.makeText(this, "Entrez votre numéro compte bancaire SVP", Toast.LENGTH_SHORT).show();
            return false;
        } else

            return true;

    }
}




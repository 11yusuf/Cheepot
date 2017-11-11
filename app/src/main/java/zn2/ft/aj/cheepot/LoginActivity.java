package zn2.ft.aj.cheepot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editEmail;
    private EditText editPassword;
    private TextView textViewMotDePasseOublie;
    private Button buttonSeConnecter;
    private TextView textViewCreerCompte;
    private TextView textViewIci;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        buttonSeConnecter = (Button) findViewById(R.id.buttonSeConnecter);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textViewMotDePasseOublie = (TextView) findViewById(R.id.textViewMotDePasseOublie);
        textViewCreerCompte = (TextView) findViewById(R.id.textViewCreerCompte);
        textViewIci = (TextView) findViewById(R.id.textViewIci);

        textViewMotDePasseOublie.setOnClickListener(this);
        buttonSeConnecter.setOnClickListener(this);
        textViewCreerCompte.setOnClickListener(this);
        textViewIci.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }
    private void userLogin(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Entrez votre email SVP", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Entrez votre mot de passe SVP", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Veuillez vous patientez ...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            // move to profile
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        Intent nextIntent;

        if (view == buttonSeConnecter) {
            userLogin();
        }
        if (view == textViewMotDePasseOublie) {
            // will open mot de passe recovery
        }
        if (view == textViewCreerCompte) {
            nextIntent = new Intent(LoginActivity.this, SignUp.class);
            finish();
            startActivity(nextIntent);

        }
        if (view == textViewIci) {
            // sign up partenaire
        }
    }

}
package zn2.ft.aj.cheepot;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import zn2.ft.aj.cheepot.data.User;

public class ChargerCompteActivity extends Activity implements OnClickListener {
    private FirebaseAuth mAuth;
    private Button buttonCharger;
    private EditText editAmmount;
    private EditText editCardNumber;
    private EditText editInternetCode;
    DatabaseReference databaseReference ;
    private int money;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger_compte);
        mAuth = FirebaseAuth.getInstance();
        buttonCharger = (Button) findViewById(R.id.buttonCharger);
        editAmmount = (EditText) findViewById(R.id.editAmmount);
        editCardNumber = (EditText) findViewById(R.id.editCardNumber);
        editInternetCode = (EditText) findViewById(R.id.editInternetCode);
        buttonCharger.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == buttonCharger) {
            if (validePayment()) {
                databaseReference = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User tmpuser = (User)dataSnapshot.getValue(User.class);
                        if (tmpuser.password == null){
                            return;
                        }
                       money = Integer.parseInt(editAmmount.getText().toString());
                       tmpuser.addmoney(money);
                       databaseReference.child("userInfo").child("money").setValue(tmpuser.money);
                       Toast.makeText(getApplicationContext(),"Recharge avec succée", Toast.LENGTH_SHORT).show();
                       Intent nextIntent = new Intent(ChargerCompteActivity.this, HomeActivity.class);
                       startActivity(nextIntent);
                       finish();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//
            }
        }
    }

//    (new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            User user = dataSnapshot.getValue(User.class);
//            money = Integer.parseInt(editAmmount.getText().toString());
//            user.addmoney(money);
//            Toast.makeText(getApplicationContext(),user.money,Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    });
    private boolean validePayment() {
        String ammount = editAmmount.getText().toString().trim();
        String cardnaumber = editCardNumber.getText().toString().trim();
        String internetcode = editInternetCode.getText().toString().trim();

        if (TextUtils.isEmpty(ammount)) {
            Toast.makeText(this, "Entrez le montant", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(cardnaumber)) {
            Toast.makeText(this, "Entrez votre numéro de carte", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(internetcode)) {
            Toast.makeText(this, "Entrez le code internet", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

}
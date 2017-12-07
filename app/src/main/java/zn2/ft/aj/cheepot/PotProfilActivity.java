package zn2.ft.aj.cheepot;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;
import com.varunest.sparkbutton.SparkEventListener;

import zn2.ft.aj.cheepot.data.Pot;

public class PotProfilActivity extends AppCompatActivity implements OnClickListener {
    private TextView potName;
    private SparkButton sparkButton;
    private Pot potCreated;
    private FirebaseAuth mAuth;
    private ImageView potProfilBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_profil);
        mAuth = FirebaseAuth.getInstance();
        potName = (TextView)findViewById(R.id.potName);
        sparkButton = (SparkButton)findViewById(R.id.finishPotCreation);
        potCreated = (Pot) getIntent().getSerializableExtra("potCreated");
        potName.setText(potCreated.potName);
        potProfilBackground = (ImageView) findViewById(R.id.potProfilPhoto);
        changeBackground(potCreated.type);
        sparkButton.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    // Button is active
                } else {
                    // Button is inactive
                }
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference currentUserDb = myRef.child("users").child(mAuth.getCurrentUser().getUid()).child("createdPots");
                DatabaseReference potsDb = myRef.child("activePots").push();
                currentUserDb.child(potsDb.getKey()).setValue(potsDb.getKey(),"key");
                potsDb.setValue(potCreated);

            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });
    }

    @Override
    public void onClick(View view) {
   /*     if ( view == sparkButton){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference currentUserDb = myRef.child("users").child(mAuth.getCurrentUser().getUid()).child("createdPots");
            DatabaseReference potsDb = myRef.child("activePots").push();
            currentUserDb.child(potsDb.getKey()).setValue("key", potsDb.getKey());
            potsDb.setValue(potCreated);
        }*/
    }

    private void changeBackground(int position) {
        switch (position) {
            case 0:
                potProfilBackground.setImageResource(R.drawable.anniversaire); //Aniversaire
                break;
            case 1:
                potProfilBackground.setImageResource(R.drawable.weekend2); //Weekèend à plusieurs
                break;
            case 2:
                potProfilBackground.setImageResource(R.drawable.soiree2); //Soirée
                break;
            case 3:
                potProfilBackground.setImageResource(R.drawable.voyage3); //Voyage
                break;
            case 4:
                potProfilBackground.setImageResource(R.drawable.projet); //Projet
                break;
            case 5:
                potProfilBackground.setImageResource(R.drawable.wedding); //Mariage
                break;
            case 6:
                potProfilBackground.setImageResource(R.drawable.naissance); //Naissance
                break;
            case 7:
                potProfilBackground.setImageResource(R.drawable.collocation); //Collocation
                break;
            case 8:
                potProfilBackground.setImageResource(R.drawable.remerciement); //Remerciments
                break;
            case 9:
                potProfilBackground.setImageResource(R.drawable.solidarity3); //Solidarité
                break;
            case 10:
                potProfilBackground.setImageResource(R.drawable.association); //Association
                break;
            case 11:
                potProfilBackground.setImageResource(R.drawable.ic_pomegranate_colored); //Autres
                break;
            default:
                break;
        }
        potProfilBackground.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}


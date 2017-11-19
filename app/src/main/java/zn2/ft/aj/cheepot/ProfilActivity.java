package zn2.ft.aj.cheepot;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends Activity implements View.OnClickListener{
    private TextView profilName;
    private CircleImageView profilPicture;
    private ImageView showMoney;
    private Button notificationButton;
    private Button addMoneyButton;
    private Button createPot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profil);
        profilName = (TextView) findViewById(R.id.profilName);
        profilPicture = (CircleImageView) findViewById(R.id.profile_image);
        addMoneyButton = (Button) findViewById(R.id.addMoneyButton);
        notificationButton = (Button) findViewById(R.id.buttonNotification);
        Bundle bundle = getIntent().getExtras();
        String idProfil = bundle.getString("IdProfil");
        profilName.setText(idProfil);
        createPot = (Button) findViewById(R.id.createPot);
        createPot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent goTo;
        if (view == profilPicture ){
            Toast.makeText(ProfilActivity.this, "upload photo", Toast.LENGTH_SHORT).show();
        }
        if (view == createPot ){
            goTo = new Intent(ProfilActivity.this, CreatePotActivity.class);
            startActivity(goTo);
            finish();
        }

    }
}

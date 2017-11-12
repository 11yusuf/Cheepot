package zn2.ft.aj.cheepot;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfilActivity extends Activity {
    private TextView profilName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profil);
        profilName = (TextView) findViewById(R.id.profilName);
        Bundle bundle = getIntent().getExtras();
        String idProfil = bundle.getString("IdProfil");
        profilName.setText(idProfil);
    }

}

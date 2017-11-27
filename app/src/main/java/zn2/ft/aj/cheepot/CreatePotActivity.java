package zn2.ft.aj.cheepot;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePotActivity extends Activity {
    private EditText nomPot;
    private EditText description;
    private Spinner spinner;
    private int selectedItem;
    private CardView potBackground;
    private ImageView photoTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_pot);
        potBackground = (CardView) findViewById(R.id.potBackground);
        spinner = (Spinner) findViewById(R.id.spinner);
        photoTest = (ImageView) findViewById(R.id.photoTest);

        final List<String> typesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.types_list)));
        List<PotType> types = new ArrayList<PotType>(12);
        for (int i = 0; i < 12; i++) {
            types.add(new PotType((String) typesList.get(i), getResources().getIdentifier("image" + i, "drawable", getPackageName())));
        }
        spinner.setAdapter(new PotTypeSpinnerAdapter(this, types));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(adapterView.getContext(), ((PotType) adapterView.getItemAtPosition(position)).getName(), Toast.LENGTH_SHORT).show();
                selectedItem = position;
                changeBackground(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
    }

    private void changeBackground(int position) {
        switch (position) {
            case 0:
                photoTest.setImageResource(R.drawable.background_sign2); //Aniversaire
                break;
            case 1:
                photoTest.setImageResource(R.drawable.background_sign); //Weekèend à plusieurs
                break;
            case 2:
                photoTest.setImageResource(R.drawable.background_sign3); //Soirée
                break;
            case 3:
                photoTest.setImageResource(R.drawable.background_sign4); //Projet
                break;
            case 4:
                photoTest.setImageResource(R.drawable.background_sign5); //Mariage
                break;
            case 5:
                photoTest.setImageResource(R.drawable.backgroundtwo); //Naissance
                break;
            case 6:
                photoTest.setImageResource(R.drawable.background); //Collocation
                break;
            case 7:
                photoTest.setImageResource(R.drawable.background_sign2); //Remerciments
                break;
            case 8:
                photoTest.setImageResource(R.drawable.ic_grenade); //Solidarité
                break;
            case 9:
                photoTest.setImageResource(R.drawable.background_sign); //Association
                break;
            case 10:
                photoTest.setImageResource(R.drawable.background_sign3); //Autres
                break;
            default:
                break;
        }
        photoTest.setScaleType(ImageView.ScaleType.FIT_XY);
    }

}

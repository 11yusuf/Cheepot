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
        potBackground = (CardView)findViewById(R.id.potBackground);
        spinner = (Spinner) findViewById(R.id.spinner);
        photoTest = (ImageView) findViewById(R.id.photoTest);
        final List<String> typesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.types_list)));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, typesList) {

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = position;
                changeBackground(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void changeBackground(int position) {
        switch (position){
            case 0: photoTest.setImageResource(R.drawable.background_sign2); //Aniversaire
                break;
            case 1:  photoTest.setImageResource(R.drawable.background_sign); //Weekèend à plusieurs
                break;
            case 2:  photoTest.setImageResource(R.drawable.background_sign3); //Soirée
                break;
            case 3:  photoTest.setImageResource(R.drawable.background_sign4); //Projet
                break;
            case 4:  photoTest.setImageResource(R.drawable.background_sign5); //Mariage
                break;
            case 5:  photoTest.setImageResource(R.drawable.backgroundtwo); //Naissance
                break;
            case 6:  photoTest.setImageResource(R.drawable.background); //Collocation
                break;
            case 7:  photoTest.setImageResource(R.drawable.background_sign2); //Remerciments
                break;
            case 8:  photoTest.setImageResource(R.drawable.ic_grenade); //Solidarité
                break;
            case 9:  photoTest.setImageResource(R.drawable.background_sign); //Association
                break;
            case 10: photoTest.setImageResource(R.drawable.background_sign3); //Autres
                break;
            default: break;
        }
        photoTest.setScaleType(ImageView.ScaleType.FIT_XY);
    }

}

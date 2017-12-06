package zn2.ft.aj.cheepot.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zn2.ft.aj.cheepot.CreatePotActivity;
import zn2.ft.aj.cheepot.HomeActivity;
import zn2.ft.aj.cheepot.PotProfilActivity;
import zn2.ft.aj.cheepot.ProfilActivity;
import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.adpater.PotType;
import zn2.ft.aj.cheepot.adpater.PotTypeSpinnerAdapter;
import zn2.ft.aj.cheepot.data.Pot;
import zn2.ft.aj.cheepot.data.User;


public class CreatePotFragment extends Fragment implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText potName;
    private Spinner spinner;
    private int selectedItem;
    private CardView potBackground;
    private ImageView photoTest;
    private Button createPotButton;
    private Pot potCreated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_create_pot, container, false);
        potBackground = (CardView) view.findViewById(R.id.potBackground);
        potName = (EditText)view.findViewById(R.id.potName);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        photoTest = (ImageView) view.findViewById(R.id.photoTest);
        createPotButton = (Button) view.findViewById(R.id.createPotButton);
        createPotButton.setOnClickListener(this);
        final List<String> typesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.types_list)));
        List<PotType> types = new ArrayList<PotType>(12);
        for (int i = 0; i < 12; i++) {
            types.add(new PotType((String) typesList.get(i), getResources().getIdentifier("image" + i, "drawable", getActivity().getPackageName())));
        }
        spinner.setAdapter(new PotTypeSpinnerAdapter(getActivity(), types));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               // Toast.makeText(adapterView.getContext(), ((PotType) adapterView.getItemAtPosition(position)).getName(), Toast.LENGTH_SHORT).show();
                selectedItem = position;
                changeBackground(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
        return view;
    }

    private void changeBackground(int position) {
        switch (position) {
            case 0:
                photoTest.setImageResource(R.drawable.anniversaire); //Aniversaire
                break;
            case 1:
                photoTest.setImageResource(R.drawable.anniversaire); //Weekèend à plusieurs
                break;
            case 2:
                photoTest.setImageResource(R.drawable.background_sign3); //Soirée
                break;
            case 3:
                photoTest.setImageResource(R.drawable.background_sign3); //Voyage
                break;
            case 4:
                photoTest.setImageResource(R.drawable.projet); //Projet
                break;
            case 5:
                photoTest.setImageResource(R.drawable.background_sign5); //Mariage
                break;
            case 6:
                photoTest.setImageResource(R.drawable.naissance); //Naissance
                break;
            case 7:
                photoTest.setImageResource(R.drawable.collocation); //Collocation
                break;
            case 8:
                photoTest.setImageResource(R.drawable.background_sign2); //Remerciments
                break;
            case 9:
                photoTest.setImageResource(R.drawable.ic_grenade); //Solidarité
                break;
            case 10:
                photoTest.setImageResource(R.drawable.background_sign); //Association
                break;
            case 11:
                photoTest.setImageResource(R.drawable.background_sign3); //Autres
                break;
            default:
                break;
        }
        photoTest.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void onClick(View v) {
        if(v == createPotButton && valideCreation()){
                //STORE DATA
             /*   DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference currentUserDb = myRef.child("users").child(mAuth.getCurrentUser().getUid()).child("createdPots");
                DatabaseReference potsDb = myRef.child("activePots").push();
                currentUserDb.setValue(potsDb.getKey());
                potsDb.setValue(potCreated);
               */
                Intent goTo;
                goTo = new Intent(v.getContext(), PotProfilActivity.class);
                goTo.putExtra("potCreated", potCreated);
                startActivity(goTo);
                getActivity().finish();
        }

    }

    public boolean valideCreation(){
        if (TextUtils.isEmpty(potName.getText().toString())){
            Toast.makeText(getActivity(), "Entrez un nom pour la cagnotte", Toast.LENGTH_SHORT).show();
            return false;
        }
        potCreated = new Pot(potName.getText().toString(), selectedItem, mAuth.getCurrentUser().getUid().toString());
        return true;
    }
}
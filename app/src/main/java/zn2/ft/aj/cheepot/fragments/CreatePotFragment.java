package zn2.ft.aj.cheepot.fragments;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zn2.ft.aj.cheepot.PotCreationActivity;
import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.adpater.PotType;
import zn2.ft.aj.cheepot.adpater.PotTypeSpinnerAdapter;
import zn2.ft.aj.cheepot.data.Pot;
import zn2.ft.aj.cheepot.data.User;


public class CreatePotFragment extends Fragment implements View.OnClickListener {

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
        if (mAuth == null) {
            Toast.makeText(getActivity(), "pas de connection", Toast.LENGTH_SHORT).show();
        }
        View view = inflater.inflate(R.layout.fragment_create_pot, container, false);
        potBackground = (CardView) view.findViewById(R.id.potBackground);
        potName = (EditText) view.findViewById(R.id.potName);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        photoTest = (ImageView) view.findViewById(R.id.photoTest);
        createPotButton = (Button) view.findViewById(R.id.newPotButton);
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
                photoTest.setImageResource(R.drawable.weekend2); //Weekèend à plusieurs
                break;
            case 2:
                photoTest.setImageResource(R.drawable.soiree2); //Soirée
                break;
            case 3:
                photoTest.setImageResource(R.drawable.voyage3); //Voyage
                break;
            case 4:
                photoTest.setImageResource(R.drawable.projet); //Projet
                break;
            case 5:
                photoTest.setImageResource(R.drawable.wedding); //Mariage
                break;
            case 6:
                photoTest.setImageResource(R.drawable.naissance); //Naissance
                break;
            case 7:
                photoTest.setImageResource(R.drawable.collocation); //Collocation
                break;
            case 8:
                photoTest.setImageResource(R.drawable.remerciement); //Remerciments
                break;
            case 9:
                photoTest.setImageResource(R.drawable.solidarity3); //Solidarité
                break;
            case 10:
                photoTest.setImageResource(R.drawable.association); //Association
                break;
            case 11:
                photoTest.setImageResource(R.drawable.ic_pomegranate_colored); //Autres
                break;
            default:
                break;
        }
        photoTest.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void onClick(View v) {
        if (v == createPotButton && valideCreation()) {
            //STORE DATA

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("userInfo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User tmpuser = dataSnapshot.getValue(User.class);
                    Intent goTo;
                    goTo = new Intent(getContext(), PotCreationActivity.class);
                    potCreated = new Pot(potName.getText().toString().trim(), selectedItem, mAuth.getCurrentUser().getUid().toString(),tmpuser.name+ " " + tmpuser.familyName);
                    goTo.putExtra("pot", potCreated);
                    goTo.putExtra("typeEntry", 0);
                    getActivity().startActivity(goTo);
                }
                    @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Erreur de connection \n verifier votre connection", Toast.LENGTH_SHORT).show();
                    }
            });

        }
    }

    public boolean valideCreation() {
        if (TextUtils.isEmpty(potName.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Entrez un nom pour la cagnotte", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
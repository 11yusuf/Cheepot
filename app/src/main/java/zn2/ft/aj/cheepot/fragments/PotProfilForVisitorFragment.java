package zn2.ft.aj.cheepot.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.data.Pot;


public class PotProfilForVisitorFragment extends Fragment implements View.OnClickListener{
  
    private Pot potVisited;
    private Button buttonShareOnFacebook, buttonPutMoney, buttonSpendPot, buttonEditPot;
    private TextView potVisitedName, potVisitedType, potVisitedDescription, moneyInPot;
    private ImageButton addPotToFavorite;

    public PotProfilForVisitorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            potVisited = (Pot) getArguments().getSerializable("potVisited");
        }
        LayerDrawable ld = (LayerDrawable) getResources().getDrawable(R.drawable.backgroundpotcreation);
        Drawable replace = (Drawable) changeBackground(potVisited.type);
        ld.setDrawableByLayerId(R.id.backgroundpotcreation, replace);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pot_profil_for_visitor, container, false);
        View photoHeader = view.findViewById(R.id.photoHeader);
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /* For devices equal or higher than lollipop set the translation above everything else */
            photoHeader.setTranslationZ(6);
            /* Redraw the view to show the translation */
            photoHeader.invalidate();
        }
        moneyInPot = (TextView) view.findViewById(R.id.moneyInPot);
        moneyInPot.setText(Integer.toString(potVisited.money) + " DT");
        potVisitedName = (TextView) view.findViewById(R.id.potVisitedName);
        potVisitedName.setText(potVisited.potName);
        potVisitedType = (TextView) view.findViewById(R.id.potVisitedType);
        potVisitedType.setText(sentenceGenerator(potVisited.type));
        potVisitedDescription = (TextView) view.findViewById(R.id.potVisitedDescription);
        potVisitedDescription.setText(potVisited.description);
        buttonShareOnFacebook = (Button) view.findViewById(R.id.buttonShareOnFacebook);
        buttonShareOnFacebook.setOnClickListener(this);
        buttonPutMoney = (Button) view.findViewById(R.id.buttonPutMoney);
        buttonPutMoney.setOnClickListener(this);
        addPotToFavorite = (ImageButton) view.findViewById(R.id.addPotToFavorite);
        addPotToFavorite.setVisibility(View.VISIBLE);
        addPotToFavorite.setOnClickListener(this);
        return  view;
    }

    private Drawable changeBackground(int position) {
        switch (position) {
            case 0:
                return getResources().getDrawable(R.drawable.anniversaire); //Aniversaire
            case 1:
                return getResources().getDrawable(R.drawable.weekend2); //Weekèend à plusieurs
            case 2:
                return getResources().getDrawable(R.drawable.soiree2); //Soirée
            case 3:
                return getResources().getDrawable(R.drawable.voyage3); //Voyage
            case 4:
                return getResources().getDrawable(R.drawable.projet); //Projet
            case 5:
                return getResources().getDrawable(R.drawable.wedding); //Mariage
            case 6:
                return getResources().getDrawable(R.drawable.naissance); //Naissance
            case 7:
                return getResources().getDrawable(R.drawable.collocation); //Collocation
            case 8:
                return getResources().getDrawable(R.drawable.remerciement); //Remerciments
            case 9:
                return getResources().getDrawable(R.drawable.solidarity3); //Solidarité
            case 10:
                return getResources().getDrawable(R.drawable.association); //Association
            case 11:
                return getResources().getDrawable(R.drawable.ic_pomegranate_colored); //Autres
            default:
                break;
        }
        return null;
    }

    private String sentenceGenerator(int type) {
        final List<String> typesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.types_list)));
        if (type == 0 || type == 10 || type == 11) {
            return "Cagnotte d'" + typesList.get(type) + " ouverte jusqu'à le " + potVisited.dateFin;
        } else {
            return "Cagnotte pour " + typesList.get(type) + " ouverte jusqu'à le " + potVisited.dateFin;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonPutMoney) {
            putMoneyInPot();
        }
        if (v == buttonShareOnFacebook) {

        }
        if (v == addPotToFavorite) {

        }
    }

    public void putMoneyInPot() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_put_money, null);

        final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
        final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
        Button mLogin = (Button) mView.findViewById(R.id.btnLogin);


        mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

}

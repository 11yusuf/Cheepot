package zn2.ft.aj.cheepot.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.data.Pot;

public class PotProfilForOwnerFragment extends Fragment {
    private Pot potCreated;

    public PotProfilForOwnerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            potCreated = (Pot) getArguments().getSerializable("potOwned");
        }
        LayerDrawable ld = (LayerDrawable) getResources().getDrawable(R.drawable.backgroundpotcreation);
        Drawable replace = (Drawable) changeBackground(potCreated.type);
        ld.setDrawableByLayerId(R.id.backgroundpotcreation, replace);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pot_profil_for_owner, container, false);
        View photoHeader = view.findViewById(R.id.photoHeader);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /* For devices equal or higher than lollipop set the translation above everything else */
            photoHeader.setTranslationZ(6);
            /* Redraw the view to show the translation */
            photoHeader.invalidate();
        }
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
}

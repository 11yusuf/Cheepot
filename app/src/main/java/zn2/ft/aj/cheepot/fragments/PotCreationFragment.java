package zn2.ft.aj.cheepot.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

import zn2.ft.aj.cheepot.HomeActivity;
import zn2.ft.aj.cheepot.PotCreationActivity;
import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.SignUpActivity;
import zn2.ft.aj.cheepot.data.Pot;


/**
 * A simple {@link Fragment} subclass.
 */
public class PotCreationFragment extends Fragment implements View.OnClickListener {

    private Pot potToCreate;
    private TextView potToCreateName, dateFinish;
    private SparkButton sparkButton;
    private FirebaseAuth mAuth;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private ImageButton calendar;
    private int yearF, monthF, dayF;
    private EditText potToCreateDescription;
    private DateTime dateFin;

    public PotCreationFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            potToCreate = (Pot) getArguments().getSerializable("potToCreate");
        }
        LayerDrawable ld = (LayerDrawable) getResources().getDrawable(R.drawable.backgroundpotcreation);
        Drawable replace = (Drawable) changeBackground(potToCreate.type);
        ld.setDrawableByLayerId(R.id.backgroundpotcreation, replace);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pot_creation, container, false);
        View photoHeader = view.findViewById(R.id.photoHeader);
        mAuth = FirebaseAuth.getInstance();
        sparkButton = (SparkButton) view.findViewById(R.id.finishPotCreation);
        potToCreateName = (TextView) view.findViewById(R.id.potToCreateName);
        potToCreateName.setText(potToCreate.potName);
        potToCreateDescription = (EditText) view.findViewById(R.id.potToCreateDescription);
        yearF = monthF = dayF = 1;
        dateFinish = (TextView) view.findViewById(R.id.dateFinTextView);
        calendar = (ImageButton) view.findViewById(R.id.calendar);
        calendar.setOnClickListener(this);
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                dateFinish.setText(String.format("Date Fin: %d/%d/%d", dd, mm + 1, yy));
                yearF = yy;
                monthF = mm + 1;
                dayF = dd;
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /* For devices equal or higher than lollipop set the translation above everything else */
            photoHeader.setTranslationZ(6);
            /* Redraw the view to show the translation */
            photoHeader.invalidate();
        }
        sparkButton.setEventListener(new SparkEventListener() {
            private boolean ko;

            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    // Button is active
                    ko = notValidCreation();
                    if (ko) {
                        sparkButton.setChecked(false);
                    }
                } else {
                    // Button is inactive
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                if (!ko) {
                    Intent goTo;
                    goTo = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(goTo);
                    getActivity().finish();
                }
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                if (!ko) {
                    String Description = "Sans description";
                    if (!TextUtils.isEmpty(potToCreateDescription.getText().toString())) {
                        Description = potToCreateDescription.getText().toString();
                    };
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference currentUserDb = myRef.child("users").child(mAuth.getCurrentUser().getUid()).child("createdPots");
                    DatabaseReference potsDb = myRef.child("activePots").push();
                    potToCreate.setter(Description, yearF, monthF, dayF,potsDb.getKey());
                    currentUserDb.child(potsDb.getKey()).setValue(potsDb.getKey(), "key");
                    potsDb.setValue(potToCreate);
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == calendar) {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    view.getContext(),
                    android.R.style.Theme_DeviceDefault_Dialog_MinWidth, DateSetListener, 2017, 11, 21);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
     /*   if (view == backButton){
            Intent goTo;
            goTo = new Intent(view.getContext(), HomeActivity.class);
            startActivity(goTo);
            getActivity().finish();
        }*/
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

    public boolean notValidCreation() {
        //if()
        dateFin = new DateTime(yearF, monthF, dayF, 23, 59);
        DateTime cmp = new DateTime();
        if (cmp.isAfter(dateFin)) {
            Toast.makeText(getActivity(), "la date est invalide", Toast.LENGTH_SHORT).show();
            return true;
        }
        // Toast.makeText(getActivity(), dateF.toString()+"la date est invalide", Toast.LENGTH_SHORT).show();
        return false;
    }

}

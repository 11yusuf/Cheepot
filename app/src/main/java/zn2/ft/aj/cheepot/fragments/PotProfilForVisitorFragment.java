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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.data.MD5;
import zn2.ft.aj.cheepot.data.Notification;
import zn2.ft.aj.cheepot.data.Pot;
import zn2.ft.aj.cheepot.data.User;


public class PotProfilForVisitorFragment extends Fragment implements View.OnClickListener {

    private Pot potVisited;
    private Button buttonShareOnFacebook, buttonPutMoney, buttonSpendPot, buttonEditPot;
    private TextView potVisitedName, potVisitedType, potVisitedDescription, moneyInPot;
    private ImageButton addPotToFavoriteStar;
    FirebaseAuth mAuth;
    DatabaseReference myRef;


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
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
//        Toast.makeText(getActivity(),"entered",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pot_profil_for_visitor, container, false);
        View photoHeader = view.findViewById(R.id.photoHeader);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        addPotToFavoriteStar = (ImageButton) view.findViewById(R.id.addPotToFavoriteStar);
        addPotToFavoriteStar.setVisibility(View.VISIBLE);
        addPotToFavoriteStar.setOnClickListener(this);
        return view;
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
        if (v == addPotToFavoriteStar) {
            addToFavorite();

        }
    }

    private void addToFavorite() {
        DatabaseReference myDataRef = myRef.child("users").child(mAuth.getCurrentUser().getUid()).child("favoritePots");
        addPotToFavoriteStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_colored));
        potVisited.addFollower();
        myDataRef.child(potVisited.potId).setValue(potVisited.potId);
        myRef.child("activePots").child(potVisited.potId).setValue(potVisited);
    }

    public void putMoneyInPot() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_put_money, null);
        boolean ok = false;
        final EditText moneyToPut = (EditText) mView.findViewById(R.id.moneyToPut);
        final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
        final TextView confirmationMessage = (TextView) mView.findViewById(R.id.confirmationMessage);
        Button verify = (Button) mView.findViewById(R.id.buttonVerify);
        Button confirm = (Button) mView.findViewById(R.id.buttonConfirm);
        Button cancel = (Button) mView.findViewById(R.id.buttonCancel);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moneyToPut.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Entrer le montant à verser", Toast.LENGTH_SHORT).show();
                    return;
                }
                int toPutMoney = Integer.parseInt(moneyToPut.getText().toString());
                if (toPutMoney < 0) {
                    Toast.makeText(getActivity(), "Entré est invalide", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    confirmationMessage.setText("Montant à verser: " + Integer.toString(toPutMoney) + " Dt");
                }

            }

        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgC = confirmationMessage.getText().toString();
                if (msgC.equals("Montant à verser:")) {
                    Toast.makeText(getActivity(), "Verifier le montant à verser", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!msgC.equals("Montant à verser: " + moneyToPut.getText().toString() + " Dt")) {
                    Toast.makeText(getActivity(), "Reverifier le montant à verser", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Confirmer en entrant votre mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                } else {
//                    Toast.makeText(getActivity(), "Veuillez patienter", Toast.LENGTH_SHORT).show();
                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    final DatabaseReference myDataRefer = FirebaseDatabase.getInstance().getReference();
                    final DatabaseReference myRefer = myDataRefer.child("users").child(mAuth.getCurrentUser().getUid());
                    myRefer.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            User tmpuser = (User) dataSnapshot.getValue(User.class);
                            if (tmpuser.password == null) {
                                return;
                            }
                            String password = mPassword.getText().toString();
                            try {
                                password = MD5.crypt(password);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Erreur de sécurité, Réessayer", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (!tmpuser.password.equals(password)) {
                                Toast.makeText(getActivity(), "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            int toPutMoney = Integer.parseInt(moneyToPut.getText().toString());
                            if (tmpuser.money < toPutMoney) {
                                Toast.makeText(getActivity(), "Vous n'avez pas assez d'argent", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            DatabaseReference notifData = myDataRefer.child("notifications").push();
                            DateTime now = DateTime.now();
                            Notification notification = new Notification(mAuth.getCurrentUser().getUid().toString(),
                                    tmpuser.name + " " + tmpuser.familyName, potVisited.potId, potVisited.potName,
                                    potVisited.creatorUid, notifData.getKey(), 2, now.toString(), toPutMoney, potVisited.money,
                                    potVisited.followers);
                            notifData.setValue(notification);
                            myDataRefer.child("users").child(potVisited.creatorUid).child("notifications").child(notifData.getKey()).setValue(notifData.getKey());
                            potVisited.addMoney(toPutMoney);
                            tmpuser.takeMoney(toPutMoney);
                            myDataRefer.child("activePots").child(potVisited.potId).setValue(potVisited);
                            myRef.child("userInfo").child("money").setValue(tmpuser.money);
                            Toast.makeText(getActivity(), "Versement avec succés", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

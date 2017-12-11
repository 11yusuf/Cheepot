package zn2.ft.aj.cheepot.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import zn2.ft.aj.cheepot.data.Pot;

/**
 * Created by lenovo-pc on 06/12/2017.
 */


public class SearchFragment extends android.support.v4.app.Fragment {
}
    /*private FirebaseAuth mAuth;
    DatabaseReference databaseReference ;
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference("activePots").child("potName");
        databaseReference.orderByChild("Mariage").equalTo(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pot tt = dataSnapshot.getValue(Pot.class);
                Toast.makeText(getActivity(), tt.potName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/


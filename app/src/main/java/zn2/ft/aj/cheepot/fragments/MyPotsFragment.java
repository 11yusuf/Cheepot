package zn2.ft.aj.cheepot.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import zn2.ft.aj.cheepot.FeedbackActivity;
import zn2.ft.aj.cheepot.PotProfilActivity;
import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.adpater.PotAdapter;
import zn2.ft.aj.cheepot.data.Pot;
import zn2.ft.aj.cheepot.data.User;

/**
 * Created by lenovo-pc on 06/12/2017.
 */

public class MyPotsFragment extends Fragment {

    private Context mContext;

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CardView myItem;
    private List potsId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypots, container, false);
        mContext = getActivity().getApplicationContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        potsId = new ArrayList();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("createdPots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> potspointer = dataSnapshot.getChildren();
                for (DataSnapshot c : potspointer) {
                    String k = c.getValue(String.class);
                    potsId.add(k);
                }
                mDatabase.child("activePots").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Pot> TT = new ArrayList<Pot>();
                        for (int i = 0; i < potsId.size(); i++) {
                            Pot T = dataSnapshot.child(potsId.get(i).toString()).getValue(Pot.class);
                            TT.add(T);
                        }
                        mAdapter = new PotAdapter(mContext, TT, 1);
                        mRecyclerView.setAdapter(mAdapter);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        String[] pots = {
                "Ta7wissa",
                "Tab7ira"

        };

        /*
            GridLayoutManager
                A RecyclerView.LayoutManager implementations that lays out items in a grid.
                By default, each item occupies 1 span. You can change it by providing a custom
                GridLayoutManager.SpanSizeLookup instance via setSpanSizeLookup(SpanSizeLookup).
        */
        /*
            public GridLayoutManager (Context context, int spanCount)
                Creates a vertical GridLayoutManager

            Parameters
                context : Current context, will be used to access resources.
                spanCount : The number of columns in the grid
        */
        // Define a layout for RecyclerView


        // Initialize a new instance of RecyclerView Adapter instance

        myItem = (CardView) view.findViewById(R.id.card_view);

        return view;
    }


}





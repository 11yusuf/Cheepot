package zn2.ft.aj.cheepot.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.adpater.PotAdapter;
import zn2.ft.aj.cheepot.data.Pot;

/**
 * Created by lenovo-pc on 06/12/2017.
 */

public class FavoritePotsFragment extends Fragment {

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
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("favoritePots").addValueEventListener(new ValueEventListener() {
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
                        mAdapter = new PotAdapter(mContext, TT, 2);
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
        return view;
    }

}

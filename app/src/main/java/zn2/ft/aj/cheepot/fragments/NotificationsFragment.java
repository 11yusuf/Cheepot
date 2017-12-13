package zn2.ft.aj.cheepot.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
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

public class NotificationsFragment extends Fragment {

    private Context mContext;

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CardView myItem;
    private List potsId;


    private ExpandingItem tt;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        mContext = getActivity().getApplicationContext();

        final ExpandingList expandingList = (ExpandingList) view.findViewById(R.id.expanding_list_main);


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
                            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
                            item.createSubItems(2);

                            Pot T = dataSnapshot.child(potsId.get(i).toString()).getValue(Pot.class);

                            ((TextView) item.findViewById(R.id.title)).setText(T.potName);
                            View subItemZero = item.getSubItemView(0);
                            ((TextView) subItemZero.findViewById(R.id.sub_title)).setText(Integer.toString(T.money));

                            View subItemOne = item.getSubItemView(1);
                            ((TextView) subItemOne.findViewById(R.id.sub_title)).setText(Integer.toString(T.money));

                            item.setIndicatorColorRes(R.color.bittersweet);
                            item.setIndicatorIconRes(R.drawable.ic_find);

                        }
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



    return view; }}


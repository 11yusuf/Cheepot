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
import android.widget.Toast;

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
import zn2.ft.aj.cheepot.data.Notification;
import zn2.ft.aj.cheepot.data.Pot;

/**
 * Created by lenovo-pc on 06/12/2017.
 */

public class NotificationsFragment extends Fragment {

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CardView myItem;
    private List notificationsId;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        final ExpandingList expandingList = (ExpandingList) view.findViewById(R.id.expanding_list_main);

        notificationsId = new ArrayList();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> potspointer = dataSnapshot.getChildren();
                for (DataSnapshot c : potspointer) {
                    String k = c.getValue(String.class);
                    notificationsId.add(k);
//                    Toast.makeText(getActivity(),k ,Toast.LENGTH_SHORT).show();
                }
                mDatabase.child("notifications").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (int i = 0; i < notificationsId.size(); i++) {
                            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
                            item.createSubItems(1);
                            Notification notification = dataSnapshot.child(notificationsId.get(i).toString()).getValue(Notification.class);
//                            Toast.makeText(getActivity(),notification.potName,Toast.LENGTH_SHORT).show();
                            if (notification.type == 1) {
                                ((TextView) item.findViewById(R.id.title)).setText(notification.notifierName + " a suivi " + notification.potName);
                                ((TextView) item.findViewById(R.id.creationDate)).setText(notification.creationDate);
                                View subItemZero = item.getSubItemView(0);
                                ((TextView) subItemZero.findViewById(R.id.sub_title)).setText(notification.followersNumber);
                                item.setIndicatorColorRes(R.color.grapefruit_1);
                                item.setIndicatorIconRes(R.drawable.ic_star);
                            }
                            if (notification.type == 2) {
                                ((TextView) item.findViewById(R.id.title)).setText(notification.notifierName + " a versé " + notification.moneyAdded + " dans " + notification.potName);
                                ((TextView) item.findViewById(R.id.creationDate)).setText(notification.creationDate);

                                int totalMoney = notification.previousMoney + notification.moneyAdded;
                                View subItemZero = item.getSubItemView(0);
                                ((TextView) subItemZero.findViewById(R.id.sub_title)).setText(notification.previousMoney + " >> " + Integer.toString(totalMoney));

                                item.setIndicatorColorRes(R.color.mint_1);
                                item.setIndicatorIconRes(R.drawable.ic_pomegranate);
                            }

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


        return view;
    }
}


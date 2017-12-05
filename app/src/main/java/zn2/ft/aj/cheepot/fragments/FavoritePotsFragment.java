package zn2.ft.aj.cheepot.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import zn2.ft.aj.cheepot.R;


public class FavoritePotsFragment extends Fragment {
    private ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_pots, container, false);


        list = (ListView)view.findViewById(R.id.ListView);
        String[] array = {};
        ArrayList<String> lst = new ArrayList<String>(Arrays.asList(array));
        lst.add("Annif yoyo");
        lst.add("VOYAGE");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,lst);
        list.setAdapter(adapter);

         return view;
    }

}

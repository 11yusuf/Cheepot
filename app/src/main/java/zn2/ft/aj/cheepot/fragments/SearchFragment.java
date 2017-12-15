package zn2.ft.aj.cheepot.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import zn2.ft.aj.cheepot.R;

public class SearchFragment extends Fragment implements View.OnClickListener {
    EditText searchWord;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchWord = (EditText) view.findViewById(R.id.searchWord);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}

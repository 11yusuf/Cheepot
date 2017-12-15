package zn2.ft.aj.cheepot;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import zn2.ft.aj.cheepot.adpater.PartnerAdapter;

public class PartnersListActivity extends ListActivity{
    private String[] partnersList = new String[]  {"Sasio" , "Mabrouk", "Demander un virement"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_partners_list);
        setListAdapter(new PartnerAdapter(this, partnersList));

    }
    protected void onListItemClick(ListView l, View v, int position, long id) {


        String selectedValue = (String) getListAdapter().getItem(position);
        if (selectedValue == "Sasio"){
            Uri uri = Uri.parse("http://www.sasio.com.tn");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);}
        else {
            Uri uri = Uri.parse("http://www.mabrouk.tn");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);}
    }

    }

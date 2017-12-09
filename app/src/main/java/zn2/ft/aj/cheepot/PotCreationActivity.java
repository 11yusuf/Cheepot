package zn2.ft.aj.cheepot;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zn2.ft.aj.cheepot.data.Pot;
import zn2.ft.aj.cheepot.fragments.PotCreationFragment;
import zn2.ft.aj.cheepot.fragments.PotProfilForOwnerFragment;
import zn2.ft.aj.cheepot.fragments.PotProfilForVisitorFragment;

public class PotCreationActivity extends AppCompatActivity {
    private Pot pot;
    private int type; // type 0 == Creation d'une cagnotte
    // type == 1 Creator Entry
    // type == 2 Visitor Entry
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_creation);
        type = (int) getIntent().getIntExtra("typeEntry", 0);
        pot = (Pot) getIntent().getSerializableExtra("pot");
        Bundle bundle = new Bundle();
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if( type == 2) {
                bundle.putSerializable("potVisited", pot);
                PotProfilForVisitorFragment  nextFragment = new PotProfilForVisitorFragment();
                nextFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, nextFragment, "PotProfilForVisitorFragment");
            }else if (type == 1){
                bundle.putSerializable("potOwned", pot);
                PotProfilForOwnerFragment nextFragment = new PotProfilForOwnerFragment();
                nextFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, nextFragment, "PotProfilForOwnerFragment");
            }else {
                bundle.putSerializable("potToCreate", pot);
                PotCreationFragment nextFragment = new PotCreationFragment();
                nextFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, nextFragment, "PotCreationFragment");
            }
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}

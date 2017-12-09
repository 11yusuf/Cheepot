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
    private Pot potCreated;
    private int type; // type 0 == Creation d'une cagnotte
    // type == 1 Creator Entry
    // type == 2 Visitor Entry
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_creation);

        type = (int) getIntent().getIntExtra("typeEntry", 0);
        potCreated = (Pot) getIntent().getSerializableExtra("potCreated");
        Bundle bundle = new Bundle();
        bundle.putSerializable("potToCreate", potCreated);
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if( type == 2) {
                PotProfilForVisitorFragment  nextFragment = new PotProfilForVisitorFragment();
                nextFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, nextFragment, "PotProfilForVisitorFragment");
            }else if (type == 1){
                PotProfilForOwnerFragment nextFragment = new PotProfilForOwnerFragment();
                nextFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, nextFragment, "PotProfilForOwnerFragment");
            }else {
                PotCreationFragment nextFragment = new PotCreationFragment();
                nextFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, nextFragment, "PotCreationFragment");
            }
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}

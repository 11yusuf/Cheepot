package zn2.ft.aj.cheepot;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zn2.ft.aj.cheepot.data.Pot;
import zn2.ft.aj.cheepot.fragments.PotCreationFragment;

public class PotCreationActivity extends AppCompatActivity {
    private Pot potCreated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_creation);
        potCreated = (Pot) getIntent().getSerializableExtra("potCreated");
        Bundle bundle = new Bundle();
        bundle.putSerializable("potToCreate", potCreated);
        if (savedInstanceState == null) {
            PotCreationFragment nextFragment = new PotCreationFragment();
            nextFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, nextFragment, "PotCreationFragment");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}

package zn2.ft.aj.cheepot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import zn2.ft.aj.cheepot.adpater.SectionsPageAdapter;
import zn2.ft.aj.cheepot.data.User;
import zn2.ft.aj.cheepot.fragments.FavoritePotsFragment;
import zn2.ft.aj.cheepot.fragments.CreatePotFragment;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPageAdapter mSectionsPageAdapter;
    DatabaseReference databaseReference ;
    private ViewPager mViewPager;
    private FirebaseAuth mAuth;
    private SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        //get datafrom firebase
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("userInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Toast.makeText(getApplicationContext(), user.getString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_favorites_colored);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_charger_compte);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_calendar);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_girl);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_favorites_colored);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_charger_compte);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_calendar);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_girl);
                        break;
                    }
                    case 1: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_favorites);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate_colored);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_charger_compte);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_calendar);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_girl);
                        break;
                    }
                    case 2: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_favorites);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_charger_compte_colored);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_calendar);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_girl);
                        break;
                    }
                    case 3:{
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_favorites);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_charger_compte);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_calendar);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_girl);
                        break;
                    }
                    case 4: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_favorites);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_charger_compte);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_calendar);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_girl);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.consult_account) {
            // Handle the camera action
        } else if (id == R.id.add_money) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.log_out) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new CreatePotFragment(), "haha2");
        adapter.addFragment(new FavoritePotsFragment(), "FavoritePots");
        adapter.addFragment( new FavoritePotsFragment(), "haha1");
        adapter.addFragment(new FavoritePotsFragment(), "haha3");
        adapter.addFragment(new FavoritePotsFragment(), "haha4");
        viewPager.setAdapter(adapter);
    }

}

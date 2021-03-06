package zn2.ft.aj.cheepot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import zn2.ft.aj.cheepot.adpater.SectionsPageAdapter;
import zn2.ft.aj.cheepot.data.User;
import zn2.ft.aj.cheepot.fragments.FavoritePotsFragment;
import zn2.ft.aj.cheepot.fragments.CreatePotFragment;
import zn2.ft.aj.cheepot.fragments.MyPotsFragment;
import zn2.ft.aj.cheepot.fragments.NotificationsFragment;
import zn2.ft.aj.cheepot.fragments.SearchFragment;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPageAdapter mSectionsPageAdapter;
    DatabaseReference databaseReference ;
    private TextView userName;
    private ViewPager mViewPager;
    private FirebaseAuth mAuth;
    private SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
    private User user;
    private CircleImageView userPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

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
        userName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.userName);
        userPhoto = (CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.userPhoto);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
       // user = new User();
        //get userInfo from firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("userInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User tmpuser = dataSnapshot.getValue(User.class);
               // Toast.makeText(getApplicationContext(), user.name + "  " + user.familyName, Toast.LENGTH_SHORT).show();
                userName.setText(tmpuser.name + " " + tmpuser.familyName);
                if (tmpuser.gender == "Femme") {
                    userPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl));
                }else{
                    userPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add_colored);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_bell);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_find);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add_colored);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_bell);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_find);
                        break;
                    }
                    case 1: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate_colored);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_bell);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_find);
                        break;
                    }
                    case 2: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star_colored);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_bell);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_find);
                        break;
                    }
                    case 3:{
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_bell_colored);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_find);
                        break;
                    }
                    case 4: {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pomegranate);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_bell);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_find_colored);
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
        Intent nextIntent;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.consult_account) {

            databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("userInfo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User tmpuser = dataSnapshot.getValue(User.class);
                    int x = tmpuser.money;
                    final AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                    alertDialog.setTitle("Votre solde est");
                    alertDialog.setMessage(Integer.toString(x)+" "+"DT");
                    alertDialog.show();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else if (id == R.id.add_money) {
            nextIntent = new Intent(HomeActivity.this, ChargerCompteActivity.class);
            this.startActivity(nextIntent);
        } else if (id == R.id.settings) {

        } else if (id == R.id.log_out) {
            FirebaseAuth.getInstance().signOut();
            nextIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(nextIntent);
            finish();
        } else if (id == R.id.feedback) {
            nextIntent = new Intent(HomeActivity.this, FeedbackActivity.class);
            this.startActivity(nextIntent);
        } else if (id == R.id.support) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new CreatePotFragment(), "CreatePotFragment");
        adapter.addFragment(new MyPotsFragment(), "FavoritePots");
        adapter.addFragment( new FavoritePotsFragment(), "FavoritePotsFragment");
        adapter.addFragment(new NotificationsFragment(), "NotificationFragment");
        adapter.addFragment(new SearchFragment(), "SearchFragment");
        viewPager.setAdapter(adapter);
    }

}

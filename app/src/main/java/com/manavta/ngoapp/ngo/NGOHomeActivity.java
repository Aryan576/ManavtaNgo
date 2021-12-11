package com.manavta.ngoapp.ngo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.manavta.ngoapp.MainActivity;
import com.manavta.ngoapp.R;
import com.manavta.ngoapp.ngo.Fragment.NGOContactFragment;
import com.manavta.ngoapp.ngo.Fragment.NGOHomeFragment;
import com.manavta.ngoapp.ngo.Fragment.NGOHospitalFragment;
import com.google.android.material.navigation.NavigationView;
import com.manavta.ngoapp.ngo.Fragment.NGOPlasmaDonorFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class NGOHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String fn = sharedPreferences.getString("firstname", "");
        String ln = sharedPreferences.getString("lastname", "");

        View navView = navigationView.getHeaderView(0);

        TextView tvEmail = navView.findViewById(R.id.email);
        TextView tvUserName = navView.findViewById(R.id.name);
        tvEmail.setText(email);
        tvUserName.setText(fn + " " + ln);
        CircleImageView circleImg = navView.findViewById(R.id.profile_image);
        circleImg.setImageResource(R.drawable.img_person);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_open);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        showHome();

    }

    private void showHome() {
        NGOHomeFragment fragment = new NGOHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, fragment,fragment.getTag());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        Fragment fragment= null;

        if ((id == R.id.nav_home)) {

            fragment = new NGOHomeFragment();
            toolbar.setTitle("Home");
        } else if (id == R.id.nav_hospital) {

            fragment = new NGOHospitalFragment();

            toolbar.setTitle("Hospitals");

        } else if (id == R.id.nav_plasma) {
            fragment = new NGOPlasmaDonorFragment();
            toolbar.setTitle("Plasma");


        } else if (id == R.id.nav_contact) {
            fragment = new NGOContactFragment();
            toolbar.setTitle("Contact User");


        } else if (id == R.id.nav_logout) {

            SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("email");
            editor.remove("firstname");
            editor.remove("lastname");
            editor.remove("roleid");
            editor.commit();

            Intent i = new Intent(NGOHomeActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if (fragment != null) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment,fragment.getTag());
             fragmentTransaction.addToBackStack(fragment.getTag());
            fragmentTransaction.commit();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {



            if (checkNavigationMenuItem() != 0) {

                navigationView.setCheckedItem(R.id.nav_home);
                Fragment fragment = new NGOHomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.splash_screen);
                builder.setTitle("Manavta NGO ");
                builder.setMessage("Are you sure do you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }

    }

    private int checkNavigationMenuItem() {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked())
                return i;
        }
        return -1;
    }

}

package com.blm.saytheirnames.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.fragments.HomeFragment;
import com.blm.saytheirnames.fragments.PetitionsFragment;
import com.blm.saytheirnames.network.BackendInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    BackendInterface backendInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);*/

        mBottomNav = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        setDefaultFragment();
       // updateToolbarText();


        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);



//TODO move this into  src/test/  ?
//Testing APi- added by thegeekybaniya
   /*     backendInterface = Utils.getBackendService();
        backendInterface.getPeople().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("API_Response", response.body().toString());
                System.out.println("RES::::::"+response.body());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("RES::::::"+t.getMessage());
            }
        });
*/

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    private void setDefaultFragment() {
        mBottomNav.setSelectedItemId(R.id.navigation_home);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, HomeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }

    private void selectFragment(MenuItem item) {

        mSelectedItem = item.getItemId();
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.navigation_home:
                frag = HomeFragment.newInstance();
                break;
            case R.id.navigation_donation:
                frag = HomeFragment.newInstance();
                break;
            case R.id.navigation_petitions:
                frag = PetitionsFragment.newInstance();
                break;

            case R.id.navigation_settings:
                frag = HomeFragment.newInstance();
                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        /*for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            //menuItem.setChecked(menuItem.getItemId() == item.getItemId());

        }

        updateToolbarText(item.getTitle());*/

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, frag, frag.getTag());
            ft.commit();
        }

    }

    /*private void updateToolbarText() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.action_bar_layout);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }*/


}
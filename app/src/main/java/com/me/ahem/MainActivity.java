package com.me.ahem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.me.ahem.address.AddressViewModel;
import com.me.ahem.location.LocationViewModel;
import com.me.ahem.reminder.ReminderViewModel;

public class MainActivity extends AppCompatActivity {

    Fragment mapFragment;
    Fragment reminderListFragment;
    Fragment addReminderFragment;
    Fragment reminderDetailsFragment;
    FloatingActionButton addButton;

    public static String mode;

    private ReminderViewModel reminderViewModel;
    private LocationViewModel locationViewModel;
    private AddressViewModel addressViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialze fragment
        mapFragment = new MapFragment();
        reminderListFragment = new reminder_list_fragment();

        addReminderFragment = new reminder_add_fragment();
        reminderDetailsFragment = new reminder_details_fragment();

        addButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.compareTo("list") == 0){

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.relativeLayout, new reminder_add_fragment())
                            .addToBackStack(null)
                            .commit();
                    addButton.setImageResource(android.R.drawable.ic_menu_save);

                }else if(mode.compareTo("add") == 0){

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.relativeLayout, new reminder_add_fragment())
                            .addToBackStack(null)
                            .commit();
                    addButton.setImageResource(android.R.drawable.ic_menu_save);

                }else{

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.relativeLayout, new reminder_add_fragment())
                            .addToBackStack(null)
                            .commit();
                    addButton.setImageResource(android.R.drawable.ic_menu_save);

                }

            }
        });

        //Open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, mapFragment)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_controls, reminderListFragment)
                .commit();

        reminderViewModel = new ViewModelProvider(this, ViewModelProvider.Factory()).get(ReminderViewModel.class);
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        reminderViewModel.get

       /* fragment = new MapFragment();

        //Open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_controls, fragment)
                .commit();*/
    }
}
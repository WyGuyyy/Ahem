package com.me.ahem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Fragment mapFragment;
    Fragment reminderListFragment;
    Fragment addReminderFragment;
    Fragment reminderDetailsFragment;
    FloatingActionButton addButton;

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
                Log.v("d", "made it");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.relativeLayout, new reminder_add_fragment())
                        .commit();
                addButton.setImageResource(android.R.drawable.ic_menu_save);
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

       /* fragment = new MapFragment();

        //Open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_controls, fragment)
                .commit();*/
    }
}
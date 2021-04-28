package com.me.ahem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialze fragment
        Fragment fragment = new MapFragment();

        Fragment reminderListFragment = new reminder_list_fragment();

        //Open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
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
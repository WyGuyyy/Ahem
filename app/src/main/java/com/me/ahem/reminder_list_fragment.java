package com.me.ahem;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.me.ahem.address.AddressViewModel;
import com.me.ahem.location.Location;
import com.me.ahem.location.LocationViewModel;
import com.me.ahem.reminder.Reminder;

import java.util.ArrayList;
import java.util.List;

public class reminder_list_fragment extends Fragment {

    ListView listView;
    FloatingActionButton addButton;

    ReminderAdapter reminderAdapter;

    private AhemViewModel ahemViewModel;
    //private LocationViewModel locationViewModel;
    //private AddressViewModel addressViewModel;

    public reminder_list_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_reminder_list_fragment, container, false);
        List<RowItem> reminderList = new ArrayList<RowItem>();

        reminderAdapter = new ReminderAdapter(getActivity(), reminderList);

        ahemViewModel = new ViewModelProvider(getActivity(), new AhemViewModelFactory(getActivity().getApplication())).get(AhemViewModel.class);
        //locationViewModel = new ViewModelProvider(this, new AhemViewModelFactory(getActivity().getApplication())).get(LocationViewModel.class);
        //addressViewModel = new ViewModelProvider(this, new AhemViewModelFactory(getActivity().getApplication())).get(AddressViewModel.class);

        //DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
       // List<RowItem> reminderList = databaseHelper.getAllRemindersForList();

        listView = (ListView) view.findViewById(R.id.reminder_list);
        listView.setAdapter(new ReminderAdapter(getActivity(), reminderList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem =  (RowItem) parent.getItemAtPosition(position);

                ahemViewModel.setRowItem(rowItem);
                Log.d("reminderID", Long.toString(rowItem.getReminderID()));
                ahemViewModel.getReminderFromDatabase(rowItem.getReminderID());
                Log.d("locationID", Long.toString(rowItem.getLocationID()));
                ahemViewModel.getLocationFromDatabase(rowItem.getLocationID());
                Log.d("addressID", Long.toString(rowItem.getAddressID()));
                ahemViewModel.getAddressFromDatabase(rowItem.getAddressID());
                Location location = ahemViewModel.getLocation();
                Log.d("location", Float.toString(location.getLongitude()));
                ahemViewModel.setMode("detail");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        //.replace(R.id.relativeLayout, new reminder_add_fragment())
                        .replace(R.id.frame_layout_controls, new reminder_details_fragment())
                        .commit();
            }
        });

        /*reminderViewModel.getAllReminders().observe(this, items -> {

        });*/

        ahemViewModel.getAllRowItems().observe(this, items -> {
            reminderAdapter.setData(items);
            listView.setAdapter(reminderAdapter);
        });

        addButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonList);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ahemViewModel.setMode("add");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        //.replace(R.id.relativeLayout, new reminder_add_fragment())
                        .replace(R.id.frame_layout_controls, new reminder_add_fragment(), "ADD_FRAGMENT")
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
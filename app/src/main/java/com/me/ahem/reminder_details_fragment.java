package com.me.ahem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.me.ahem.address.Address;
import com.me.ahem.location.Location;
import com.me.ahem.reminder.Reminder;


public class reminder_details_fragment extends Fragment implements View.OnFocusChangeListener{

    private AhemViewModel ahemViewModel;

    EditText txtLongitude;
    EditText txtLatitude;
    EditText txtAddress;
    EditText txtName;
    EditText txtDescription;
    EditText txtDistance;
    EditText txtHour;
    EditText txtMinute;
    EditText txtSecond;

    Switch swDistanceType;
    Switch getSwDistanceUnit;
    Switch swSoundType;

    RadioButton rbCustom;
    RadioButton rbDefault;
    RadioButton rbPing;

    FloatingActionButton editButton;
    FloatingActionButton backButton;

    public reminder_details_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_reminder_details_fragment, container, false);

        ahemViewModel = new ViewModelProvider(getActivity(), new AhemViewModelFactory(getActivity().getApplication())).get(AhemViewModel.class);
        Reminder reminder = ahemViewModel.getReminder();
        Location location = ahemViewModel.getLocation();
        Address address  = ahemViewModel.getAddress();

        txtLongitude = (EditText) view.findViewById(R.id.reminder_details_map_position_longitude);
        txtLatitude = (EditText) view.findViewById(R.id.reminder_details_map_position_latitude);
        txtAddress = (EditText) view.findViewById(R.id.reminder_details_map_position_address);
        txtName = (EditText) view.findViewById(R.id.reminder_details_name);
        txtDescription = (EditText) view.findViewById(R.id.reminder_details_description);
        txtDistance = (EditText) view.findViewById(R.id.reminder_details_distance_unit_amount);
        txtHour = (EditText) view.findViewById(R.id.reminder_details_time_hour);
        txtMinute = (EditText) view.findViewById(R.id.reminder_details_time_minute);
        txtSecond = (EditText) view.findViewById(R.id.reminder_details_time_second);

        swDistanceType = (Switch) view.findViewById(R.id.reminder_details_distance_type);
        getSwDistanceUnit = (Switch) view.findViewById(R.id.reminder_details_distance_unit);
        swSoundType = (Switch) view.findViewById(R.id.reminder_details_sound_type);

        rbCustom = (RadioButton) view.findViewById(R.id.reminder_details_sound_custom);
        rbDefault = (RadioButton) view.findViewById(R.id.reminder_details_sound_default);
        rbPing = (RadioButton) view.findViewById(R.id.reminder_details_sound_ping);

        editButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonDetail);
        backButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonBackDetail);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ahemViewModel.setMode("edit");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        //.replace(R.id.relativeLayout, new reminder_add_fragment())
                        .replace(R.id.frame_layout_controls, new reminder_add_fragment())
                        .commit();
                }
        });

        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ahemViewModel.setMode("list");

                getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        );

        txtLongitude.setText(Float.toString(location.getLongitude()));
        txtLatitude.setText(Float.toString(location.getLatitude()));

        String strAddress = address.getStreetNumber() + " " + address.getStreet() + " " + address.getCity() +
                            address.getState() + ", " + address.getZip() + " " + address.getCountry();
        txtAddress.setText(strAddress);

        txtName.setText(reminder.getName());
        txtDescription.setText(reminder.getReminderDescription());
        txtDistance.setText(Float.toString(location.getRadius()));
        txtHour.setText(Integer.toString(location.getTime()));
        txtMinute.setText(Integer.toString(location.getTime()));
        txtSecond.setText(Integer.toString(location.getTime()));

        swDistanceType.setChecked(true);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            updateViewModel(v);
        }
    }

    public void updateViewModel(View v){

    }

}
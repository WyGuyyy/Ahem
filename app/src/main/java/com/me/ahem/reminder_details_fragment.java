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
import android.widget.ToggleButton;

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

//START HERE NEXT TIME -> NEED TO UPDATE DETAIL FRAGMENT XML LAYOUT TO MATCH ADD FRAGMENT
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

    ToggleButton swDistanceType;
    ToggleButton getSwDistanceUnit;
    ToggleButton swSoundType;

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

        swDistanceType = (ToggleButton) view.findViewById(R.id.reminder_details_distance_type);
        getSwDistanceUnit = (ToggleButton) view.findViewById(R.id.reminder_details_distance_unit);
        swSoundType = (ToggleButton) view.findViewById(R.id.reminder_details_sound_type);

        rbCustom = (RadioButton) view.findViewById(R.id.reminder_details_sound_custom);
        rbDefault = (RadioButton) view.findViewById(R.id.reminder_details_sound_default);
        rbPing = (RadioButton) view.findViewById(R.id.reminder_details_sound_ping);

        editButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonDetail);
        backButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonBackDetail);

        int hour;
        int minute;
        int second;

        float radius;

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ahemViewModel.setMode("edit");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        //.replace(R.id.relativeLayout, new reminder_add_fragment())
                        .replace(R.id.frame_layout_controls, new reminder_add_fragment(), "ADD_FRAGMENT")
                        .commit();
                }
        });

        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ahemViewModel.setMode("list");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        //.replace(R.id.relativeLayout, new reminder_add_fragment())
                        .replace(R.id.frame_layout_controls, new reminder_list_fragment())
                        .commit();
                }
            }
        );

        txtLongitude.setText(Float.toString(location.getLongitude()));
        txtLongitude.setEnabled(false);

        txtLatitude.setText(Float.toString(location.getLatitude()));
        txtLatitude.setEnabled(false);

        String strAddress = address.getStreetNumber(); //+ " " + address.getStreet() + " " + address.getCity() +
                           // address.getState() + ", " + address.getZip() + " " + address.getCountry();
        txtAddress.setText(strAddress);
        txtAddress.setEnabled(false);

        txtName.setText(reminder.getName());
        txtName.setEnabled(false);

        txtDescription.setText(reminder.getReminderDescription());
        txtDescription.setEnabled(false);

        radius = location.getRadius() == -1 ? 0 : location.getRadius();

        txtDistance.setText(Float.toString(radius));
        txtDistance.setEnabled(false);

        hour = location.getTime() == -1 ? 0 : location.getTime() / 3600;
        minute = location.getTime() == -1 ? 0 : location.getTime() - (3600 * hour) / 60;
        second = location.getTime() == -1 ? 0 : location.getTime() - ((3600 * hour) + (60 * minute));

        txtHour.setText(Integer.toString(hour));
        txtHour.setEnabled(false);

        txtMinute.setText(Integer.toString(minute));
        txtMinute.setEnabled(false);

        txtSecond.setText(Integer.toString(second));
        txtSecond.setEnabled(false);

        swDistanceType.setChecked(location.getDistanceType().equals("Radius") ? false : true);
        swDistanceType.setEnabled(false);

        getSwDistanceUnit.setEnabled(false);
        getSwDistanceUnit.setChecked(location.getDistanceUnit().equals("MI") ? false : true);

        swSoundType.setEnabled(false);
        swSoundType.setChecked(reminder.getSoundType().equals("Noise") ? false : true);

        rbCustom.setEnabled(false);
        rbCustom.setChecked(reminder.getSoundSelection().equals("Custom"));

        rbDefault.setEnabled(false);
        rbDefault.setChecked(reminder.getSoundSelection().equals("Default"));

        rbPing.setEnabled(false);
        rbPing.setChecked(reminder.getSoundSelection().equals("Ping"));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            updateViewModel(v);
        }
    }

    public void updateViewModel(View v) {

    }

}
package com.me.ahem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


public class reminder_details_fragment extends Fragment implements View.OnFocusChangeListener{

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

    public reminder_details_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_reminder_details_fragment, container, false);

        txtLongitude = (EditText) getActivity().findViewById(R.id.reminder_details_map_position_longitude);
        txtLatitude = (EditText) getActivity().findViewById(R.id.reminder_details_map_position_latitude);
        txtAddress = (EditText) getActivity().findViewById(R.id.reminder_details_map_position_address);
        txtName = (EditText) getActivity().findViewById(R.id.reminder_details_name);
        txtDescription = (EditText) getActivity().findViewById(R.id.reminder_details_description);
        txtDistance = (EditText) getActivity().findViewById(R.id.reminder_details_distance_unit_amount);
        txtHour = (EditText) getActivity().findViewById(R.id.reminder_details_time_hour);
        txtMinute = (EditText) getActivity().findViewById(R.id.reminder_details_time_minute);
        txtSecond = (EditText) getActivity().findViewById(R.id.reminder_details_time_second);

        swDistanceType = (Switch) getActivity().findViewById(R.id.reminder_details_distance_type);
        getSwDistanceUnit = (Switch) getActivity().findViewById(R.id.reminder_details_distance_unit);
        swSoundType = (Switch) getActivity().findViewById(R.id.reminder_details_sound_type);

        rbCustom = (RadioButton) getActivity().findViewById(R.id.reminder_details_sound_custom);
        rbDefault = (RadioButton) getActivity().findViewById(R.id.reminder_details_sound_default);
        rbPing = (RadioButton) getActivity().findViewById(R.id.reminder_details_sound_ping);

        txtLongitude.setOnFocusChangeListener(this);
        txtLatitude.setOnFocusChangeListener(this);
        txtAddress.setOnFocusChangeListener(this);
        txtName.setOnFocusChangeListener(this);
        txtDescription.setOnFocusChangeListener(this);
        txtDistance.setOnFocusChangeListener(this);
        txtHour.setOnFocusChangeListener(this);
        txtMinute.setOnFocusChangeListener(this);
        txtSecond.setOnFocusChangeListener(this);

        swDistanceType.setOnFocusChangeListener(this);
        getSwDistanceUnit.setOnFocusChangeListener(this);
        swSoundType.setOnFocusChangeListener(this);

        rbCustom.setOnFocusChangeListener(this);
        rbDefault.setOnFocusChangeListener(this);
        rbPing.setOnFocusChangeListener(this);

        MainActivity.mode = "detail";

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
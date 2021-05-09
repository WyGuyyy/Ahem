package com.me.ahem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class reminder_add_fragment extends Fragment implements View.OnFocusChangeListener{

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

    public reminder_add_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_reminder_add_fragment, container, false);

        ahemViewModel = new ViewModelProvider(this, new AhemViewModelFactory(getActivity().getApplication())).get(AhemViewModel.class);

        txtLongitude = (EditText) getActivity().findViewById(R.id.map_position_longitude);
        txtLatitude = (EditText) getActivity().findViewById(R.id.map_position_latitude);
        txtAddress = (EditText) getActivity().findViewById(R.id.map_position_address);
        txtName = (EditText) getActivity().findViewById(R.id.add_reminder_name);
        txtDescription = (EditText) getActivity().findViewById(R.id.add_reminder_description);
        txtDistance = (EditText) getActivity().findViewById(R.id.add_reminder_distance_unit_amount);
        txtHour = (EditText) getActivity().findViewById(R.id.add_reminder_time_hour);
        txtMinute = (EditText) getActivity().findViewById(R.id.add_reminder_time_minute);
        txtSecond = (EditText) getActivity().findViewById(R.id.add_reminder_time_second);

        swDistanceType = (Switch) getActivity().findViewById(R.id.add_reminder_distance_type);
        getSwDistanceUnit = (Switch) getActivity().findViewById(R.id.add_reminder_distance_unit);
        swSoundType = (Switch) getActivity().findViewById(R.id.add_reminder_sound_type);

        rbCustom = (RadioButton) getActivity().findViewById(R.id.add_reminder_sound_custom);
        rbDefault = (RadioButton) getActivity().findViewById(R.id.add_reminder_sound_default);
        rbPing = (RadioButton) getActivity().findViewById(R.id.add_reminder_sound_ping);

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

        MainActivity.mode = "add";

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
        String[] splitArr = v.getTag().toString().split("|");

        String viewType = splitArr[0];
        String key = splitArr[1];
        String value = "";

        if(viewType.equals("ET")){
            value = ((EditText) v).getText().toString();
        }else if(viewType.equals("SW")){
            value = Boolean.toString(((Switch) v).isChecked());
        }else{
            value = Boolean.toString(((RadioButton) v).isChecked());
        }

        ahemViewModel.updateDataMap(key, value);

    }
}
package com.me.ahem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.me.ahem.address.Address;
import com.me.ahem.location.Location;
import com.me.ahem.reminder.Reminder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class reminder_add_fragment extends Fragment{

    private AhemViewModel ahemViewModel;

    EditText txtLongitude;
    EditText txtLatitude;
    AutoCompleteTextView txtAddress;
    EditText txtName;
    EditText txtDescription;
    EditText txtDistance;
    EditText txtHour;
    EditText txtMinute;
    EditText txtSecond;

    /*Switch swDistanceType;
    Switch swDistanceUnit;
    Switch swSoundType;*/

    ToggleButton swDistanceType;
    ToggleButton swDistanceUnit;
    ToggleButton swSoundType;

    RadioButton rbCustom;
    RadioButton rbDefault;
    RadioButton rbPing;

    FloatingActionButton saveButton;
    FloatingActionButton backButton;
    FloatingActionButton recordButton;

    EditText[] textFields;
    ToggleButton[] switchFields;
    RadioButton[] radioButtonFields;

    PlacesClient placesClient;

    public reminder_add_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_reminder_add_fragment, container, false);

        ahemViewModel = new ViewModelProvider(getActivity(), new AhemViewModelFactory(getActivity().getApplication())).get(AhemViewModel.class);

        Places.initialize(this.getActivity().getApplicationContext(), getString(R.string.api_key));
        placesClient = Places.createClient(this.getContext());

        txtLongitude = (EditText) view.findViewById(R.id.map_position_longitude);
        txtLatitude = (EditText) view.findViewById(R.id.map_position_latitude);
        txtAddress = (AutoCompleteTextView) view.findViewById(R.id.map_position_address);
        txtName = (EditText) view.findViewById(R.id.add_reminder_name);
        txtDescription = (EditText) view.findViewById(R.id.add_reminder_description);
        txtDistance = (EditText) view.findViewById(R.id.add_reminder_distance_unit_amount);
        txtHour = (EditText) view.findViewById(R.id.add_reminder_time_hour);
        txtMinute = (EditText) view.findViewById(R.id.add_reminder_time_minute);
        txtSecond = (EditText) view.findViewById(R.id.add_reminder_time_second);

        saveButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonAdd);
        backButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonBackAdd);
        recordButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonRecordAdd);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reminder_add_fragment addFragment = (reminder_add_fragment) getActivity().getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT");

                if(addFragment.persistFields()) {

                    ahemViewModel.submitDataMap();

                    if(ahemViewModel.getMode().equals("edit")){

                        ahemViewModel.setMode("detail");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                //.replace(R.id.relativeLayout, new reminder_add_fragment())
                                .replace(R.id.frame_layout_controls, new reminder_details_fragment())
                                .commit();

                    }else {

                        ahemViewModel.setMode("list");

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                //.replace(R.id.relativeLayout, new reminder_add_fragment())
                                .replace(R.id.frame_layout_controls, new reminder_list_fragment())
                                .commit();
                    }
                }
            }

         });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ahemViewModel.getMode().equals("edit")){
                    ahemViewModel.setMode("detail");

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            //.replace(R.id.relativeLayout, new reminder_add_fragment())
                            .replace(R.id.frame_layout_controls, new reminder_details_fragment())
                            .commit();
                }else {
                    ahemViewModel.setMode("list");

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            //.replace(R.id.relativeLayout, new reminder_add_fragment())
                            .replace(R.id.frame_layout_controls, new reminder_list_fragment())
                            .commit();
                }
            }
        });

        textFields = new EditText[8];
        textFields[0] = txtLongitude;
        textFields[1] = txtLatitude;
        textFields[2] = txtName;
        textFields[3] = txtDescription;
        textFields[4] = txtDistance;
        textFields[5] = txtHour;
        textFields[6] = txtMinute;
        textFields[7] = txtSecond;

        /*swDistanceType = (Switch) view.findViewById(R.id.add_reminder_distance_type);
        swDistanceUnit = (Switch) view.findViewById(R.id.add_reminder_distance_unit);
        swSoundType = (Switch) view.findViewById(R.id.add_reminder_sound_type);*/

        swDistanceType = (ToggleButton) view.findViewById(R.id.add_reminder_distance_type);
        swDistanceUnit = (ToggleButton) view.findViewById(R.id.add_reminder_distance_unit);
        swSoundType = (ToggleButton) view.findViewById(R.id.add_reminder_sound_type);

        swDistanceUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ToggleButton)v).isChecked()){

                }else{

                }
            }
        });

        swDistanceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean distanceTypeState = ((ToggleButton)v).isChecked();

                swDistanceUnit.setEnabled(!distanceTypeState);
                txtDistance.setEnabled(!distanceTypeState);

                txtHour.setEnabled(distanceTypeState);
                txtMinute.setEnabled(distanceTypeState);
                txtSecond.setEnabled(distanceTypeState);
            }
        });

        swSoundType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean soundTypeState = ((ToggleButton) v).isChecked();

                rbCustom.setEnabled(!soundTypeState);
                rbDefault.setEnabled(!soundTypeState);
                rbPing.setEnabled(!soundTypeState);
                recordButton.setEnabled(!soundTypeState);

            }
        });

        switchFields = new ToggleButton[3];
        switchFields[0] = swDistanceType;
        switchFields[1] = swSoundType;
        switchFields[2] = swDistanceUnit;

        rbCustom = (RadioButton) view.findViewById(R.id.add_reminder_sound_custom);
        rbDefault = (RadioButton) view.findViewById(R.id.add_reminder_sound_default);
        rbPing = (RadioButton) view.findViewById(R.id.add_reminder_sound_ping);

        rbCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean customState = ((RadioButton)v).isChecked();

                if(customState){
                    rbCustom.setChecked(true);
                    rbDefault.setChecked(false);
                    rbPing.setChecked(false);

                    recordButton.setEnabled(true);
                }
            }
        });

        rbDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean defaultState = ((RadioButton)v).isChecked();

                if(defaultState){
                    rbCustom.setChecked(false);
                    rbDefault.setChecked(true);
                    rbPing.setChecked(false);

                    recordButton.setEnabled(false);
                }
            }
        });

        rbPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean pingState = ((RadioButton)v).isChecked();

                if(pingState) {
                    rbCustom.setChecked(false);
                    rbDefault.setChecked(false);
                    rbPing.setChecked(true);

                    recordButton.setEnabled(false);
                }
             }
        });


        radioButtonFields = new RadioButton[3];
        radioButtonFields[0] = rbCustom;
        radioButtonFields[1] = rbDefault;
        radioButtonFields[2] = rbPing;

        if(ahemViewModel.getMode().equals("edit")){
            fillFieldsToEdit();
        }

        boolean distanceTypeState = swDistanceType.isChecked();

        swDistanceUnit.setEnabled(!distanceTypeState);
        txtDistance.setEnabled(!distanceTypeState);

        txtHour.setEnabled(distanceTypeState);
        txtMinute.setEnabled(distanceTypeState);
        txtSecond.setEnabled(distanceTypeState);

        boolean soundTypeState = swSoundType.isChecked();

        rbCustom.setEnabled(!soundTypeState);
        rbDefault.setEnabled(!soundTypeState);
        rbPing.setEnabled(!soundTypeState);
        recordButton.setEnabled(!soundTypeState);

        // Inflate the layout for this fragment
        return view;
    }

  private void fillFieldsToEdit(){

      Reminder reminder = ahemViewModel.getReminder();
      Location location = ahemViewModel.getLocation();
      Address address = ahemViewModel.getAddress();

      String longitude = Float.toString(location.getLongitude());
      String latitude = Float.toString(location.getLatitude());
      String strAddress = address.getStreetNumber() + " " + address.getStreet() + " " + address.getCity() + " " + address.getState() + " " + address.getCountry() + " " + address.getZip();
      String name = reminder.getName();
      String description = reminder.getReminderDescription();
      String strDistance = Float.toString(location.getRadius());
      String hour = Integer.toString(location.getTime());
      String minute = Integer.toString(location.getTime());
      String second = Integer.toString(location.getTime());

      boolean distanceType = true;
      boolean distanceUnit = false;
      boolean soundType = true;

      boolean custom = true;
      boolean aiVoice = false;
      boolean ping = false;

      txtLongitude.setText(longitude);
      txtLatitude.setText(latitude);
      txtAddress.setText(strAddress);
      txtName.setText(name);
      txtDescription.setText(description);
      txtDistance.setText(strDistance);
      txtHour.setText(hour);
      txtMinute.setText(minute);
      txtSecond.setText(second);

      swDistanceType.setChecked(distanceType);
      swDistanceUnit.setChecked(distanceUnit);
      swSoundType.setChecked(soundType);

      rbCustom.setChecked(custom);
      rbDefault.setChecked(aiVoice);
      rbPing.setChecked(ping);

  }

    private void updateSuggestions(CharSequence s){

        List<String> suggestions = new ArrayList<String>();

        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        Log.d("test", "here");

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(s.toString())
                .build();

        Log.d("test", "here");

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
           for(AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                suggestions.add(prediction.getPrimaryText(null).toString());
               Log.d("test", prediction.getPrimaryText(null).toString());
           }
        }).addOnFailureListener((exception) -> {
            if(exception instanceof ApiException){
                //put error message here
                Log.d("test", exception.getMessage());
            }
        });

        Log.d("test", "here");

        ArrayAdapter<String> suggestionAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, suggestions);
        txtAddress.setAdapter(suggestionAdapter);
    }

    public boolean persistFields(){

        TreeMap<String, String> dataMap = ahemViewModel.getDataMap().getValue();

        Log.d("test", "1");

        for(int i = 0; i < textFields.length; i++){
            String tag = textFields[i].getTag().toString();
            String value = textFields[i].getText().toString();

            if(!value.equals("")){
                dataMap.put(tag, value);
            }else{
                return false;
            }
        }

        Log.d("test", "2");

        for(int i = 0; i < switchFields.length; i++){
            String tag = switchFields[i].getTag().toString();
            boolean isChecked = switchFields[i].isChecked();
            String value;

            if(tag.equals("SWDistanceTypeTag")){
                value = (isChecked ? "Time" : "Distance");
            }else if(tag.equals("SWDistanceUnitTag")){
                value = (isChecked ? "M" : "KM");
            }else{
                value = (isChecked ? "Silent" : "Voice");
            }

            dataMap.put(tag, value);

           /* if(!value.equals("")){
                dataMap.put(tag, value);
            }else{
                return false;
            }*/
        }

        Log.d("test", "3");

        for(int i = 0; i < radioButtonFields.length; i++){
            String tag = radioButtonFields[i].getTag().toString();
            boolean isChecked = radioButtonFields[i].isChecked();
            String value = (isChecked ? "ON" : "OFF");

            dataMap.put(tag, value);
        }

        Log.d("test", "4");

        ahemViewModel.setDataMap(dataMap);

        Log.d("test", "5");

        return true;

    }
}
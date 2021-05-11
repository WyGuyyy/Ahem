package com.me.ahem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import java.util.ArrayList;
import java.util.List;


public class reminder_add_fragment extends Fragment implements View.OnFocusChangeListener{

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

    Switch swDistanceType;
    Switch getSwDistanceUnit;
    Switch swSoundType;

    RadioButton rbCustom;
    RadioButton rbDefault;
    RadioButton rbPing;

    PlacesClient placesClient;

    public reminder_add_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_reminder_add_fragment, container, false);

        ahemViewModel = new ViewModelProvider(this, new AhemViewModelFactory(getActivity().getApplication())).get(AhemViewModel.class);

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

        swDistanceType = (Switch) view.findViewById(R.id.add_reminder_distance_type);
        getSwDistanceUnit = (Switch) view.findViewById(R.id.add_reminder_distance_unit);
        swSoundType = (Switch) view.findViewById(R.id.add_reminder_sound_type);

        rbCustom = (RadioButton) view.findViewById(R.id.add_reminder_sound_custom);
        rbDefault = (RadioButton) view.findViewById(R.id.add_reminder_sound_default);
        rbPing = (RadioButton) view.findViewById(R.id.add_reminder_sound_ping);

        txtLongitude.setOnFocusChangeListener(this);
        txtLatitude.setOnFocusChangeListener(this);

        txtAddress.setOnFocusChangeListener(this);
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Nothing to do
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSuggestions(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Nothing to do
            }
        });

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
        Log.d("test", splitArr[1]);
        String viewType = splitArr[0];
        String key = splitArr[1];
        String value = "";

        if(v instanceof EditText){
            value = ((EditText) v).getText().toString();
        }else if(v instanceof Switch){
            value = Boolean.toString(((Switch) v).isChecked());
        }else{
            value = Boolean.toString(((RadioButton) v).isChecked());
        }

        /*if(viewType.equals("ET")){
            value = ((EditText) v).getText().toString();
        }else if(viewType.equals("SW")){
            value = Boolean.toString(((Switch) v).isChecked());
        }else{
            value = Boolean.toString(((RadioButton) v).isChecked());
        }*/

        ahemViewModel.updateDataMap(key, value);

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
}
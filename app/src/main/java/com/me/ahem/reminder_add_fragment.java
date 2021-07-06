package com.me.ahem;

import android.Manifest;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.util.ULocale;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    FloatingActionButton playButton;

    EditText[] textFields;
    ToggleButton[] switchFields;
    RadioButton[] radioButtonFields;

    MediaRecorder recorder = null;
    String filename = "NONE";
    String filepath = "";

    boolean isRecording = false;

    PlacesClient placesClient;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    public reminder_add_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_reminder_add_fragment, container, false);

        ActivityCompat.requestPermissions(this.getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        isRecording = false;

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
        playButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonPlay);

        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#adadad")));
                playRecording();
            }
        });

        playButton.setEnabled(false);

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

        recordButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isRecording){
                    isRecording = false;
                    recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00b02f")));
                    stopRecording();
                    playButton.setEnabled(true);
                    playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00b02f")));
                }else{
                    isRecording = true;
                    recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7affa0")));
                    startRecording();
                    playButton.setEnabled(false);
                    playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#adadad")));
                }
            }
        });

       /* recordButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startRecording();
                return false;
            }
        });

        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    stopRecording();
                }

                return false;
            }
        });*/

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

                rbCustom.setChecked(!soundTypeState);
                rbDefault.setChecked(false);
                rbPing.setChecked(false);

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

        rbCustom.setChecked(true);

        rbCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbCustom.setChecked(true);
                rbDefault.setChecked(false);
                rbPing.setChecked(false);
                recordButton.setEnabled(true);
                playButton.setEnabled(true);
                recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00b02f")));
                playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00b02f")));
            }
        });

        rbDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean defaultState = ((RadioButton)v).isChecked();
                rbCustom.setChecked(false);
                rbDefault.setChecked(true);
                rbPing.setChecked(false);
                recordButton.setEnabled(false);
                playButton.setEnabled(false);
                recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#adadad")));
                playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#adadad")));
            }
        });

        rbPing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rbCustom.setChecked(false);
                rbDefault.setChecked(false);
                rbPing.setChecked(true);
                recordButton.setEnabled(false);
                playButton.setEnabled(false);
                recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#adadad")));
                playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#adadad")));
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
      String strAddress = address.getStreetNumber(); // + " " + address.getStreet() + " " + address.getCity() + " " + address.getState() + " " + address.getCountry() + " " + address.getZip();
      String name = reminder.getName();
      String description = reminder.getReminderDescription();
      String strDistance = Float.toString(location.getRadius());
      String hour = Integer.toString(location.getTime());
      String minute = Integer.toString(location.getTime());
      String second = Integer.toString(location.getTime());

      int iHour = (Integer.parseInt(hour) == -1 ? 0 : (location.getTime() / 3600));
      int iMinute = (Integer.parseInt(minute) == -1 ? 0 : (location.getTime() - (iHour * 3600))/60);
      int iSecond = (Integer.parseInt(second) == -1 ? 0 : ((location.getTime() - (iHour * 3600)) - (iMinute * 60)));

      hour = Integer.toString(iHour);
      minute = Integer.toString(iMinute);
      second = Integer.toString(iSecond);

      boolean distanceType = location.getDistanceType().equals("Radius") ? false : true;
      boolean distanceUnit = location.getDistanceUnit().equals("MI") ? false : true;
      boolean soundType = reminder.getSoundType().equals("Noise") ? false : true;

      boolean custom = reminder.getSoundSelection().equals("Custom");
      boolean aiVoice = reminder.getSoundSelection().equals("Default");
      boolean ping = reminder.getSoundSelection().equals("Ping");

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

      filepath = reminder.getSoundFilePath();

      if(!(filepath == null)){
          playButton.setEnabled(true);
          playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00b02f")));
      }

  }

    private void updateSuggestions(CharSequence s){

        List<String> suggestions = new ArrayList<String>();

        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

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

        for(int i = 0; i < textFields.length; i++){
            String tag = textFields[i].getTag().toString();
            String value = textFields[i].getText().toString();

            if(!value.equals("")){
                dataMap.put(tag, value);
            }else{
                if(tag.equals("distance_amount")){
                    if(swDistanceType.getText().equals("Radius")){
                        return false;
                    }else{
                        continue;
                    }
                }

                if(tag.equals("hour") || tag.equals("minute") || tag.equals("second")){
                    if(swDistanceType.getText().equals("Time")){
                        return false;
                    }else{
                        continue;
                    }
                }

                return false;
            }
        }

        for(int i = 0; i < switchFields.length; i++){
            String tag = switchFields[i].getTag().toString();
            boolean isChecked = switchFields[i].isChecked();
            String value;

            Log.d("leg", tag);

            if(tag.equals("distance_type")){
                value = (isChecked ? "Time" : "Radius");

            }else if(tag.equals("distance_unit")){
                value = (isChecked ? "M" : "KM");
            }else{
                value = (isChecked ? "Silent" : "Noise");
            }

            dataMap.put(tag, value);

           /* if(!value.equals("")){
                dataMap.put(tag, value);
            }else{
                return false;
            }*/
        }

        for(int i = 0; i < radioButtonFields.length; i++){
            String tag = radioButtonFields[i].getTag().toString();
            boolean isChecked = radioButtonFields[i].isChecked();
            String value = (isChecked ? "ON" : "OFF");

            dataMap.put(tag, value);
        }

        String tag = txtAddress.getTag().toString();
        String value = txtAddress.getText().toString();

        if(!value.equals("")){
            dataMap.put(tag, value);
        }else{
            return false;
        }

        if(dataMap.get("custom").equals("ON")){
            if(filepath.equals("")){
                return false;
            }else{
                dataMap.put("filepath", filepath);
            }
        }

        //dataMap.put("filepath", filename);

        ahemViewModel.setDataMap(dataMap);

        return true;

    }

    private void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        filepath = generateFilepath();
        recorder.setOutputFile(filepath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording(){
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void playRecording(){
        MediaPlayer mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00b02f")));
            }
        });

        try {
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFilepath(){

        if(!filepath.equals("")){
            return filepath;
        }

        File file = getContext().getFilesDir();
        String[] fileList = getContext().fileList();
        int fileID = 0;

        for(fileID = 0; fileID < fileList.length; fileID++){
            if(!Arrays.asList(fileList).contains("custom_sound" + fileID + ".mp4")){
                break;
            }
        }

        String filepath = file.getAbsolutePath() + "/custom_sound" + fileID + ".mp4";

        return filepath;
    }
}
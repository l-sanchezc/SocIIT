package com.sociit.app.sociit.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sociit.app.sociit.MyApplication;
import com.sociit.app.sociit.R;
import com.sociit.app.sociit.entities.Activity;
import com.sociit.app.sociit.entities.Building;
import com.sociit.app.sociit.entities.Comment;
import com.sociit.app.sociit.entities.User;
import com.sociit.app.sociit.helpers.SqlHelper;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddActivityFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String buildingId="";

    private OnFragmentInteractionListener mListener;

    private Spinner spinner;
    private EditText activityName;
    private EditText activityDescription;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Date datePickerDate;
    List<User> userList = new ArrayList<>();


    SqlHelper db;

    public AddActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddActivityFragment newInstance(String param1, String param2) {
        AddActivityFragment fragment = new AddActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new SqlHelper(getActivity().getApplicationContext());
            if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_activity, container, false);

        //Spinner list and creation
        List<String> buildingsArray = new ArrayList<String>();
        buildingsArray.add("Select a place for the activity...");
        buildingsArray.add("MTCC");
        buildingsArray.add("Stuart Building");
        buildingsArray.add("Hermann Hall");
        buildingsArray.add("S. R. Crown Hall");
        buildingsArray.add("Paul V Galvin Library");
        buildingsArray.add("Keating Sports Center");
        buildingsArray.add("VanderCook College of Music");
        buildingsArray.add("IIT Tower");
        buildingsArray.add("Life Sciences Building");
        buildingsArray.add("Perlstein Hall");
        buildingsArray.add("Wishnick Hall");

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, buildingsArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(parent.getItemAtPosition(pos).toString().equals("Select a place for the activity...")) {
                    buildingId="";}

                    else for(int i=1;i<12;i++)
                        if (pos==i){
                            buildingId=parent.getItemAtPosition(pos).toString();
                        }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Text fields creation
        activityName = (EditText) view.findViewById(R.id.activityName);
        activityDescription = (EditText) view.findViewById(R.id.activityDescription);

        //DatePicker and TimePicker initialization and parameters
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        datePickerDate = new Date();
        try {
            datePickerDate = formatter.parse("DAY"+month+" "+day+" "+hour+" "+timePicker.getCurrentMinute()+" 00 EDT "+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Logic for create activity button
        Button createActivityButton = (Button) view.findViewById(R.id.createActivityButton);
        createActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (verifyFields()){
                    Toast.makeText(getContext(), "TEST~ "+datePickerDate , Toast.LENGTH_SHORT).show();
                    //activity created with the parameters entered by the user
   //IMPLEMENTAR ACTIVITY                 Activity activity = new Activity(db.getAllActivities().size()+1, activityName.getText(), db.getBuildingByName(buildingId), datePickerDate.parse(""), , null);
                    //activity added to the database
           //         db.addActivity(activity);
                }
            }
        });


        return view;
    }


    //Method to verify that all fields are correctly filled
    public boolean verifyFields(){
        boolean validFields=true;

        if (activityName.length()<1){
            activityName.setError("An activity name must be entered");
            activityName.requestFocus();
            validFields=false;}
         if(activityDescription.length()<1){
                activityDescription.setError("A description for the activity must be entered");
                activityName.requestFocus();
                validFields=false;}
         if(buildingId.equals("")){
                    activityName.requestFocus();
                    Toast.makeText(getContext(), "A place for the activity must be selected" , Toast.LENGTH_SHORT).show();
                    validFields=false;
                }

        return validFields;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

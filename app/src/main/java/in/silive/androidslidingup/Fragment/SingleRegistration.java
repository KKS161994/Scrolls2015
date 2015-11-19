package in.silive.androidslidingup.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import in.silive.androidslidingup.Adapter.SpinnerAdapter;
import in.silive.androidslidingup.Config;
import in.silive.androidslidingup.R;
import in.silive.androidslidingup.network.FetchDataforLists;
import in.silive.androidslidingup.network.NetworkResponseListener;

/**
 * Created by kone on 23/9/15.
 */
public class SingleRegistration extends Fragment implements View.OnClickListener, NetworkResponseListener {
    Button submit;
    Spinner course, year, college;
    SpinnerAdapter courseAdapter, yearAdapter, collegeAdapter;
    ArrayList<String> collegeArray = new ArrayList<>();
    String[] courseArray, yearArray;
    FetchDataforLists fetchdataforLists;
    JSONObject jsonObject = new JSONObject();
    ArrayList<String> searchlIST = new ArrayList<>();
    ProgressBar progressBar;
    String inProgress = "";
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.single_registration, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        courseArray = getActivity().getResources().getStringArray(R.array.course_array);
        yearArray = getActivity().getResources().getStringArray(R.array.year_array);

        courseAdapter = new SpinnerAdapter(getActivity(), new ArrayList<String>(Arrays.asList(courseArray)));
        yearAdapter = new SpinnerAdapter(getActivity(), new ArrayList<String>(Arrays.asList(yearArray)));
        course = (Spinner) rootView.findViewById(R.id.courseCategories);
        course.setAdapter(courseAdapter);
        college = (Spinner) rootView.findViewById(R.id.college);
        year = (Spinner) rootView.findViewById(R.id.year);
        year.setAdapter(yearAdapter);
        submit = (Button) rootView.findViewById(R.id.SubmitSingleRegistration);
        submit.setOnClickListener(this);
        fetchdataforLists = new FetchDataforLists();
        try {
            inProgress = "GETALLCOLLEGES";
            searchlIST = new ArrayList<>();
            searchlIST.add("CollegeId");
            searchlIST.add("CollegeName");
            fetchdataforLists.setNrl(this);
            fetchdataforLists.setURL(Config.GET_COLLEGES);
            fetchdataforLists.setSearchForIds(false);
            fetchdataforLists.setType_of_request(Config.GET);
            fetchdataforLists.setSearchList(searchlIST);
            fetchdataforLists.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void beforeRequest() throws MalformedURLException {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postRequest(Object result) throws MalformedURLException {

        progressBar.setVisibility(View.GONE);
        final ArrayList<HashMap<String, String>> resultList = (ArrayList<HashMap<String, String>>) result;
        //Toast.makeText(getActivity(), "Length " + resultList.size() + " " + inProgress+" "+resultList.get(0).get(searchlIST.get(0)), Toast.LENGTH_SHORT).show();
        if (inProgress.equals("CHECK_EMAIL")) {
            if ((resultList.get(0).get(searchlIST.get(0))).equals("false")) {
                try {
                    if (((rootView.findViewById(R.id.otherCollege)).getVisibility()) == (View.VISIBLE))
                        checkOtherCollege();
                    else
                        selfRegister();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Email already Registered", Toast.LENGTH_SHORT).show();
            }
        } else if (inProgress.equals("GETALLCOLLEGES")) {
            for (int i = 0; i < resultList.size(); i++) {
                Log.d("Adding Colleges", resultList.get(i).get(searchlIST.get(1)));
                collegeArray.add(resultList.get(i).get(searchlIST.get(1)));
            }
            collegeArray.add("Others");
            collegeAdapter = new SpinnerAdapter(getActivity(), collegeArray);
            college.setAdapter(collegeAdapter);
            college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == (collegeArray.size() - 1)) {
                        (rootView.findViewById(R.id.otherCollege)).setVisibility(View.VISIBLE);
                    } else {
                        (rootView.findViewById(R.id.otherCollege)).setVisibility(View.GONE);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else if (inProgress.equals("CREATE_NEW_COLLEGE")) {
            if (resultList.size() > 0) {
                Toast.makeText(getActivity(), "College Added", Toast.LENGTH_SHORT).show();
                collegeArray.set(collegeArray.size() - 1, resultList.get(0).get(searchlIST.get(0)));
                collegeArray.add("Others");
           /*
                collegeAdapter = new SpinnerAdapter(getActivity(), collegeArray);
                college.setAdapter(collegeAdapter);
           */
                collegeAdapter.notifyDataSetChanged();

                try {
                    selfRegister();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (inProgress.equals("SELF_REGISTER")) {
            if (resultList.size() > 0) {
         final String id=resultList.get(0).get(searchlIST.get(0));
                new AlertDialog.Builder(getActivity())
                        .setTitle("SuccessFull Registration")
                        .setMessage("Congratulations" + resultList.get(0).get(searchlIST.get(1)) + " your registration is successful.\n Your" +
                                "registration id is " + resultList.get(0).get(searchlIST.get(0)) +
                                "To continue click OK")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Config.PREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Config.individual_id, id);
                                editor.commit();      }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                Toast.makeText(getActivity(), "Request Unsuccessfu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.SubmitSingleRegistration:
                /*if ((college.getSelectedItem().toString()).equals(collegeArray.length - 1)) {
                    jsonObject.put(getSpinnerText(college))
                }*/
                try {
                    inProgress = "CHECK_EMAIL";
                    searchlIST = new ArrayList<>();
                    searchlIST.add("IsEmailAlreadyRegistered");
                    //Log.d("FETCH_DATA", ((EditText) rootView.findViewById(R.id.otherCollegeText)).getText().toString() + jsonObject);
                    fetchdataforLists = null;
                    fetchdataforLists = new FetchDataforLists();
                    fetchdataforLists.setType_of_request(Config.GET);
                    fetchdataforLists.setURL(Config.IS_EMAIL_ALREADY_REGISTERED + ((EditText) rootView.findViewById(R.id.emailidet)).getText().toString()
                    );
                    fetchdataforLists.setNrl(this);
                    fetchdataforLists.setSearchList(searchlIST);
                    fetchdataforLists.execute();

             
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void checkOtherCollege() throws JSONException, MalformedURLException {
        searchlIST = new ArrayList<>();
        searchlIST.add("CollegeName");
        inProgress = "CREATE_NEW_COLLEGE";
        jsonObject = new JSONObject();
        jsonObject.put("CollegeId", "1");
        jsonObject.put("CollegeName", ((EditText) rootView.findViewById(R.id.otherCollegeText)).getText().toString());
        Log.d("FETCH_DATA", ((EditText) rootView.findViewById(R.id.otherCollegeText)).getText().toString() + jsonObject);
        fetchdataforLists = null;
        fetchdataforLists = new FetchDataforLists();
        fetchdataforLists.setType_of_request(Config.POST);
        fetchdataforLists.setURL(Config.CREATE_COLLEGE);
        fetchdataforLists.setNrl(this);
        fetchdataforLists.setJson(jsonObject);
        fetchdataforLists.setSearchList(searchlIST);
        fetchdataforLists.execute();

    }

    public void selfRegister() throws JSONException, MalformedURLException {
        inProgress = "SELF_REGISTER";
        searchlIST = new ArrayList<>();
        searchlIST.add("RegId");
        searchlIST.add("Name");
        jsonObject = new JSONObject();
        jsonObject.put("Name", ((EditText) rootView.findViewById(R.id.name_et)).getText().toString());
        jsonObject.put("CourseId", (Integer.parseInt(course.getSelectedItem().toString())) + 1);
        jsonObject.put("Year", (Integer.parseInt(year.getSelectedItem().toString() ))+ 1);
        jsonObject.put("CollegeId", (Integer.parseInt(college.getSelectedItem().toString() ))+ 1);
        jsonObject.put("StudentId", ((EditText) rootView.findViewById(R.id.studentid_et)).getText().toString());
        jsonObject.put("MobileNo", ((EditText) rootView.findViewById(R.id.mobilenumberet)).getText().toString());
        jsonObject.put("EmailId", ((EditText) rootView.findViewById(R.id.emailidet)).getText().toString());
        if (((CheckBox) rootView.findViewById(R.id.accomodation)).isChecked())
            jsonObject.put("AccomodationRequired", 1);
        else
            jsonObject.put("AccomodationRequired", 0);

                    /*jsonObject.put("CollegeId", "1");
                    jsonObject.put("CollegeName", ((EditText) rootView.findViewById(R.id.otherCollegeText)).getText().toString());
                    */
        Log.d("FETCH_DATA", ((EditText) rootView.findViewById(R.id.otherCollegeText)).getText().toString() + jsonObject);
        fetchdataforLists = null;
        fetchdataforLists = new FetchDataforLists();
        fetchdataforLists.setType_of_request(Config.POST);
        fetchdataforLists.setURL(Config.SELF_REGISTRATION);
        fetchdataforLists.setNrl(this);
        fetchdataforLists.setJson(jsonObject);
        fetchdataforLists.setSearchList(searchlIST);
        fetchdataforLists.execute();

    }

    public static String getSpinnerText(Spinner spinner) {
        TextView textView = (TextView) spinner.getSelectedView().findViewById(R.id.spinner_text);
        return textView.getText().toString();
    }
}

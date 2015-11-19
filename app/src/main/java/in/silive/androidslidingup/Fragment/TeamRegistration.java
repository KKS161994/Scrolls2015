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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
public class TeamRegistration extends Fragment implements View.OnClickListener, NetworkResponseListener {
    View rootView;
    String task = "CHECK_SCROLLS_ID";
    int inProgress = 0;
    int maxProgress = 2;
    FetchDataforLists fetchDataforLists;
    ArrayList<String> searchList = new ArrayList<>();
    ProgressBar progressBar;
    int leader_number = 0;

    JSONObject jsonObject;
    String[] domainArray = new String[5], topicArray = new String[13];
    SpinnerAdapter topicsAdapter, domainAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.team_registration, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        domainArray = getActivity().getResources().getStringArray(R.array.domain_array);
        topicArray = getActivity().getResources().getStringArray(R.array.topic_array);
        topicsAdapter = new SpinnerAdapter(getActivity(), new ArrayList<String>(Arrays.asList(topicArray)));
        domainAdapter = new SpinnerAdapter(getActivity(), new ArrayList<String>(Arrays.asList(domainArray)));
        ((Spinner) rootView.findViewById(R.id.topics)).setAdapter(topicsAdapter);
        ((Spinner) rootView.findViewById(R.id.domain)).setAdapter(domainAdapter);
        ((RadioGroup) rootView.findViewById(R.id.radiogroupMembers)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.membertwo) {
                    rootView.findViewById(R.id.regidmemberthree).setVisibility(View.GONE);
                    rootView.findViewById(R.id.regidmemberthreeet).setVisibility(View.GONE);
                    rootView.findViewById(R.id.namememberthreeet).setVisibility(View.GONE);
                    rootView.findViewById(R.id.leaderthree).setVisibility(View.GONE);
                    maxProgress = 2;
                } else {
                    rootView.findViewById(R.id.regidmemberthree).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.regidmemberthreeet).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.namememberthreeet).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.leaderthree).setVisibility(View.VISIBLE);
                    maxProgress = 3;

                }
            }
        });
        ((RadioGroup) rootView.findViewById(R.id.teamleader)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.leaderone) {
                    leader_number = 0;
                } else if (i == R.id.leadertwo) {
                    leader_number = 1;
                } else if (i == R.id.leaderthree) {
                    leader_number = 2;
                }
            }
        });
        rootView.findViewById(R.id.createteam).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createteam:
                try {
                    task = Config.CHECK_TEAM_NAME_AVAILABLE;
                    inProgress = 0;
                    checkTeamNameAvailable();
               //     teamregistration();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
        }
    }

    public void isPersonAlreadyInTeam() {

    }

    @Override
    public void beforeRequest() throws MalformedURLException {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postRequest(Object result) throws MalformedURLException {
        ArrayList<HashMap<String, String>> resultList = (ArrayList<HashMap<String, String>>) result;
        progressBar.setVisibility(View.GONE);
        if (task.equals(Config.CHECK_TEAM_NAME_AVAILABLE)) {
            if (resultList.size() > 0) {

                if ((resultList.get(0).get(searchList.get(0))).equalsIgnoreCase(Config.TRUE)) {
                    AlertDialog.Builder errorDialg = new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Sorry team name already exists Please Choose another")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert);
                    errorDialg.show();
                } else {
                    Toast.makeText(getActivity(), "Team name provided"+resultList.get(0).get(searchList.get(0)), Toast.LENGTH_SHORT).show();
                    task = "CHECK_SCROLLS_ID";
                    teamregistration();
                }
            }
        }
        else if (task.equals("CHECK_SCROLLS_ID")) {
            if (resultList.size() > 0) {
                Toast.makeText(getActivity(), "Result of " + searchList.get(0) + " is " + resultList.get(0).get(searchList.get(0)), Toast.LENGTH_SHORT).show();

                if (((resultList.get(0)).get(searchList.get(0))).equals(Config.TRUE)) {
                    Toast.makeText(getActivity(), "Participant " + (inProgress ) + " is already in a team.Please choose some other member", Toast.LENGTH_SHORT).show();
                } else {
                    if (inProgress < maxProgress) {
                        Toast.makeText(getActivity(), "Member " + (inProgress ) + " not in team", Toast.LENGTH_SHORT).show();
                        Log.d("InProgress", "" + inProgress);
                        teamregistration();
                    } else {
                        Log.d("InProgress Inside", "" + inProgress);
                        try {
                            registerTeam();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        } else if (task.equals("REGISTER_TEAM")) {
            if (resultList.size() > 0) {
                final String id = resultList.get(0).get(searchList.get(0));
                new AlertDialog.Builder(getActivity())
                        .setTitle("SuccessFull Registration")
                        .setMessage("Congratulations Your Team has been successful registered. Your team id is " + resultList.get(0).get(searchList.get(0)))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Config.PREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Config.team_id, id);
                                editor.commit();
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Error in Registration. This may be due to Invalid credentials or Network Connection. Please Check..")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert);
                dialog.show();

            }
        }

    }

    public void checkTeamNameAvailable() throws MalformedURLException {
        searchList.clear();
        searchList.add(Config.CHECK_TEAM_NAME_AVAILABLE);
        fetchDataforLists = null;
        fetchDataforLists = new FetchDataforLists();
        fetchDataforLists.setNrl(this);
        fetchDataforLists.setSearchList(searchList);
        fetchDataforLists.setType_of_request(Config.GET);
        fetchDataforLists.setURL(Config.IS_TEAM_NAME_AVAILABALE + ((EditText) rootView.findViewById(R.id.teamname)).getText().toString());
        fetchDataforLists.execute();

    }

    public void teamregistration() throws MalformedURLException {
        if (inProgress < maxProgress) {

            searchList.clear();
            searchList.add("IsParticipantAlreadyInATeam");
            fetchDataforLists = null;
            fetchDataforLists = new FetchDataforLists();
            fetchDataforLists.setNrl(this);
            if (inProgress == 0)
                fetchDataforLists.setURL(Config.ISPERSONALREADYINTEAM + ((EditText) rootView.findViewById(R.id.regidmemberoneet)).getText().toString());
            else if (inProgress == 1)
                fetchDataforLists.setURL(Config.ISPERSONALREADYINTEAM + ((EditText) rootView.findViewById(R.id.regidmembertwoet)).getText().toString());
            if (inProgress == 2)
                fetchDataforLists.setURL(Config.ISPERSONALREADYINTEAM + ((EditText) rootView.findViewById(R.id.regidmembertwoet)).getText().toString());
            fetchDataforLists.setSearchList(searchList);
            fetchDataforLists.setType_of_request(Config.GET);
            inProgress++;
            fetchDataforLists.execute();
        }
    }

    private void registerTeam() throws MalformedURLException, JSONException {

        task = "REGISTER_TEAM";
        jsonObject = new JSONObject();
        jsonObject.put("TeamName", ((EditText) rootView.findViewById(R.id.teamname)).getText().toString());
        jsonObject.put("TotalMembers", maxProgress);
        jsonObject.put("Member1RegId", ((EditText) rootView.findViewById(R.id.regidmemberoneet)).getText().toString());
        jsonObject.put("Member2RegId", ((EditText) rootView.findViewById(R.id.regidmembertwoet)).getText().toString());
        if (maxProgress == 3)
            jsonObject.put("Member3RegId", ((EditText) rootView.findViewById(R.id.regidmemberthreeet)).getText().toString());
        jsonObject.put("DomainId", (Integer.parseInt(((Spinner) rootView.findViewById(R.id.domain)).getSelectedItem().toString()) + 1));
        jsonObject.put("TopicId", (Integer.parseInt(((Spinner) rootView.findViewById(R.id.topics)).getSelectedItem().toString()) + 1));
        jsonObject.put("Password", ((EditText) rootView.findViewById(R.id.password)).getText().toString());
        if (leader_number == 0)
            jsonObject.put("TeamLeader", ((EditText) rootView.findViewById(R.id.regidmemberoneet)).getText().toString());
        else if (leader_number == 1)
            jsonObject.put("TeamLeader", ((EditText) rootView.findViewById(R.id.regidmembertwoet)).getText().toString());

        else if (leader_number == 2)
            jsonObject.put("TeamLeader", ((EditText) rootView.findViewById(R.id.regidmemberthreeet)).getText().toString());
        jsonObject.put("SynopsisName", "");

        searchList.clear();
        searchList.add("TeamId");
        fetchDataforLists = null;
        fetchDataforLists = new FetchDataforLists();
        fetchDataforLists.setURL(Config.TEAM_REGISTRATION);
        fetchDataforLists.setNrl(this);
        fetchDataforLists.setSearchList(searchList);
        fetchDataforLists.setType_of_request(Config.POST);
        fetchDataforLists.setJson(jsonObject);

        fetchDataforLists.execute();

    }
}

package in.silive.androidslidingup.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import in.silive.androidslidingup.Config;
import in.silive.androidslidingup.R;
import in.silive.androidslidingup.network.FetchDataforLists;
import in.silive.androidslidingup.network.NetworkResponseListener;

/**
 * Created by kone on 24/9/15.
 */
public class ForgotIdFragment extends Fragment implements View.OnClickListener, NetworkResponseListener {
    View rootView;
    ProgressBar progressBar;
    FetchDataforLists fetchDataforLists;
    ArrayList<String> searchList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.forgotid, container, false);
        searchList.add(Config.CHECK_ID_BY_EMAIL);
        ((Button) rootView.findViewById(R.id.submit_forgotid)).setOnClickListener(this);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_forgotid:
                fetchDataforLists = new FetchDataforLists();
                try {
                    fetchDataforLists.setURL(Config.ID_BY_EMAIL + ((EditText) rootView.findViewById(R.id.email_forgotid)).getText().toString());
                    fetchDataforLists.setType_of_request(Config.GET);
                    fetchDataforLists.setSearchList(searchList);
                    fetchDataforLists.setNrl(this);
                    fetchDataforLists.execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void beforeRequest() throws MalformedURLException {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postRequest(Object result) throws MalformedURLException {
        progressBar.setVisibility(View.GONE);
        ArrayList<HashMap<String, String>> resultId = (ArrayList<HashMap<String, String>>) result;
        if (resultId.size() > 0) {
            if (!(resultId.get(0).get(searchList.get(0)).equals("0"))) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Id")
                        .setMessage("Your id is " + resultId.get(0).get(searchList.get(0)))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert);
                dialog.show();

            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Your email does not exist in Database")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert);
                dialog.show();

            }
        } else
            Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
}

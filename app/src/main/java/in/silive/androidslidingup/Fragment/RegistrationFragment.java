package in.silive.androidslidingup.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import in.silive.androidslidingup.R;

/**
 * Created by kone on 23/9/15.
 */
public class RegistrationFragment extends Fragment{
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registration, container, false);
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroupRegistrationType);
        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fragmetRegistrationType, new TeamRegistration(), "Board")
                .addToBackStack(null).commit();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boardradiobuttonLoad) {
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragmetRegistrationType, new TeamRegistration(), "Board").addToBackStack(null).commit();
                } else if (checkedId == R.id.boardradiobuttonTruck) {
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragmetRegistrationType, new SingleRegistration(), "Board").addToBackStack(null).commit();
                }
            }
        });
        return rootView;
    }

}

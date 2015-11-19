package in.silive.androidslidingup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import in.silive.androidslidingup.GyroSensor.SimulationView;
import in.silive.androidslidingup.R;

/**
 * Created by Shobhit Agarwal on 9/23/2015.
 */
public class Home extends Fragment {

    private SimulationView mSimulationView;

    @Override
    public void onResume() {
        super.onResume();
        mSimulationView.startSimulation();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mSimulationView = new SimulationView(getActivity());
        FrameLayout main_content = (FrameLayout) rootView.findViewById(R.id.main);
        main_content.removeAllViews();
        main_content.addView(mSimulationView);
        getChildFragmentManager().beginTransaction().add(R.id.sliding_up_panel, new Dashboard(), "").commit();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSimulationView.stopSimulation();
    }
}

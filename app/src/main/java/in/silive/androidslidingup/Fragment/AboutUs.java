package in.silive.androidslidingup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.silive.androidslidingup.R;

/**
 * Created by Shobhit Agarwal on 9/24/2015.
 */
public class AboutUs extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_us, container, false);

        return rootView;
    }
}

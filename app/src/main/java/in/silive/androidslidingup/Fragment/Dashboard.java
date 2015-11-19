package in.silive.androidslidingup.Fragment;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import in.silive.androidslidingup.Adapter.DashboardAdapter;
import in.silive.androidslidingup.R;
import in.silive.androidslidingup.models.ImageItem;

/**
 * Created by Shobhit Agarwal on 9/23/2015.
 */
public class Dashboard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);
        ListView dashboard = (ListView) rootView.findViewById(R.id.grid_menu);
        DashboardAdapter adapter = new DashboardAdapter(getActivity(), R.layout.grid_item_layout, getData());
        dashboard.setAdapter(adapter);
        dashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                CategoryPaper frag = new CategoryPaper();
                frag.setArguments(bundle);
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_frame, frag, "category paper")
                        .addToBackStack(null).setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).commit();
            }
        });
        return rootView;

    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.category_icons);
        String[] titles = getResources().getStringArray(R.array.category_titles);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, titles[i]));
        }
        return imageItems;
    }
}

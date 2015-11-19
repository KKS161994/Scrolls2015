package in.silive.androidslidingup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import in.silive.androidslidingup.R;

/**
 * Created by Shobhit Agarwal on 9/23/2015.
 */
public class CategoryPaper extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_items_list, container, false);
        String[] array = getActivity().getResources().getStringArray(R.array.category_titles);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        TextView title = (TextView) view.findViewById(R.id.heading);
        Bundle bundle = getArguments();
        int pos = bundle.getInt("position");
        title.setText(array[pos]);
        int id = 0;
        switch (pos) {
            case 0:
                id = R.array.management;
                break;
            case 1:
                id = R.array.cs_it;
                break;
            case 2:
                id = R.array.ec;
                break;
            case 3:
                id = R.array.en;
                break;
            case 4:
                id = R.array.me;
                break;
            case 5:
                id = R.array.ce;
                break;
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(id));
        listView.setAdapter(itemsAdapter);
        return view;
    }
}

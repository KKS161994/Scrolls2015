package in.silive.androidslidingup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.silive.androidslidingup.R;

/**
 * Created by kone on 23/9/15.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> title_array;
    public SpinnerAdapter(Context context,ArrayList<String> title_array)
    {
        this.context=context;
        this.title_array=title_array;
    }@Override
     public int getCount() {
        return title_array.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.spinner_item,null);
        TextView tv= (TextView) view.findViewById(R.id.spinner_text);
        tv.setText(title_array.get(i));
        return view;
    }
}
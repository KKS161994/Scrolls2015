package in.silive.androidslidingup.Adapter;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.silive.androidslidingup.R;

/**
 * Created by kone on 23/9/15.
 */
public class SlidingPaneDrawerAdapter extends BaseAdapter{
Context context;
    String[] title_array;
    public SlidingPaneDrawerAdapter(Context context,String[] title_array)
    {
        this.context=context;
        this.title_array=title_array;
    }@Override
    public int getCount() {
        return title_array.length;
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
        view=inflater.inflate(R.layout.drawer_list_item,null);
        TextView tv= (TextView) view.findViewById(R.id.tv_drawer);
        tv.setText(title_array[i]);
        return view;
    }
}

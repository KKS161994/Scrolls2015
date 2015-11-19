package in.silive.androidslidingup;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

<<<<<<< HEAD
import in.silive.androidslidingup.Fragment.AboutUs;
import in.silive.androidslidingup.Fragment.Home;
import in.silive.androidslidingup.Fragment.QueryUsFragment;
import in.silive.androidslidingup.Fragment.ReachUsFragment;
import in.silive.androidslidingup.Fragment.Register;
import in.silive.androidslidingup.Fragment.UploadADoc;
=======
import in.silive.androidslidingup.Adapter.SlidingPaneDrawerAdapter;
import in.silive.androidslidingup.Fragment.NewsFeedsFragment;
import in.silive.androidslidingup.Fragment.QueryUsFragment;
import in.silive.androidslidingup.Fragment.ReachUsFragment;
import in.silive.androidslidingup.Fragment.RegistrationFragment;
import in.silive.androidslidingup.Fragment.TeamRegistration;
>>>>>>> origin/kartikey

public class MainActivity extends AppCompatActivity {

    private String[] drawer_option_titles;
    Toolbar toolbar;
    private Fragment fragment;
<<<<<<< HEAD
    private PowerManager.WakeLock mWakeLock;
    private static final String TAG = "com.example.motionsensor";
    private android.support.v4.widget.SlidingPaneLayout slidingPane;
    private ImageView iv_toolbar;
=======
    SlidingPaneLayout slidingPaneLayout;
    ListView slidingDrawer;
    ImageView iv_toolbar;
    TextView app_title;
    SlidingPaneDrawerAdapter slidingPaneDrawerAdapter;
>>>>>>> origin/kartikey

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        iv_toolbar = (ImageView) toolbar.findViewById(R.id.iv_toolbar);
<<<<<<< HEAD
        TextView app_title = (TextView) toolbar.findViewById(R.id.app_title);
        app_title.setText("Scrolls");
        drawer_option_titles = getResources().getStringArray(R.array.drawer_options);
        slidingPane = (android.support.v4.widget.SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        slidingPane.setParallaxDistance(200);
        ListView slidingDrawer = (ListView) findViewById(R.id.sliding_drawer);
        selectItem(0);
        iv_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingPane.isOpen()) {
                    iv_toolbar.animate().rotation(0);
                    slidingPane.closePane();
                } else {
                    iv_toolbar.animate().rotation(90);
                    slidingPane.openPane();
                }
            }
        });
        SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener() {

            @Override
            public void onPanelClosed(View arg0) {
                // TODO Auto-generated method stub        getActionBar().setTitle(getString(R.string.app_name));
                iv_toolbar.animate().rotation(0);
            }

            @Override
            public void onPanelOpened(View arg0) {
                // TODO Auto-generated method stub
                iv_toolbar.animate().rotation(90);
            }

            @Override
            public void onPanelSlide(View arg0, float arg1) {
                // TODO Auto-generated method stub

            }

        };
        slidingDrawer.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawer_option_titles));
=======

        app_title = (TextView) toolbar.findViewById(R.id.app_title);
        app_title.setText("Scrolls");
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        drawer_option_titles = getResources().getStringArray(R.array.drawer_options);
        slidingPaneLayout.setPanelSlideListener(panelListener);
        slidingPaneLayout.setParallaxDistance(200);
        slidingDrawer = (ListView) findViewById(R.id.sliding_drawer);
        slidingPaneDrawerAdapter = new SlidingPaneDrawerAdapter(this, drawer_option_titles);
        slidingDrawer.setAdapter(slidingPaneDrawerAdapter);
>>>>>>> origin/kartikey
        slidingDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
<<<<<<< HEAD
        PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWakeLock.release();
=======
        selectItem(0);
        iv_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingPaneLayout.isOpen()) {
                    iv_toolbar.animate().rotation(0);
                    slidingPaneLayout.closePane();
                } else {
                    iv_toolbar.animate().rotation(90);
                    slidingPaneLayout.openPane();
                }
            }
        });
>>>>>>> origin/kartikey
    }

    SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener() {

        @Override
        public void onPanelClosed(View arg0) {
            // TODO Auto-genxxerated method stub        getActionBar().setTitle(getString(R.string.app_name));
            iv_toolbar.animate().rotation(0);
        }

        @Override
        public void onPanelOpened(View arg0) {
            // TODO Auto-generated method stub
            iv_toolbar.animate().rotation(90);
        }

        @Override
        public void onPanelSlide(View arg0, float arg1) {
            // TODO Auto-generated method stub

        }

    };

    @Override
    public void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    private void selectItem(int position) {
        Bundle args = new Bundle();
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                fragment = new Home();
                args.putInt("Number", position);
                break;
            case 1:
                fragment = new Register();
                args.putInt("Number", position);
                break;
            case 2:
                fragment = new UploadADoc();
                args.putInt("Number", position);
                break;
            case 1:
                fragment=new RegistrationFragment();
                args.putInt("Number",position);
                break;
            case 3:
                fragment = new QueryUsFragment();
                args.putInt("Number", position);
                break;
            case 4:
                fragment = new ReachUsFragment();
                args.putInt("Number", position);
                break;
            case 5:
                fragment = new AboutUs();
                args.putInt("Number", position);
                break;
            default:
                break;
        }
<<<<<<< HEAD
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        closeDrawer();
        setTitle(drawer_option_titles[position]);
    }
=======
        // update selected item and title, then close the drawer
        if (position == 0 || position == 4 || position == 3||position==1) {
            fragment.setArguments(args);
>>>>>>> origin/kartikey

    private void closeDrawer() {
        iv_toolbar.animate().rotation(0);
        slidingPane.closePane();

<<<<<<< HEAD
=======
            //    setTitle(drawer_option_titles[position]);
        }
        closeDrawer();
    }

    private void closeDrawer() {
        iv_toolbar.animate().rotation(0);
        slidingPaneLayout.closePane();

>>>>>>> origin/kartikey
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package co.mobiwise.dotlinewatchface;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.wearable.PutDataRequest;
import com.mobiwise.Commons;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.mobiwise.dotlinewatchface.adapter.MyRecyclerAdapter;
import co.mobiwise.dotlinewatchface.ui.DotLineView;
import model.DotLinePreferences;

public class WearControlActivity extends ActionBarActivity implements MyRecyclerAdapter.OnItemClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.view_dotline)
    DotLineView mDotLineView;

    private MyRecyclerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private DotLinePreferences mDotLinePreferences;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_control);

        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAdapter = new MyRecyclerAdapter(getDefaultColors(),getApplicationContext());
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        mDotLinePreferences = DotLinePreferences.newInstance(getApplicationContext());
        mDotLineView.changeDotLineColor(mDotLinePreferences.getBackgroundColour());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wear_control, menu);
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

    private int[] getDefaultColors(){
        int[] colorset = getApplicationContext().getResources().getIntArray(R.array.colorset);
        return colorset;
    }

    @Override
    public void onItemClicked(View view, int position) {
        mDotLinePreferences.setBackgroundColour(getDefaultColors()[position]);
        mDotLineView.changeDotLineColor(getDefaultColors()[position]);

        //Sends data to wear
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(Commons.PATH);
        putDataMapReq.getDataMap().putInt(Commons.KEY_BACKGROUND_COLOUR, getDefaultColors()[position]);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("TEST","onConnected");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("TEST","onConnectionSuspended");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("TEST","onConnectionFailed");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }
}

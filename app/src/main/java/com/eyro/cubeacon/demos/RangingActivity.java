package com.eyro.cubeacon.demos;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.graphics.Color;
import android.widget.TextView;

import com.eyro.cubeacon.CBBeacon;
import com.eyro.cubeacon.CBRangingListener;
import com.eyro.cubeacon.CBRegion;
import com.eyro.cubeacon.CBServiceListener;
import com.eyro.cubeacon.Cubeacon;
import com.eyro.cubeacon.SystemRequirementManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RangingActivity extends AppCompatActivity implements CBRangingListener, CBServiceListener, AdapterView.OnItemClickListener {

    private static final String TAG = RangingActivity.class.getSimpleName();
    private Cubeacon cubeacon;
    private GridView gridview;
    private List<CBBeacon> beacons;

    RangingAdapter adapter;
    List<RangingModel> rangingModels = new ArrayList<RangingModel>();
    String title, subtitle, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);
        gridview = (GridView) findViewById(R.id.gridView);
        RangingAdapter adapter = new RangingAdapter(this, R.layout.gridview_items, rangingModels);
        gridview.setAdapter(adapter);
        // set listview on item click listener
        gridview.setOnItemClickListener(this);


        // assign local instance of Cubeacon manager
        cubeacon = Cubeacon.getInstance();
    }

    private void setRangingData(List<RangingModel> list, String title, String subtitle, String status){
        list.add(new RangingModel(title, subtitle, status));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check all requirement like is BLE available, is bluetooth on/off,
        // location service for Android API 23 or later
        if (SystemRequirementManager.checkAllRequirementUsingDefaultDialog(this)) {
            // connecting to Cubeacon service when all requirements completed
            cubeacon.connect(this);
            // disable background mode, because we're going to use full
            // scanning resource in foreground mode
            cubeacon.setBackgroundMode(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // enable background mode when this activity paused
        cubeacon.setBackgroundMode(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // disconnect from Cubeacon service when this activity destroyed
        cubeacon.disconnect(this);
    }

    @Override
    public void didRangeBeaconsInRegion(final List<CBBeacon> beacons, CBRegion region) {
        this.beacons = beacons;

        rangingModels.clear();

        for (CBBeacon beacon : beacons) {
            status = "FAR";
            title = beacon.getProximityUUID().toString().toUpperCase();
            subtitle = String.format(Locale.getDefault(), "Distance:%.2fm", beacon.getAccuracy());
            Log.d(TAG, "didRangeBeaconsInRegion: " + beacon.getAccuracy());

            // menampilkan status distance
            if (beacon.getAccuracy() < 0.5 ) {
                status = "IMMEDIATE";
//                listView.setBackgroundColor(Color.parseColor("#FF9AD082"));
            }
            setRangingData(rangingModels, title, subtitle, status);
        }

//        adapter.setRangingItems(rangingModels);

        // update view using runnable
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gridview.invalidateViews();
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setSubtitle("Amount Cubical Dosen : " + beacons.size());
                    }
            }
        });
    }

    @Override
    public void onBeaconServiceConnect() {
        // add ranging listener implementation
        cubeacon.addRangingListener(this);
        try {
            // create a new region for ranging beacons
            CBRegion region = new CBRegion("com.eyro.cubeacon.ranging_region");
            // start ranging beacons using region
            cubeacon.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            Log.e(TAG, "Error while start ranging beacon, " + e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CBBeacon beacon = this.beacons.get(i);
        Intent intent = new Intent(this, MonitoringActivity.class);
        intent.putExtra(MonitoringActivity.INTENT_BEACON, beacon);
        startActivity(intent);
    }


}

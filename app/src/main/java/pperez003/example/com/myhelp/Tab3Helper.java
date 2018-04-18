package pperez003.example.com.myhelp;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;

import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by pperez003 on 12/04/2018.
 */

public class Tab3Helper extends Fragment implements BeaconConsumer {
    //Relative Layout
    RelativeLayout rl;
    //Recycler View
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    //Beacon Manager
    private BeaconManager beaconManager;
    // Progress bar
    private ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        //getting beaconManager instance (object) for Main Activity class
        beaconManager = BeaconManager.getInstanceForApplication (getActivity ( ));

        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers ( ).add (new BeaconParser ( ).
                setBeaconLayout ("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        //Binding MainActivity to the BeaconService.

        beaconManager.bind (this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate (R.layout.tab3helper, container, false);

        // Intializing the Layout

        //Relative Layout
        rl = v.findViewById (R.id.Relative_One);

        // Recycler View
        rv = v.findViewById (R.id.search_recycler);

        //Progress Bar
        // pb = v.findViewById(R.id.pb);
        return v;
    }


    public void onBeaconServiceConnect() {
        final Region region = new  Region("myBeaons",null, null, null);

        beaconManager.addMonitorNotifier (new MonitorNotifier ( ) {

            public void didEnterRegion(Region region) {
                try {
                    // Log.d(TAG, "didEnterRegion");
                    beaconManager.startRangingBeaconsInRegion (region);
                } catch (RemoteException e) {
                    e.printStackTrace ( );
                }
            }


            public void didExitRegion(Region region) {
                try {
                    //Log.d(TAG, "didExitRegion");
                    beaconManager.stopRangingBeaconsInRegion (region);
                } catch (RemoteException e) {
                    e.printStackTrace ( );
                }
            }




            public void didDetermineStateForRegion(int state, Region region) {
                System.out.println( "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        beaconManager.addRangeNotifier (new RangeNotifier ( ) {

            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
              //  Log.d(TAG, "distance: " + oneBeacon.getDistance() + " id:" + oneBeacon.getId1() + "/" + oneBeacon.getId2() + "/" + oneBeacon.getId3());
                /*
                for (Beacon oneBeacon : beacons) {
                    System.out.println ("Major value =" +oneBeacon.getId2 ()+ "size =" +beacons.size () + "*");
                }
                */
                if(beacons.size()>0){
                   //System.out.print("**"+beacons.size()+"**");
                    try{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Make ProgressBar Invisible
                                //pb.setVisibility(View.INVISIBLE);

                                // Make Relative Layout to be Gone
                                rl.setVisibility(View.GONE);

                                //Make RecyclerView to be visible
                                rv.setVisibility(View.VISIBLE);

                                // Setting up the layout manager to be linear
                                layoutManager = new LinearLayoutManager(getActivity());
                                rv.setLayoutManager(layoutManager);
                            }
                        });
                    }
                    catch(Exception e){

                    }
                    final ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();

                    // Iterating through all Beacons from Collection of Beacons
                    for (Beacon b:beacons){

                        //UUID
                        String uuid = String.valueOf(b.getId1());

                        //Major
                        String major = String.valueOf(b.getId2());

                        //Minor
                        String minor = String.valueOf(b.getId3());
                        // test

                        //Distance
                        double distance1 =b.getDistance();
                        String distance = String.valueOf(Math.round(distance1*100.0)/100.0);

                        ArrayList<String> arr = new ArrayList<String>();
                        arr.add(uuid);
                        arr.add(major);
                        arr.add(minor);
                        arr.add(distance + " meters");
                        arrayList.add(arr);
                        //System.out.print("**"+b.getId1()+"**");
                        //System.out.print("**"+arrayList.size()+"**");
                    }

                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Setting Up the Adapter for Recycler View
                                adapter = new RecyclerAdapter(arrayList);
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }catch(Exception e){

                    }




                    //fin

                }
            }

        });

        try {
            beaconManager.startMonitoringBeaconsInRegion (region);
        } catch (RemoteException e) {
            e.printStackTrace ( );
        }

    }










    /*
         If we are implementing the BeaconConsumer interface in a Fragment
        (and not an Activity, Service or Application instance),
         we need to chain all of the methods.
     */
    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent, serviceConnection, i);
    }


    // Override onDestroy Method
    @Override
    public void onDestroy() {
        super.onDestroy();
        //Unbinds an Android Activity or Service to the BeaconService to avoid leak.
        beaconManager.unbind(this);
    }


}








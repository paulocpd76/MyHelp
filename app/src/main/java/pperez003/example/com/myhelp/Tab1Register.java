package pperez003.example.com.myhelp;

/**
 * Created by pperez003 on 12/04/2018.
 */


import android.os.Build;
import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import java.util.Arrays;
        import org.altbeacon.beacon.Beacon;
        import org.altbeacon.beacon.BeaconParser;
        import org.altbeacon.beacon.BeaconTransmitter;
        import android.bluetooth.BluetoothAdapter;
import android.widget.TextView;

public class Tab1Register extends  Fragment {


    private BluetoothAdapter blVer;
    private TextView device;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1reg, container, false);
        device = rootView.findViewById(R.id.device);
        blVer = BluetoothAdapter.getDefaultAdapter();
        boolean le2MPhySupported;
        //device.setText("hola");
        if (Build.VERSION.SDK_INT >= 21) {
            // Call some material design APIs here
            device.setText("supported");
            Beacon beacon = new Beacon.Builder()
                    .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                    .setId2("1")
                    .setId3("2")
                    .setManufacturer(0x0118)
                    .setTxPower(-59)
                    .setDataFields(Arrays.asList(new Long[] {0l}))
                    .build();
            BeaconParser beaconParser = new BeaconParser()
                    .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
            BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getActivity(), beaconParser);
            beaconTransmitter.startAdvertising(beacon);
        } else {
            // Implement this feature without material design
            device.setText("Not supported");
        }





        return rootView;
    }
}
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
        import org.altbeacon.beacon.Beacon;
        import org.altbeacon.beacon.BeaconParser;
        import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.Identifier;

import android.bluetooth.BluetoothAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
public class Tab1Register extends  Fragment {


    private BluetoothAdapter blVer;
    private TextView device,helpId;
    private EditText userText,emerNumber;
    private Button buttoSos;
    public static final Identifier MY_MATCHING_IDENTIFIER = Identifier.fromInt(0x8b9c);
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1reg, container, false);
        device = rootView.findViewById(R.id.device);
        blVer = BluetoothAdapter.getDefaultAdapter();
        userText=rootView.findViewById(R.id.userText);
        emerNumber=rootView.findViewById(R.id.emerNumber);
        buttoSos=rootView.findViewById(R.id.buttoSos);
        helpId=rootView.findViewById(R.id.helpId);
        buttoSos.setEnabled(false);
        boolean le2MPhySupported;
        //device.setText("hola");
        if (Build.VERSION.SDK_INT >= 21) {
            // Call some material design APIs here
            //device.setText("supported");
            buttoSos.setEnabled(true);
            buttoSos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stringToTransmit =  userText.getText().toString() +"/" +emerNumber.getText().toString();
                    byte[] stringToTransmitAsAsciiBytes = stringToTransmit.getBytes(StandardCharsets.US_ASCII);


                    //Beacon from screm string data
                    Beacon beacon = new Beacon.Builder()

                            //.setId1(MY_MATCHING_IDENTIFIER.toString())
                            .setId1(Identifier.fromBytes(stringToTransmitAsAsciiBytes, 0, 16, false).toString())
                            //.setId2(Identifier.fromBytes(stringToTransmitAsAsciiBytes, 0, 4, false).toString())
                            .setId2("3")
                            .setId3("2")
                            .setManufacturer(0x0118)
                            .setTxPower(-59)
                            .setDataFields(Arrays.asList(new Long[] {255l}))
                            .setBluetoothName(Identifier.fromBytes(stringToTransmitAsAsciiBytes, 0, 5, false).toString())
                            .build();




                    BeaconParser beaconParser = new BeaconParser()
                            .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
                    BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getActivity(), beaconParser);
                    beaconTransmitter.startAdvertising(beacon);

                        /*
                        Orginal Beacon
                        Beacon beacon = new Beacon.Builder()
                                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                                .setId2("1")
                                .setId3("2")
                                .setManufacturer(0x0118)
                                .setTxPower(-59)
                                .setDataFields(Arrays.asList(new Long[] {255l}))
                                .setBluetoothName("Paulo")
                                .build();
                        */
                    helpId.setText("Help Active");
                }
            });

        } else {
            // Implement this feature without material design
            device.setText("This device not supported");
        }





        return rootView;
    }
}
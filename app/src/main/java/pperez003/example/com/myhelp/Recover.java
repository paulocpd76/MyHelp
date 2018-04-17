package pperez003.example.com.myhelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pperez003 on 12/04/2018.
 */

public class Recover extends Fragment{
    private TextView helper;
    public Recover(){}
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3helper, container, false);
        helper.setText("Helper");

        return rootView;
    }
}

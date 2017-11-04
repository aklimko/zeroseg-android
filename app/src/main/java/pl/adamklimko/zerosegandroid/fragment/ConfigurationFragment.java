package pl.adamklimko.zerosegandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.adamklimko.zerosegandroid.R;

public class ConfigurationFragment extends Fragment {


    public ConfigurationFragment() {
        // Required empty public constructor
    }

    public static ConfigurationFragment newInstance() {
        return new ConfigurationFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }

}

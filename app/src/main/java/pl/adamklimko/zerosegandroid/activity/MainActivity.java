package pl.adamklimko.zerosegandroid.activity;

import android.os.Bundle;
import pl.adamklimko.zerosegandroid.R;
import pl.adamklimko.zerosegandroid.fragment.MessageFragment;
import pl.adamklimko.zerosegandroid.rest.ApiClient;
import pl.adamklimko.zerosegandroid.rest.ZerosegService;

public class MainActivity extends DrawerActivity implements FragmentCommunicator {

    private ZerosegService zerosegService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        zerosegService = ApiClient.createServiceWithAuth(ZerosegService.class, this);

        if (savedInstanceState == null) {
            final MessageFragment messageFragment = MessageFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, messageFragment)
                    .commit();
        }
    }

    @Override
    public ZerosegService getZerosegService() {
        return zerosegService;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zerosegService = null;
    }
}

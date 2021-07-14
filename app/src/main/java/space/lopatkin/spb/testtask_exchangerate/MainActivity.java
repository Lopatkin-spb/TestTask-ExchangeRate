package space.lopatkin.spb.testtask_exchangerate;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import space.lopatkin.spb.testtask_exchangerate.ui.Fragment;
import space.lopatkin.spb.testtask_exchangerate.utils.NetworkReceiver;

public class MainActivity extends AppCompatActivity {
    public static final String http = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String TAG = "myLogs";
    public static final int DIALOG_NO_INTERNET = R.string.dialog_no_internet;
    public static final int TOAST_ERROR_LOADING = R.string.toast_error_loading;
    public static final String TEXT_VIEW_TITLE = "Курс валюты ЦБРФ от ";

    public static final String KEY_IS_APP_TURN = "isTurn";
    public static final String KEY_IS_LOADER_STARTED = "isLoaderStarted";
    public static final String KEY_HTTP = "url";
    public static final String TARGET_VALUTE = "Японских иен";
    private NetworkReceiver receiver = new NetworkReceiver();
    private Fragment fragment =new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState ==null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();


//            fragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, fragmentFacts)
//                    .addToBackStack("backstack")
//                    .commit();

        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

}
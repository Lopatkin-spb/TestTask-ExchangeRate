package space.lopatkin.spb.testtask_exchangerate;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
//import android.support.v4.;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import space.lopatkin.spb.testtask_exchangerate.ui.Fragment;
import space.lopatkin.spb.testtask_exchangerate.utils.NetworkReceiver;

public class MainActivity extends AppCompatActivity {

    // залогировать ошибки- обработать
    //вывести статистику изменения выбранной валюты на другом фрагменте
    // доработать текстовые вью

    public static final String http = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String TAG = "myLogs";
    public static final int DIALOG_NO_INTERNET = R.string.dialog_no_internet;
    public static final int DIALOG_ERROR_LOADING = R.string.dialog_error_loading;
    public static final int DIALOG_GOOD_LOADING = R.string.dialog_good_loading;
    public static final int DIALOG_INFO_SPINNER = R.string.dialog_info_spinner;
    public static final int DIALOG_INFO_CONVERTER = R.string.dialog_info_converter;


    public static final int DIALOG_BUTTON_NEGATIVE = R.string.dialog_button_negative;


    public static final String TEXT_VIEW_TITLE = "Курс валют ЦБРФ"; //почемуто инт ссылка на стринговый ресурс отображает цифры, а не предложение
    public static final int TEXT_RIGHT_VALUTE = R.string.text_right_valute;


    public static final String KEY_IS_APP_TURN = "isTurn";
    public static final String KEY_IS_LOADER_STARTED = "isLoaderStarted";
    public static final String KEY_USER_INPUT_LEFT_VALUE = "userLeftValue";
    public static final String KEY_USER_CALCULATE_RIGHT_VALUE = "userRightValue";


    public static final String KEY_HTTP = "url";
    public static final String TARGET_VALUTE = "Японских иен";
    private NetworkReceiver receiver = new NetworkReceiver();
    private Fragment fragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
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
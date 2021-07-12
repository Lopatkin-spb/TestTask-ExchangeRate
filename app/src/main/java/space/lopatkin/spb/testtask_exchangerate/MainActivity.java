package space.lopatkin.spb.testtask_exchangerate;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import space.lopatkin.spb.testtask_exchangerate.model.Valute;
import space.lopatkin.spb.testtask_exchangerate.utils.AsyncLoader;
import space.lopatkin.spb.testtask_exchangerate.utils.NetworkReceiver;
import space.lopatkin.spb.testtask_exchangerate.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Valute>> {
    private static final String http = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String TAG = "myLogs";
    public static final int DIALOG_NO_INTERNET = R.string.dialog_no_internet;
    public static final int TOAST_ERROR_LOADING = R.string.toast_error_loading;
    public static final String TEXT_VIEW_TITLE = "Курс валюты ЦБРФ от ";

    public static final String KEY_IS_APP_TURN = "isTurn";
    public static final String KEY_IS_LOADER_STARTED = "isLoaderStarted";
    public static final String KEY_HTTP = "url";
    private static final String TARGET_VALUTE = "Японских иен";
    private NetworkReceiver receiver = new NetworkReceiver();
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private List<Valute> listValute = new ArrayList();
    private TextView viewTitle;
    private TextView viewLeftValute;
    private TextView viewRightValute;
    private TextView viewLeftValue;
    private TextView viewRightValue;
    private Button buttonRefresh;
    private ProgressBar progressBar;
    private Loader<List<Valute>> loader;
    private int idLoader = 1;
    private boolean isAppTurn = false;
    private boolean isLoaderStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferencesHelper = new SharedPreferencesHelper(this);

        viewTitle = findViewById(R.id.view_title);
        viewLeftValute = findViewById(R.id.view_left_valute);
        viewRightValute = findViewById(R.id.view_right_valute);

        buttonRefresh = findViewById(R.id.button_refresh);
        viewLeftValue = findViewById(R.id.view_left_value);
        viewRightValue = findViewById(R.id.view_right_value);
        progressBar = findViewById(R.id.progress_bar);
    }


    @Override
    protected void onResume() {
        super.onResume();
        listValute = mSharedPreferencesHelper.getSaveValutes();
        buttonRefresh.setOnClickListener(buttonOnClickListener);
        initLoader();
        if (isLoaderStarted) {
            loaderStartLoading();
        }
        if (listValute != null) {
            updateUI(listValute);
        }
    }


    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loaderStartLoading();
        }
    };

    private void initLoader() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_HTTP, http);
        if (loader == null) {
            loader = getSupportLoaderManager().initLoader(idLoader, bundle, this);
        } else {
            loader = getSupportLoaderManager().restartLoader(idLoader, bundle, this);
        }
    }

    @NonNull
    @Override
    public Loader<List<Valute>> onCreateLoader(int id, @Nullable Bundle args) {
        loader = new AsyncLoader(this, args);
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Valute>> loader, List<Valute> data) {
        if (data == null && !isAppTurn) {
            Toast.makeText(this, TOAST_ERROR_LOADING, Toast.LENGTH_LONG).show();

        } else if (data == null && isAppTurn) {
        } else {
            listValute = data;
            updateUI(listValute);
            mSharedPreferencesHelper.saveValutes(data);
        }
        isLoaderStarted = false;
        isAppTurn = true;
        buttonRefresh.setEnabled(true);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<Valute>> loader) {
        //для закрытия сложных устройств, типа курсора
    }


    private void updateUI(List<Valute> list) {
        int index = getTargetValute(list);
        viewTitle.setText(TEXT_VIEW_TITLE + "" + list.get(index).getDate());
        viewLeftValute.setText(
                list.get(index).getName() + " (" + list.get(index).getCharCode() + ")");
        viewRightValute.setText("Российский рубль (RUB)");
        viewLeftValue.setText(list.get(index).getNominal());
        viewRightValue.setText(list.get(index).getValue());
    }

    private int getTargetValute(List<Valute> list) {
        int out = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(TARGET_VALUTE)) {
                out = i;
            }
        }
        return out;
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


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isAppTurn = true;
        outState.putBoolean(KEY_IS_APP_TURN, isAppTurn);
        outState.putBoolean(KEY_IS_LOADER_STARTED, isLoaderStarted);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isAppTurn = savedInstanceState.getBoolean(KEY_IS_APP_TURN);
        isLoaderStarted = savedInstanceState.getBoolean(KEY_IS_LOADER_STARTED);
    }

    private void loaderStartLoading() {
        isAppTurn = false;
        isLoaderStarted = true;
        initLoader();
        buttonRefresh.setEnabled(false);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        loader.forceLoad();
    }


}
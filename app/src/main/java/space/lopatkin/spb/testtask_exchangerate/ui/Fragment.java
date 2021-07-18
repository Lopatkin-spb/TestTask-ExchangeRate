package space.lopatkin.spb.testtask_exchangerate.ui;

import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import space.lopatkin.spb.testtask_exchangerate.R;
import space.lopatkin.spb.testtask_exchangerate.model.Valute;
import space.lopatkin.spb.testtask_exchangerate.utils.AsyncLoader;
import space.lopatkin.spb.testtask_exchangerate.utils.MiniDialog;
import space.lopatkin.spb.testtask_exchangerate.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.*;

public class Fragment extends androidx.fragment.app.Fragment
        implements LoaderManager.LoaderCallbacks<List<Valute>> {

    private static final String TAG_MINI_DIALOG = "miniDialog";
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private List<Valute> listValutes = new ArrayList();
    private TextView viewTitle;
    private Spinner viewSpinnerLeftValute;
    private TextView viewRightValute;
    private TextView viewLeftValue;
    private TextView viewRightValue;
    private Button buttonRefresh;
    private ProgressBar progressBar;


    private Loader<List<Valute>> loader;
    private int idLoader = 1;
    private boolean isAppTurn = false;
    private boolean isLoaderStarted = false;

    public Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance() {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            isAppTurn = savedInstanceState.getBoolean(KEY_IS_APP_TURN);
            isLoaderStarted = savedInstanceState.getBoolean(KEY_IS_LOADER_STARTED);
        }
    }

    private void buildSpinner() {
        if (listValutes.size() > 10) {
            String[] list = getSpinnerList(listValutes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_selectable_list_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewSpinnerLeftValute.setAdapter(adapter);
            viewSpinnerLeftValute.setSelection(33); //0-33
            viewSpinnerLeftValute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    viewLeftValue.setText(listValutes.get(i).getNominal());
                    viewRightValue.setText(listValutes.get(i).getValue());

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else {
            showDialog(DIALOG_INFO_SPINNER);
        }
    }

    private String[] getSpinnerList(List<Valute> fullList) {
        String[] spinnerList = new String[fullList.size()];
        for (int line = 0; line < fullList.size(); line++) {
            spinnerList[line] = fullList.get(line).getName()
                    + " (" + fullList.get(line).getCharCode() + ")";
        }
        return spinnerList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        viewTitle = view.findViewById(R.id.view_title);
        viewSpinnerLeftValute = view.findViewById(R.id.view_left_spinner_valute);
        viewRightValute = view.findViewById(R.id.view_right_valute);
        buttonRefresh = view.findViewById(R.id.view_button_refresh);
        viewLeftValue = view.findViewById(R.id.view_left_value);
        viewRightValue = view.findViewById(R.id.view_right_value);
        progressBar = view.findViewById(R.id.progress_bar);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listValutes = mSharedPreferencesHelper.getSaveValutes();
        buttonRefresh.setOnClickListener(buttonOnClickListener);
        initLoader();
        if (isLoaderStarted) {
            loaderStartLoading();
        }
        if (listValutes != null) {
            updateUI(listValutes);
        }
        buildSpinner();
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
            loader = getActivity().getSupportLoaderManager().initLoader(idLoader, bundle, this);
        } else {
            loader = getActivity().getSupportLoaderManager().restartLoader(idLoader, bundle, this);
        }
    }


    @NonNull
    @Override
    public Loader<List<Valute>> onCreateLoader(int id, @Nullable Bundle args) {
        loader = new AsyncLoader(getActivity(), args);
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Valute>> loader, List<Valute> data) {
        if (!ifThereIsData(data) && !isAppTurn) {
            showDialog(DIALOG_ERROR_LOADING);
        } else if (ifThereIsData(data) && !isAppTurn) {
            listValutes = data;
            updateUI(listValutes);
            mSharedPreferencesHelper.saveValutes(data);
            showDialog(DIALOG_GOOD_LOADING);
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

    private void loaderStartLoading() {
        isAppTurn = false;
        isLoaderStarted = true;
        initLoader();
        buttonRefresh.setEnabled(false);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        loader.forceLoad();
    }

    private void updateUI(List<Valute> list) {
        int index = getTargetValute(list);
        viewTitle.setText(TEXT_VIEW_TITLE + " " + list.get(index).getDate());
        viewRightValute.setText(TEXT_RIGHT_VALUTE);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isAppTurn = true;
        outState.putBoolean(KEY_IS_APP_TURN, isAppTurn);
        outState.putBoolean(KEY_IS_LOADER_STARTED, isLoaderStarted);
    }

    private boolean ifThereIsData(List<Valute> list) {
        boolean out = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(TARGET_VALUTE)) {
                out = true;
            }
        }
        return out;
    }

    private void showDialog(int message) {
        MiniDialog dialog = MiniDialog.newInstance(message);
        dialog.show(getFragmentManager(), TAG_MINI_DIALOG);
    }

}
package space.lopatkin.spb.testtask_exchangerate.ui;

import android.os.Bundle;
import android.util.Log;
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
import space.lopatkin.spb.testtask_exchangerate.utils.*;

import java.util.ArrayList;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.*;

public class Fragment extends androidx.fragment.app.Fragment
        implements LoaderManager.LoaderCallbacks<List<Valute>> {

    private static final String TAG_MINI_DIALOG = "miniDialog";
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private Calculator calculator = new Calculator();
    private List<Valute> listValutes = new ArrayList();
    private TextView viewTitle;
    private Spinner viewLeftValuteSpinner;
    private TextView viewRightValute;
    private TextView viewLeftValue;
    private TextView viewRightValue;
    private EditText viewLeftConverter;
    private TextView viewRightConverter;
    private LinearLayout containerLeftValue;
    private LinearLayout containerRightValue;
    private LinearLayout containerLeftConverter;
    private LinearLayout containerRightConverter;
    private Button buttonRefresh;
    private ImageButton buttonRoundCalculate;
    private ProgressBar progressBar;

    private Loader<List<Valute>> loader;
    private int idLoader = 1;
    private boolean isAppTurn = false;
    private boolean isLoaderStarted = false;
    private int positionSpinnerValute = 33;
    private String userInputLeftValue = null;
    private String userRightValue = null;
    private int countSpinner = 0;

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
            userInputLeftValue = savedInstanceState.getString(KEY_USER_INPUT_LEFT_VALUE);
            userRightValue = savedInstanceState.getString(KEY_USER_CALCULATE_RIGHT_VALUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());
        containerLeftValue = view.findViewById(R.id.container_left_value);
        containerRightValue = view.findViewById(R.id.container_right_value);
        containerLeftConverter = view.findViewById(R.id.container_left_converter);
        containerRightConverter = view.findViewById(R.id.container_right_converter);
        progressBar = view.findViewById(R.id.progress_bar);

        viewTitle = view.findViewById(R.id.view_title);
        viewLeftValuteSpinner = view.findViewById(R.id.view_left_valute_spinner);
        viewRightValute = view.findViewById(R.id.view_right_valute);
        viewLeftValue = view.findViewById(R.id.text_left_value);
        viewRightValue = view.findViewById(R.id.text_right_value);
        viewLeftConverter = view.findViewById(R.id.text_left_converter);
        viewRightConverter = view.findViewById(R.id.text_right_converter);

        buttonRefresh = view.findViewById(R.id.view_button_refresh);
        buttonRoundCalculate = view.findViewById(R.id.view_button_arrow);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listValutes = mSharedPreferencesHelper.getValutes();
        buttonRefresh.setOnClickListener(refreshOnClickListener);
        buttonRoundCalculate.setOnClickListener(calculateOnClickListener);
        setUpViewElevation();
        initLoader();
        if (isLoaderStarted) {
            loaderStartLoading();
        }
        if (listValutes.size() != 0) {
            updateUI(listValutes);
        }
        setUpSpinner();
    }


    private void setUpSpinner() {
        if (listValutes.size() > 10) {
            String[] list = getSpinnerList(listValutes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_selectable_list_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewLeftValuteSpinner.setAdapter(adapter);
            viewLeftValuteSpinner.setSelection(positionSpinnerValute); //0-33
            viewLeftValuteSpinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        } else {
            showDialog(DIALOG_INFO_SPINNER);
        }
    }


    private View.OnClickListener refreshOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loaderStartLoading();
        }
    };

    private View.OnClickListener calculateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userInput = viewLeftConverter.getText().toString();
            if (!userInput.equals("")) {
                String newRightValue = calculator.calculate(listValutes, positionSpinnerValute, userInput);
                viewRightConverter.setText(newRightValue);
            } else {
                showDialog(DIALOG_INFO_CONVERTER);
            }
        }
    };

    private AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            countSpinner++;
            positionSpinnerValute = i;
            mSharedPreferencesHelper.savePositionValute(positionSpinnerValute);
            if (countSpinner == 2 && isAppTurn) {
                viewLeftConverter.setText(userInputLeftValue);
                viewRightConverter.setText(userRightValue);
                isAppTurn = false;
            } else {
                viewLeftValue.setText(listValutes.get(i).getNominal());
                viewRightValue.setText(listValutes.get(i).getValue().replace(",", "."));
                viewLeftConverter.setText("");
                viewLeftConverter.setHint("0");
                viewRightConverter.setText("0");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
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
        isAppTurn = false;
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
        positionSpinnerValute = mSharedPreferencesHelper.getPositionValute();
        viewTitle.setText(TEXT_VIEW_TITLE + " " + list.get(positionSpinnerValute).getDate());
        viewRightValute.setText(TEXT_RIGHT_VALUTE);
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

    private String[] getSpinnerList(List<Valute> fullList) {
        String[] spinnerList = new String[fullList.size()];
        for (int line = 0; line < fullList.size(); line++) {
            spinnerList[line] = "> " + fullList.get(line).getName()
                    + " (" + fullList.get(line).getCharCode() + ") <";
        }
        return spinnerList;
    }

    private void showDialog(int message) {
        MiniDialog dialog = MiniDialog.newInstance(message);
        dialog.show(getFragmentManager(), TAG_MINI_DIALOG);
    }


    private void setUpViewElevation() {
        viewTitle.setTranslationZ(2);
        viewTitle.setElevation(26);

        buttonRoundCalculate.setTranslationZ(10);
        buttonRoundCalculate.setElevation(20);
        buttonRoundCalculate.setStateListAnimator(null);

        viewLeftValuteSpinner.setTranslationZ(2);
        viewLeftValuteSpinner.setElevation(16);
        viewRightValute.setTranslationZ(2);
        viewRightValute.setElevation(16);

        containerLeftValue.setTranslationZ(2);
        containerLeftValue.setElevation(16);
        containerRightValue.setTranslationZ(2);
        containerRightValue.setElevation(16);

        containerLeftConverter.setTranslationZ(2);
        containerLeftConverter.setElevation(16);
        containerRightConverter.setTranslationZ(2);
        containerRightConverter.setElevation(16);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isAppTurn = true;
        outState.putBoolean(KEY_IS_APP_TURN, isAppTurn);
        outState.putBoolean(KEY_IS_LOADER_STARTED, isLoaderStarted);
        userInputLeftValue = viewLeftConverter.getText().toString();
        userRightValue = viewRightConverter.getText().toString();
        outState.putString(KEY_USER_INPUT_LEFT_VALUE, userInputLeftValue);
        outState.putString(KEY_USER_CALCULATE_RIGHT_VALUE, userRightValue);
    }


}
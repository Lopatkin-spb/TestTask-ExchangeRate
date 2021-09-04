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
import space.lopatkin.spb.testtask_exchangerate.db.AppDelegate;
import space.lopatkin.spb.testtask_exchangerate.db.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.db.ExchangeValutesDao;
import space.lopatkin.spb.testtask_exchangerate.db.Valute;
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
        initDB();
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
            updateDB(data);
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

    ExchangeValutesDao exchangeValutesDao;

    private void initDB() {
        exchangeValutesDao = ((AppDelegate) getActivity().getApplicationContext())
                .getExchangeValutesDatabase()
                .getExchangeValutesDao();
    }


    private void updateDB(List<Valute> data) {
        ExchangeValutes item = new ExchangeValutes();
        item.setDate(data.get(1).getDate());


        String s0 = data.get(0).getNominal() + "/" + data.get(0).getValue();
        item.setNumCode_036(s0);
        String s1 = data.get(1).getNominal() + "/" + data.get(1).getValue();
        item.setNumCode_944(s1);
        item.setNumCode_826(data.get(2).getNominal() + "/" + data.get(2).getValue());
        item.setNumCode_051(data.get(3).getNominal() + "/" + data.get(3).getValue());
        item.setNumCode_933(data.get(4).getNominal() + "/" + data.get(4).getValue());
        item.setNumCode_975(data.get(5).getNominal() + "/" + data.get(5).getValue());
        item.setNumCode_986(data.get(6).getNominal() + "/" + data.get(6).getValue());
        item.setNumCode_348(data.get(7).getNominal() + "/" + data.get(7).getValue());
        item.setNumCode_344(data.get(8).getNominal() + "/" + data.get(8).getValue());
        item.setNumCode_208(data.get(9).getNominal() + "/" + data.get(9).getValue());

        item.setNumCode_840(data.get(10).getNominal() + "/" + data.get(10).getValue());
        item.setNumCode_978(data.get(11).getNominal() + "/" + data.get(11).getValue());
        item.setNumCode_356(data.get(12).getNominal() + "/" + data.get(12).getValue());
        item.setNumCode_398(data.get(13).getNominal() + "/" + data.get(13).getValue());
        item.setNumCode_124(data.get(14).getNominal() + "/" + data.get(14).getValue());
        item.setNumCode_417(data.get(15).getNominal() + "/" + data.get(15).getValue());
        item.setNumCode_156(data.get(16).getNominal() + "/" + data.get(16).getValue());
        item.setNumCode_498(data.get(17).getNominal() + "/" + data.get(17).getValue());
        item.setNumCode_578(data.get(18).getNominal() + "/" + data.get(18).getValue());
        item.setNumCode_985(data.get(19).getNominal() + "/" + data.get(19).getValue());

        item.setNumCode_946(data.get(20).getNominal() + "/" + data.get(20).getValue());
        item.setNumCode_960(data.get(21).getNominal() + "/" + data.get(21).getValue());
        item.setNumCode_702(data.get(22).getNominal() + "/" + data.get(22).getValue());
        item.setNumCode_972(data.get(23).getNominal() + "/" + data.get(23).getValue());
        item.setNumCode_949(data.get(24).getNominal() + "/" + data.get(24).getValue());
        item.setNumCode_934(data.get(25).getNominal() + "/" + data.get(25).getValue());
        item.setNumCode_860(data.get(26).getNominal() + "/" + data.get(26).getValue());
        item.setNumCode_980(data.get(27).getNominal() + "/" + data.get(27).getValue());
        item.setNumCode_203(data.get(28).getNominal() + "/" + data.get(28).getValue());

        item.setNumCode_752(data.get(29).getNominal() + "/" + data.get(29).getValue());

        item.setNumCode_756(data.get(30).getNominal() + "/" + data.get(30).getValue());
        item.setNumCode_710(data.get(31).getNominal() + "/" + data.get(31).getValue());
        item.setNumCode_410(data.get(32).getNominal() + "/" + data.get(32).getValue());
        item.setNumCode_392(data.get(33).getNominal() + "/" + data.get(33).getValue());

        exchangeValutesDao.insertExchangeValutes(item);

    }


}
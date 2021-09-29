package space.lopatkin.spb.testtask_exchangerate.presentation.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONException;
import org.json.JSONObject;
import space.lopatkin.spb.testtask_exchangerate.Injection;
import space.lopatkin.spb.testtask_exchangerate.R;
import space.lopatkin.spb.testtask_exchangerate.data.db.Dates;
import space.lopatkin.spb.testtask_exchangerate.presentation.ViewModelFactory;
import space.lopatkin.spb.testtask_exchangerate.data.db.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.domain.models.Valute;
import space.lopatkin.spb.testtask_exchangerate.utils.*;
import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;

import java.util.ArrayList;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.*;

public class Fragment extends androidx.fragment.app.Fragment {

    //внедрить чистую архитектуру
    //внедрить МВП
    //рид ми добавить
    //собрать файл
    //выложить в инет прогу
    //разобраться да конца с поворотами - изучить стэйты (done)

    //разобраться в работе спинера - включается по два раза (навырост)
    //оптимизировать взаимодействие рхДжава с Апи и Роом (навырост)


    private static final String TAG_MINI_DIALOG = "miniDialog";
    private static final String TAG = Fragment.class.getSimpleName();

    private SharedPreferencesHelper mSharedPreferencesHelper;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ViewModelFactory mViewModelFactory;
    private ExchangeValutesViewModel mViewModel;
    private SavedStateViewModel mSavedStateViewModel;


    private Calculator calculator = new Calculator();
    private List<Valute> mListValutes = new ArrayList();
    private String[] mListValutesForSpinner = new String[34];

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

    private boolean isAppTurn = false;
    private boolean isLoaderStarted = false;
    private int positionSpinnerValute = 33;
    private String userInputLeftConverter = "0";
    private String userRightConverter = "0";
    private int countForSpinner = 0;

    public Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    //паттерн фабричный метод
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
//            isAppTurn = savedInstanceState.getBoolean(KEY_IS_APP_TURN);
            isLoaderStarted = savedInstanceState.getBoolean(KEY_IS_LOADER_STARTED);
            isAppTurn = true;
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

        setSavedStateData(savedInstanceState);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpViewElevation();

        buttonRefresh.setOnClickListener(refreshOnClickListener);
        buttonRoundCalculate.setOnClickListener(calculateOnClickListener);
        viewLeftValuteSpinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);

        setViewModels(); //android recomendation room+rxjava2

        try {
            setStateView();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setSavedStateData(Bundle savedInstanceState) {
        mSavedStateViewModel = new ViewModelProvider(requireActivity()).get(SavedStateViewModel.class);
        if (savedInstanceState != null) {
            mSavedStateViewModel.getSavedStateData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    viewLeftConverter.setText(strings.get(0));
                    viewRightConverter.setText(strings.get(1));
                }
            });
        }

    }

    private void setStateView() throws InterruptedException {

        mViewModel.getSavedStateData().observe(getViewLifecycleOwner(), new Observer<States>() {
            @Override
            public void onChanged(States states) {
                if (states == States.MESSAGE) {
                    int messageText = States.MESSAGE.getText();
                    textViewNoItems();
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    if (States.MESSAGE.isToast()) {
                        showToast(messageText);
                    } else {
                        showDialog(messageText);
                    }
                }
                if (states == States.DEFAULT_VIEW) {
                    textViewNoItems();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    buttonRefresh.setEnabled(true);
                }
                if (states == States.NORMAL_VIEW) {
                    ExchangeValutes ev = States.NORMAL_VIEW.getValutes();
                    try {
                        convertExValutesToList(ev);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateTextUI(ev);
                    setUpSpinner();
                    viewLeftValue.setText(mListValutes.get(positionSpinnerValute).getNominal());
                    viewRightValue.setText(mListValutes.get(positionSpinnerValute).getValue().replace(",", "."));
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    buttonRefresh.setEnabled(false);
                    buttonRoundCalculate.setEnabled(true);
                    viewLeftConverter.setFocusableInTouchMode(true);
                }

            }
        });


    }

    private void textViewNoItems() {
        mListValutesForSpinner = getSpinnerMockList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_selectable_list_item, mListValutesForSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewLeftValuteSpinner.setAdapter(adapter);
        viewLeftValuteSpinner.setSelection(positionSpinnerValute); //0-33
        viewLeftValuteSpinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        viewRightValute.setText("------------------------");
        viewLeftValue.setText("-------");
        viewRightValue.setText("-------");
        viewLeftConverter.setText("");
        viewLeftConverter.setHint("0");
        viewRightConverter.setText("0");
        buttonRefresh.setEnabled(false);
        buttonRoundCalculate.setEnabled(false);
        viewLeftConverter.setFocusable(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        userInputLeftConverter = viewLeftConverter.getText().toString();
        userRightConverter = viewRightConverter.getText().toString();

        mSavedStateViewModel.saveUserValue(userInputLeftConverter, userRightConverter);
    }

    private void setUpSpinner() {
        if (mListValutes != null && mListValutes.size() > 10) {
            mListValutesForSpinner = getSpinnerList(mListValutes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_selectable_list_item, mListValutesForSpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewLeftValuteSpinner.setAdapter(adapter);
            viewLeftValuteSpinner.setSelection(positionSpinnerValute); //0-33
//            viewLeftValuteSpinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        } else {
//            showDialog(DIALOG_INFO_SPINNER);
        }


    }


    private View.OnClickListener refreshOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mViewModel.update();
        }
    };

    private View.OnClickListener calculateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userInput = viewLeftConverter.getText().toString();
            if (!userInput.equals("")) {
                String newRightValue = calculator.calculate(mListValutes, positionSpinnerValute, userInput);
                viewRightConverter.setText(newRightValue);
            } else {
                showDialog(DIALOG_INFO_CONVERTER);
            }
        }
    };

    private AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //очень странная работа спинера. он включается по два раза после поворота.
            // потому добавлен счетчик и флаг исапптюрн (доработать в свободное время)
            countForSpinner++;
            positionSpinnerValute = i;
            mSharedPreferencesHelper.savePositionValute(positionSpinnerValute);
            if (countForSpinner == 1 && mListValutes.size() > 20) {
                if (isAppTurn) {
                    viewLeftValue.setText(mListValutes.get(i).getNominal());
                    viewRightValue.setText(mListValutes.get(i).getValue().replace(",", "."));
                } else {
                    viewLeftValue.setText(mListValutes.get(i).getNominal());
                    viewRightValue.setText(mListValutes.get(i).getValue().replace(",", "."));
                    viewRightConverter.setText("0");
                }

            }
            if (countForSpinner == 2) {
                countForSpinner = 0;
            }
            isAppTurn = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };


    private void updateTextUI(ExchangeValutes exchangeValutes) {
        String date = exchangeValutes.getDate();
        positionSpinnerValute = mSharedPreferencesHelper.getPositionValute();
        viewTitle.setText(TEXT_VIEW_TITLE + " " + date);
        viewRightValute.setText(TEXT_RIGHT_VALUTE);
    }


    private String[] getSpinnerList(List<Valute> fullList) {
        String[] spinnerList = new String[fullList.size()];
        for (int line = 0; line < fullList.size(); line++) {
            spinnerList[line] = "> " + fullList.get(line).getName()
                    + " (" + fullList.get(line).getCharCode() + ") <";
        }
        return spinnerList;
    }

    private String[] getSpinnerMockList() {
        String[] spinnerList = new String[34];
        for (int line = 0; line < 34; line++) {
            spinnerList[line] = "> -------------------------- (---) <";
        }
        return spinnerList;
    }


    private void showDialog(int message) {
        MiniDialog dialog = MiniDialog.newInstance(message);
        dialog.show(getFragmentManager(), TAG_MINI_DIALOG);
    }

    private void showToast(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
//        userInputLeftConverter = viewLeftConverter.getText().toString();
//        userRightConverter = viewRightConverter.getText().toString();
//        outState.putString(KEY_USER_INPUT_LEFT_VALUE, userInputLeftConverter);
//        outState.putString(KEY_USER_CALCULATE_RIGHT_VALUE, userRightConverter);
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void setViewModels() {
        mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(getActivity(), mViewModelFactory)
                .get(ExchangeValutesViewModel.class);
    }


    private void convertExValutesToList(ExchangeValutes exchangeValutes) throws JSONException {
        Gson mGson = new Gson();
        String jsonString = mGson.toJson(exchangeValutes);
        JSONObject factJSONObject = new JSONObject(jsonString);
        List<Valute> items = new ArrayList<>();

        for (int i = 0; i < 34; i++) {
            Valute item = new Valute();

            String columnName = "valute" + (i + 1);
            String columnValue = factJSONObject.getString(columnName);
            String[] detailsValute = columnValue.split("/");

            item.setNumCode(detailsValute[0]);
            item.setCharCode(detailsValute[1]);
            item.setNominal(detailsValute[2]);
            item.setName(detailsValute[3]);
            item.setValue(detailsValute[4]);
            item.setDate(factJSONObject.getString("date"));

            items.add(i, item);
        }
        mListValutes = items;

    }


    //----------------------------------------StateMachine--------------------------------------------

//    States
//
//    1.1)-update (обновление с сайта свежих данных) - 1 раз в день ,при первом запуске
//       1-поля пустые и отключены все кнопки
//       2-icon loading
//       3-обьявление: фон - обновление...
//    1.2)-good update and save db
//       1-поля пустые и отключены все кнопки
//       2-icon loading
//       3-обьявление: фон - обновление данных произошло успешно, бд обновлена.
//    3)-loading (загрузка данных из бд) - при каждом запуске
//       1-поля пустые и отключены все кнопки
//       2-icon loading
//       3-обьявление: фон - загрузка...
//    4)-error update (показ обьявления ошибки апдейта)
//       1-поля пустые и отключены все кнопки
//       2-icon loading
//       3-обьявление: фон - произошла ошибка загрузки с сайта, производится загрузка из бд
//    5)-error loading default state(показ обьявления загрузки из бд)
//       1-поля пустые и отключены все кнопки
//       2-обьявление: большое - данные в бд не найдены, сделайте принудительную загрузку после исправления неисправности
//    6)-normal state (и после поворотов)
//       1-нужные поля заполнены и кнопки включены
//       2-обьявление: большое - приложение готово к работе. выберите слева нужную валюту. инфо кнопок
//
//


}
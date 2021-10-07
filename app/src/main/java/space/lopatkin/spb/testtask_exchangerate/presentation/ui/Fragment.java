package space.lopatkin.spb.testtask_exchangerate.presentation.ui;

import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import io.reactivex.disposables.CompositeDisposable;
import org.json.JSONException;
import org.json.JSONObject;
import space.lopatkin.spb.testtask_exchangerate.presentation.Injection;
import space.lopatkin.spb.testtask_exchangerate.R;
import space.lopatkin.spb.testtask_exchangerate.presentation.ViewModelFactory;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.utils.xvlConverter.Valute;
import space.lopatkin.spb.testtask_exchangerate.utils.*;
import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;

import java.util.ArrayList;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.*;

public class Fragment extends androidx.fragment.app.Fragment {

    //внедрить чистую архитектуру
    //внедрить МВП
    //рид ми добавить +
    //собрать файл
    //выложить в инет прогу
    //разобраться да конца с поворотами - изучить стэйты (done)
//добавить модульность

    //разобраться в работе спинера - включается по два раза (future)
    //оптимизировать взаимодействие рхДжава с Апи и Роом (future)

    //начать клин архитекчур

    private static final String TAG_MINI_DIALOG = "miniDialog";
    private static final String TAG = Fragment.class.getSimpleName();

    private SharedPreferencesHelper mSharedPreferencesHelper;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ViewModelFactory mViewModelFactory;
    private ExchangeValutesViewModel mViewModel;
    private SavedStateViewModel mSavedStateViewModel;


    private Calculator mCalculator = new Calculator();
    private List<Valute> mListValutes = new ArrayList();
    private String[] mListValutesForSpinner = new String[34];

    private TextView mViewTitle;
    private Spinner mViewLeftValuteSpinner;
    private TextView mViewRightValute;
    private TextView mViewLeftValue;
    private TextView mViewRightValue;
    private EditText mViewLeftConverter;
    private TextView mViewRightConverter;
    private LinearLayout mContainerLeftValue;
    private LinearLayout mContainerRightValue;
    private LinearLayout mContainerLeftConverter;
    private LinearLayout mContainerRightConverter;
    private Button mButtonRefresh;
    private ImageButton mButtonRoundCalculate;
    private ProgressBar mProgressBar;

    private boolean mIsAppTurn = false;
    private boolean mIsLoaderStarted = false;
    private int mPositionSpinnerValute = 33;
    private String mUserInputLeftConverter = "0";
    private String mUserRightConverter = "0";
    private int mCountForSpinner = 0;

    private int mSizeViewElevation = 16;
    private int mSizeViewElevationTitle = 26;
    private int mSizeViewElevationButtonCalculate = 20;
    private int mSizeViewZ = 2;
    private int mSizeViewZButtonCalculate = 10;

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
            mIsLoaderStarted = savedInstanceState.getBoolean(KEY_IS_LOADER_STARTED);
            mIsAppTurn = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mContainerLeftValue = view.findViewById(R.id.container_left_value);
        mContainerRightValue = view.findViewById(R.id.container_right_value);
        mContainerLeftConverter = view.findViewById(R.id.container_left_converter);
        mContainerRightConverter = view.findViewById(R.id.container_right_converter);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mViewTitle = view.findViewById(R.id.view_title);
        mViewLeftValuteSpinner = view.findViewById(R.id.view_left_valute_spinner);
        mViewRightValute = view.findViewById(R.id.view_right_valute);
        mViewLeftValue = view.findViewById(R.id.text_left_value);
        mViewRightValue = view.findViewById(R.id.text_right_value);
        mViewLeftConverter = view.findViewById(R.id.text_left_converter);
        mViewRightConverter = view.findViewById(R.id.text_right_converter);

        mButtonRefresh = view.findViewById(R.id.view_button_refresh);
        mButtonRoundCalculate = view.findViewById(R.id.view_button_arrow);

        setSavedStateData(savedInstanceState);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpViewElevation();

        mButtonRefresh.setOnClickListener(refreshOnClickListener);
        mButtonRoundCalculate.setOnClickListener(calculateOnClickListener);
        mViewLeftValuteSpinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);

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
                public void onChanged(List<String> list) {
                    mViewLeftConverter.setText(list.get(0));
                    mViewRightConverter.setText(list.get(1));
                }
            });
        }

    }

    private void setStateView() throws InterruptedException {

        mViewModel.getState().observe(getViewLifecycleOwner(), new Observer<States>() {
            @Override
            public void onChanged(States states) {
                if (states == States.MESSAGE) {
//                    int messageText = States.MESSAGE.getText();
                    setTextViewNoItems();
                    mProgressBar.setVisibility(ProgressBar.VISIBLE);
                    if (States.MESSAGE.isToast()) {
                        showToast(States.MESSAGE.getText());
                    } else {
                        showDialog(States.MESSAGE.getText());
                    }
                }
                if (states == States.DEFAULT_VIEW) {
                    setTextViewNoItems();
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    mButtonRefresh.setEnabled(true);
                }
                if (states == States.NORMAL_VIEW) {
//                    ExchangeValutes ev = States.NORMAL_VIEW.getValutes();
                    try {
                        convertExValutesToList(States.NORMAL_VIEW.getValutes());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateTextUI(States.NORMAL_VIEW.getValutes());
                    setUpSpinner();
                    mViewLeftValue.setText(mListValutes.get(mPositionSpinnerValute).getNominal());
                    mViewRightValue.setText(mListValutes.get(mPositionSpinnerValute).getValue().replace(",", "."));
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    mButtonRefresh.setEnabled(false);
                    mButtonRoundCalculate.setEnabled(true);
                    mViewLeftConverter.setFocusableInTouchMode(true);
                }

            }
        });


    }

    private void setTextViewNoItems() {
        mListValutesForSpinner = getSpinnerMockList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_selectable_list_item, mListValutesForSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mViewLeftValuteSpinner.setAdapter(adapter);
        mViewLeftValuteSpinner.setSelection(mPositionSpinnerValute); //0-33
        mViewLeftValuteSpinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        mViewRightValute.setText("------------------------");
        mViewLeftValue.setText("-------");
        mViewRightValue.setText("-------");
        mViewLeftConverter.setText("");
        mViewLeftConverter.setHint("0");
        mViewRightConverter.setText("0");
        mButtonRefresh.setEnabled(false);
        mButtonRoundCalculate.setEnabled(false);
        mViewLeftConverter.setFocusable(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mUserInputLeftConverter = mViewLeftConverter.getText().toString();
        mUserRightConverter = mViewRightConverter.getText().toString();

        mSavedStateViewModel.saveUserValue(mUserInputLeftConverter, mUserRightConverter);
    }

    private void setUpSpinner() {
        if (mListValutes != null && mListValutes.size() > 10) {
            mListValutesForSpinner = getSpinnerList(mListValutes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_selectable_list_item, mListValutesForSpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mViewLeftValuteSpinner.setAdapter(adapter);
            mViewLeftValuteSpinner.setSelection(mPositionSpinnerValute); //0-33
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
            String userInput = mViewLeftConverter.getText().toString();
            if (!userInput.equals("")) {
                String newRightValue = mCalculator.calculate(mListValutes, mPositionSpinnerValute, userInput);
                mViewRightConverter.setText(newRightValue);
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
            mCountForSpinner++;
            mPositionSpinnerValute = i;
            mSharedPreferencesHelper.savePositionValute(mPositionSpinnerValute);
            if (mCountForSpinner == 1 && mListValutes.size() > 20) {
                if (mIsAppTurn) {
                    mViewLeftValue.setText(mListValutes.get(i).getNominal());
                    mViewRightValue.setText(mListValutes.get(i).getValue().replace(",", "."));
                } else {
                    mViewLeftValue.setText(mListValutes.get(i).getNominal());
                    mViewRightValue.setText(mListValutes.get(i).getValue().replace(",", "."));
                    mViewRightConverter.setText("0");
                }

            }
            if (mCountForSpinner == 2) {
                mCountForSpinner = 0;
            }
            mIsAppTurn = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };


    private void updateTextUI(ExchangeValutes exchangeValutes) {
        String date = exchangeValutes.getDate();
        mPositionSpinnerValute = mSharedPreferencesHelper.getPositionValute();
        mViewTitle.setText(TEXT_VIEW_TITLE + " " + date);
        mViewRightValute.setText(TEXT_RIGHT_VALUTE);
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

//    private int mSizeViewElevation = 16;
//    private int mSizeViewElevationTitle = 26;
//    private int mSizeViewElevationButtonCalculate = 20;
//
//    private int mSizeViewZ = 2;
//    private int mSizeViewZButtonCalculate = 10;


    private void setUpViewElevation() {
        mViewTitle.setTranslationZ(mSizeViewZ);
        mViewTitle.setElevation(mSizeViewElevationTitle);

        mButtonRoundCalculate.setTranslationZ(mSizeViewZButtonCalculate);
        mButtonRoundCalculate.setElevation(mSizeViewElevationButtonCalculate);
        mButtonRoundCalculate.setStateListAnimator(null);

        mViewLeftValuteSpinner.setTranslationZ(mSizeViewZ);
        mViewLeftValuteSpinner.setElevation(mSizeViewElevation);
        mViewRightValute.setTranslationZ(mSizeViewZ);
        mViewRightValute.setElevation(mSizeViewElevation);

        mContainerLeftValue.setTranslationZ(mSizeViewZ);
        mContainerLeftValue.setElevation(mSizeViewElevation);
        mContainerRightValue.setTranslationZ(mSizeViewZ);
        mContainerRightValue.setElevation(mSizeViewElevation);

        mContainerLeftConverter.setTranslationZ(mSizeViewZ);
        mContainerLeftConverter.setElevation(mSizeViewElevation);
        mContainerRightConverter.setTranslationZ(mSizeViewZ);
        mContainerRightConverter.setElevation(mSizeViewElevation);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mIsAppTurn = true;
        outState.putBoolean(KEY_IS_APP_TURN, mIsAppTurn);
        outState.putBoolean(KEY_IS_LOADER_STARTED, mIsLoaderStarted);
//        userInputLeftConverter = viewLeftConverter.getText().toString();
//        userRightConverter = viewRightConverter.getText().toString();
//        outState.putString(KEY_USER_INPUT_LEFT_VALUE, userInputLeftConverter);
//        outState.putString(KEY_USER_CALCULATE_RIGHT_VALUE, userRightConverter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
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
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
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import space.lopatkin.spb.testtask_exchangerate.R;
import space.lopatkin.spb.testtask_exchangerate.db.AppDelegate;
import space.lopatkin.spb.testtask_exchangerate.db.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.db.ExchangeValutesDao;
import space.lopatkin.spb.testtask_exchangerate.db.Valute;
import space.lopatkin.spb.testtask_exchangerate.utils.*;
import space.lopatkin.spb.testtask_exchangerate.utils.xmlConverter.ValCurs;

import java.util.ArrayList;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.*;

public class Fragment extends androidx.fragment.app.Fragment
        implements LoaderManager.LoaderCallbacks<List<Valute>> {

    private static final String TAG_MINI_DIALOG = "miniDialog";
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private Calculator calculator = new Calculator();
    private List<Valute> mListValutes = new ArrayList();
    private String[] mListValutesForSpinner = new String[mListValutes.size()];

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
//        mListValutes = mSharedPreferencesHelper.getValutes();
        buttonRefresh.setOnClickListener(refreshOnClickListener);
        buttonRoundCalculate.setOnClickListener(calculateOnClickListener);
        setUpViewElevation();
//        initLoader();
//        if (isLoaderStarted) {
//            loaderStartLoading();
//        }
//        if (listValutes.size() != 0) {
//            updateUI(listValutes);
//        }
        showDataFromRoomRecomendation();
        getRXJAVA2ListValutes();
//        setUpSpinner();

    }


    private void setUpSpinner() {
        if (mListValutes.size() > 10) {
            mListValutesForSpinner = getSpinnerList(mListValutes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_selectable_list_item, mListValutesForSpinner);
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
            countSpinner++;
            positionSpinnerValute = i;
            mSharedPreferencesHelper.savePositionValute(positionSpinnerValute);
            if (countSpinner == 2 && isAppTurn) {
                viewLeftConverter.setText(userInputLeftValue);
                viewRightConverter.setText(userRightValue);
                isAppTurn = false;
            } else {
                viewLeftValue.setText(mListValutes.get(i).getNominal());
                viewRightValue.setText(mListValutes.get(i).getValue().replace(",", "."));
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
            mListValutes = data;
//            updateUI(mListValutes);
            mSharedPreferencesHelper.saveValutes(data);
            showDialog(DIALOG_GOOD_LOADING);
//            updateDB(data);
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

    private void updateUI(ExchangeValutes exchangeValutes) {
        String date = exchangeValutes.getDate();
        positionSpinnerValute = mSharedPreferencesHelper.getPositionValute();
//        viewTitle.setText(TEXT_VIEW_TITLE + " " + list.get(positionSpinnerValute).getDate());
        viewTitle.setText(TEXT_VIEW_TITLE + " " + date);
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


    //------------------------------------------------------------------------------------

    ExchangeValutesDao exchangeValutesDao;

    private void initDB() {
        exchangeValutesDao = ((AppDelegate) getActivity().getApplicationContext())
                .getExchangeValutesDatabase()
                .getExchangeValutesDao();
    }


    private void updateDB(ValCurs valCurs) {
        mListValutes = valCurs.getListValutes();
        ExchangeValutes item = new ExchangeValutes();
        item.setDate(valCurs.getDate());

        String s0 = mListValutes.get(0).getNominal() + "/" + mListValutes.get(0).getValue();
        item.setNumCode_036(s0);
        String s1 = mListValutes.get(1).getNominal() + "/" + mListValutes.get(1).getValue();
        item.setNumCode_944(s1);
        item.setNumCode_826(mListValutes.get(2).getNominal() + "/" + mListValutes.get(2).getValue());
        item.setNumCode_051(mListValutes.get(3).getNominal() + "/" + mListValutes.get(3).getValue());
        item.setNumCode_933(mListValutes.get(4).getNominal() + "/" + mListValutes.get(4).getValue());
        item.setNumCode_975(mListValutes.get(5).getNominal() + "/" + mListValutes.get(5).getValue());
        item.setNumCode_986(mListValutes.get(6).getNominal() + "/" + mListValutes.get(6).getValue());
        item.setNumCode_348(mListValutes.get(7).getNominal() + "/" + mListValutes.get(7).getValue());
        item.setNumCode_344(mListValutes.get(8).getNominal() + "/" + mListValutes.get(8).getValue());
        item.setNumCode_208(mListValutes.get(9).getNominal() + "/" + mListValutes.get(9).getValue());

        item.setNumCode_840(mListValutes.get(10).getNominal() + "/" + mListValutes.get(10).getValue());
        item.setNumCode_978(mListValutes.get(11).getNominal() + "/" + mListValutes.get(11).getValue());
        item.setNumCode_356(mListValutes.get(12).getNominal() + "/" + mListValutes.get(12).getValue());
        item.setNumCode_398(mListValutes.get(13).getNominal() + "/" + mListValutes.get(13).getValue());
        item.setNumCode_124(mListValutes.get(14).getNominal() + "/" + mListValutes.get(14).getValue());
        item.setNumCode_417(mListValutes.get(15).getNominal() + "/" + mListValutes.get(15).getValue());
        item.setNumCode_156(mListValutes.get(16).getNominal() + "/" + mListValutes.get(16).getValue());
        item.setNumCode_498(mListValutes.get(17).getNominal() + "/" + mListValutes.get(17).getValue());
        item.setNumCode_578(mListValutes.get(18).getNominal() + "/" + mListValutes.get(18).getValue());
        item.setNumCode_985(mListValutes.get(19).getNominal() + "/" + mListValutes.get(19).getValue());

        item.setNumCode_946(mListValutes.get(20).getNominal() + "/" + mListValutes.get(20).getValue());
        item.setNumCode_960(mListValutes.get(21).getNominal() + "/" + mListValutes.get(21).getValue());
        item.setNumCode_702(mListValutes.get(22).getNominal() + "/" + mListValutes.get(22).getValue());
        item.setNumCode_972(mListValutes.get(23).getNominal() + "/" + mListValutes.get(23).getValue());
        item.setNumCode_949(mListValutes.get(24).getNominal() + "/" + mListValutes.get(24).getValue());
        item.setNumCode_934(mListValutes.get(25).getNominal() + "/" + mListValutes.get(25).getValue());
        item.setNumCode_860(mListValutes.get(26).getNominal() + "/" + mListValutes.get(26).getValue());
        item.setNumCode_980(mListValutes.get(27).getNominal() + "/" + mListValutes.get(27).getValue());
        item.setNumCode_203(mListValutes.get(28).getNominal() + "/" + mListValutes.get(28).getValue());

        item.setNumCode_752(mListValutes.get(29).getNominal() + "/" + mListValutes.get(29).getValue());

        item.setNumCode_756(mListValutes.get(30).getNominal() + "/" + mListValutes.get(30).getValue());
        item.setNumCode_710(mListValutes.get(31).getNominal() + "/" + mListValutes.get(31).getValue());
        item.setNumCode_410(mListValutes.get(32).getNominal() + "/" + mListValutes.get(32).getValue());
        item.setNumCode_392(mListValutes.get(33).getNominal() + "/" + mListValutes.get(33).getValue());

        insertDataToDb(item);
    }

    //------------------------------------------------------------------------------------
    //--------------------------------------Rxjava2----------------------------------------------


    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void getRXJAVA2ListValutes() {
        compositeDisposable.add(Client.getApiService()
                .getAllValutes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ValCurs>() {
                               @Override
                               public void accept(ValCurs valCurs) throws Exception {
                                   updateDB(valCurs);
                                   Log.d(TAG, "get data from API successe");
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d(TAG, "--> FRAGMENT getRXJAVA2ListValutes: throwable=" + throwable);
                                   showDialog(DIALOG_ERROR_LOADING);
                               }
                           }
                )
        );
    }

    private void insertDataToDb(ExchangeValutes list) {
        compositeDisposable.add(exchangeValutesDao
                .insertExchangeValutes(list)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "insert to db successe");
                    }
                }));
    }


    private void showDataFromRoomRecomendation() {

        compositeDisposable.add(exchangeValutesDao
                .getLastExchangeValutes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ExchangeValutes>() {
                    @Override
                    public void accept(ExchangeValutes valutes) throws Exception {
                        convertExValutesToList(valutes);
                        updateUI(valutes);
                        setUpSpinner();
                        Log.d(TAG, "load from db successe");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
        );

    }

    private void convertExValutesToList(ExchangeValutes exchangeValutes) throws JSONException {
        Gson mGson = new Gson();
        String jsonString = mGson.toJson(exchangeValutes);
        JSONObject factJSONObject = new JSONObject(jsonString);
        List<Valute> items = new ArrayList<>();
        for (int i = 0; i < mListValutes.size(); i++) {
            Valute item = new Valute();
            item.setNumCode(mListValutes.get(i).getNumCode());
            item.setCharCode(mListValutes.get(i).getCharCode());
            item.setDate(factJSONObject.getString("date"));
            item.setName(mListValutes.get(i).getName());
            String fieldName = "numCode_" + mListValutes.get(i).getNumCode();
            String fieldValue = factJSONObject.getString(fieldName);
            String[] output = fieldValue.split("/");
            item.setNominal(output[0]);
            item.setValue(output[1]);
            items.add(item);
        }
        mListValutes = items;
    }


}
package space.lopatkin.spb.testtask_exchangerate.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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
import space.lopatkin.spb.testtask_exchangerate.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.*;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment extends androidx.fragment.app.Fragment
        implements LoaderManager.LoaderCallbacks<List<Valute>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferencesHelper mSharedPreferencesHelper;
    private List<Valute> listValutes = new ArrayList();
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


    public Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String param1, String param2) {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (savedInstanceState != null) {
            isAppTurn = savedInstanceState.getBoolean(KEY_IS_APP_TURN);
            isLoaderStarted = savedInstanceState.getBoolean(KEY_IS_LOADER_STARTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        viewTitle = view.findViewById(R.id.view_title);
        viewLeftValute = view.findViewById(R.id.view_left_valute);
        viewRightValute = view.findViewById(R.id.view_right_valute);

        buttonRefresh = view.findViewById(R.id.button_refresh);
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
        if (data == null && !isAppTurn) {
            Toast.makeText(getActivity(), TOAST_ERROR_LOADING, Toast.LENGTH_LONG).show();

        } else if (data == null && isAppTurn) {
        } else {
            listValutes = data;
            updateUI(listValutes);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isAppTurn = true;
        outState.putBoolean(KEY_IS_APP_TURN, isAppTurn);
        outState.putBoolean(KEY_IS_LOADER_STARTED, isLoaderStarted);
    }

}
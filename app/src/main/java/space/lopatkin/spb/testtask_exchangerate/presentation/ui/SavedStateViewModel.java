package space.lopatkin.spb.testtask_exchangerate.presentation.ui;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.TAG_MY_LOGS;

public class SavedStateViewModel extends ViewModel {

    public static final String KEY_LEFT_CONVERTER = "ExchangeValutesViewModel.KEY_LEFT_CONVERTER";
    public static final String KEY_RIGHT_CONVERTER = "ExchangeValutesViewModel.KEY_RIGHT_CONVERTER";

    private final SavedStateHandle mSavedStateHandle;

    public SavedStateViewModel(SavedStateHandle savedStateHandle) {
        mSavedStateHandle = savedStateHandle;
    }

    public void saveUserValue(String userInputLeftConverter, String userRightConverter) {
        mSavedStateHandle.set(KEY_LEFT_CONVERTER, userInputLeftConverter);
        mSavedStateHandle.set(KEY_RIGHT_CONVERTER, userRightConverter);
    }

    private MutableLiveData<List<String>> mUserData;

    public MutableLiveData<List<String>> getSavedStateData() {

        if (mUserData == null) {
            mUserData = new MutableLiveData<>();
        }
        List<String> data = new ArrayList<>();
        data.add(0, mSavedStateHandle.get(KEY_LEFT_CONVERTER));
        data.add(1, mSavedStateHandle.get(KEY_RIGHT_CONVERTER));

        mUserData.setValue(data);
        return mUserData;
    }


}

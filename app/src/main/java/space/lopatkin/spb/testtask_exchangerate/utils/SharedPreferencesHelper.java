package space.lopatkin.spb.testtask_exchangerate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import space.lopatkin.spb.testtask_exchangerate.utils.xvlConverter.Valute;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SharedPreferencesHelper {

    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String VALUTES_KEY = "VALUTES_KEY";
    public static final String TARGET_VALUTE_KEY = "TARGET_VALUTE_KEY";

    public static final Type VALUTES_TYPE = new TypeToken<List<Valute>>() {
    }.getType();

    private SharedPreferences mSharedPreferences;
    private Gson mGson = new Gson();

    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.
                getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveValutes(List list) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String json = mGson.toJson(list);
        editor.putString(VALUTES_KEY, json);
        editor.apply();
    }

    public ArrayList getValutes() {
        String json = mSharedPreferences.getString(VALUTES_KEY, null);
        ArrayList<Valute> arrayList = mGson.fromJson(json, VALUTES_TYPE);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    public void savePositionValute(int positionValute) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(TARGET_VALUTE_KEY, positionValute);
        editor.apply();
    }

    public int getPositionValute() {
        int positionValute = mSharedPreferences.getInt(TARGET_VALUTE_KEY, 0);
        return positionValute;
    }


}

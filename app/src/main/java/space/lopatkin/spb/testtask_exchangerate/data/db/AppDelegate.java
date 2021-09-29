package space.lopatkin.spb.testtask_exchangerate.data.db;

import android.app.Application;
import androidx.room.Room;

public class AppDelegate extends Application {

    private static String DATABASE_NAME = "database_app_exchange_rates";

    //гугл рекомендует инициализировать рум через апликэйшин
    //поскольку рум тяжелая. позаботимся об только одном екземпляре ее
    // и сделаем в апликейшин
    // -> манифест

    private ExchangeValutesDatabase exchangeValutesDatabase;

    public ExchangeValutesDatabase getExchangeValutesDatabase() {
        return exchangeValutesDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (exchangeValutesDatabase == null) {
            exchangeValutesDatabase = Room.databaseBuilder(getApplicationContext()
                    , ExchangeValutesDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

    }
}


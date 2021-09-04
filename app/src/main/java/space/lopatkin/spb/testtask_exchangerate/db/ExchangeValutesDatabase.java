package space.lopatkin.spb.testtask_exchangerate.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ExchangeValutes.class}, version = 1)
public abstract class ExchangeValutesDatabase extends RoomDatabase {

    public abstract ExchangeValutesDao getExchangeValutesDao();
}

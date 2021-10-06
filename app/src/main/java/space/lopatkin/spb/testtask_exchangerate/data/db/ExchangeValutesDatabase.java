package space.lopatkin.spb.testtask_exchangerate.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ExchangeValutes.class}, version = 18)
public abstract class ExchangeValutesDatabase extends RoomDatabase {

    public abstract ExchangeValutesDao getExchangeValutesDao();

}

package space.lopatkin.spb.testtask_exchangerate.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ExchangeValutes;

@Database(entities = {ExchangeValutes.class}, version = 18)
public abstract class ExchangeValutesDatabase extends RoomDatabase {

    public abstract ExchangeValutesDao getExchangeValutesDao();

}

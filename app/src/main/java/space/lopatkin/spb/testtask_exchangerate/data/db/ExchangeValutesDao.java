package space.lopatkin.spb.testtask_exchangerate.data.db;

import androidx.room.*;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface ExchangeValutesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertExchangeValutes(ExchangeValutes valutes);

    @Query("SELECT * FROM table_exchange_valutes ORDER BY date DESC LIMIT 1")
    Single<ExchangeValutes> getLastExchangeValutes();

    @Query("DELETE FROM table_exchange_valutes")
    Completable deleteAllExchangeValutes();

    @Query("SELECT id, date FROM table_exchange_valutes ORDER BY date DESC")
    Flowable<List<Dates>> getAllDates();






}

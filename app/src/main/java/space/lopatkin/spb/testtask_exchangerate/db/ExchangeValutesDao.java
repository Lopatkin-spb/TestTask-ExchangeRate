package space.lopatkin.spb.testtask_exchangerate.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface ExchangeValutesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertExchangeValutes(ExchangeValutes valutes);

    @Query("select * from table_exchange_valutes ORDER BY date DESC LIMIT 1")
//    @Query("select * from table_exchange_valutes ORDER BY date ASC LIMIT 1")
    Flowable<ExchangeValutes> getLastExchangeValutes();

    @Query("delete from table_exchange_valutes")
    void deleteAllExchangeValutes();


}

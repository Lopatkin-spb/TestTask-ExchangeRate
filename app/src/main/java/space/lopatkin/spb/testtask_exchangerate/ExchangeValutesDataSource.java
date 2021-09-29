package space.lopatkin.spb.testtask_exchangerate;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import space.lopatkin.spb.testtask_exchangerate.data.db.Dates;
import space.lopatkin.spb.testtask_exchangerate.data.db.ExchangeValutes;

import java.util.List;

public interface ExchangeValutesDataSource {

    //1) копируем все запросы из FactDao ()

    Completable insertExchangeValutes(ExchangeValutes valutes);

    Flowable<ExchangeValutes> getLastExchangeValutes();

    Completable deleteAllExchangeValutes();


    //-----------------------------------------------------------------------

    Flowable<List<Dates>> getAllDates();




}

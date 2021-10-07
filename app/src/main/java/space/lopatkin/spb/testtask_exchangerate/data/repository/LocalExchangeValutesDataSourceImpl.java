package space.lopatkin.spb.testtask_exchangerate.data.repository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import space.lopatkin.spb.testtask_exchangerate.data.local.Dates;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.data.local.ExchangeValutesDao;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ExchangeValutesDataSource;

import java.util.List;

public class LocalExchangeValutesDataSourceImpl implements ExchangeValutesDataSource {

    //2) имплементим FactDataSource и остальное по мелочи ()

    private final ExchangeValutesDao mExchangeValutesDao;


    public LocalExchangeValutesDataSourceImpl(ExchangeValutesDao exchangeValutesDao) {
        mExchangeValutesDao = exchangeValutesDao;
    }
    @Override
    public Completable insertExchangeValutes(ExchangeValutes valutes) {
        return mExchangeValutesDao.insertExchangeValutes(valutes);
    }
    @Override
    public Single<ExchangeValutes> getLastExchangeValutes() {
        return mExchangeValutesDao.getLastExchangeValutes();
    }
    @Override
    public Completable deleteAllExchangeValutes() {
        return mExchangeValutesDao.deleteAllExchangeValutes();
    }

    @Override
    public Flowable<List<Dates>> getAllDates() {
        return mExchangeValutesDao.getAllDates();
    }


}

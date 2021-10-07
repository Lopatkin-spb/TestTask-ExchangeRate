package space.lopatkin.spb.testtask_exchangerate.presentation;

import android.content.Context;
import space.lopatkin.spb.testtask_exchangerate.data.local.AppDelegate;
import space.lopatkin.spb.testtask_exchangerate.data.local.ExchangeValutesDao;
import space.lopatkin.spb.testtask_exchangerate.data.network.CbrApi;
import space.lopatkin.spb.testtask_exchangerate.data.network.Client;
import space.lopatkin.spb.testtask_exchangerate.data.repository.LocalExchangeValutesDataSourceImpl;
import space.lopatkin.spb.testtask_exchangerate.data.repository.ValCursDataSourceImpl;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ExchangeValutesDataSource;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ValCursDataSource;

public class Injection {

//5) ? (готово) возможно бд заменить на дао или наоборот
    //класс нужен для Фрагмента и вьюМодел


    public static ExchangeValutesDataSource provideLocalDataSource(Context context) {
        ExchangeValutesDao database = ((AppDelegate) context.getApplicationContext())
                .getExchangeValutesDatabase()
                .getExchangeValutesDao();
        return new LocalExchangeValutesDataSourceImpl(database);
    }


    public static ValCursDataSource provideNetworkDataSource() {
        CbrApi api = Client.getApiService();
        return new ValCursDataSourceImpl(api);
    }


    public static ViewModelFactory provideViewModelFactory(Context context) {
        ExchangeValutesDataSource localDataSource = provideLocalDataSource(context);
        ValCursDataSource networkDataSource = provideNetworkDataSource();
        return new ViewModelFactory(localDataSource, networkDataSource);
    }
}
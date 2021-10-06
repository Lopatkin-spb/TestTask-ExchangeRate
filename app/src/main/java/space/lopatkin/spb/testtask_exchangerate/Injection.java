package space.lopatkin.spb.testtask_exchangerate;

import android.content.Context;
import space.lopatkin.spb.testtask_exchangerate.data.db.AppDelegate;
import space.lopatkin.spb.testtask_exchangerate.data.db.ExchangeValutesDao;
import space.lopatkin.spb.testtask_exchangerate.data.repository.LocalExchangeValutesDataSource;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ExchangeValutesDataSource;
import space.lopatkin.spb.testtask_exchangerate.presentation.ViewModelFactory;

public class Injection {

//5) ? (готово) возможно бд заменить на дао


    public static ExchangeValutesDataSource provideUserDataSource(Context context) {
        ExchangeValutesDao database = ((AppDelegate) context.getApplicationContext())
                .getExchangeValutesDatabase()
                .getExchangeValutesDao();
        return new LocalExchangeValutesDataSource(database);
    }


    public static ViewModelFactory provideViewModelFactory(Context context) {

        ExchangeValutesDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
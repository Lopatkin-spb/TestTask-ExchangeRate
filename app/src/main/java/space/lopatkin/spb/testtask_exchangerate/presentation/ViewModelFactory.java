package space.lopatkin.spb.testtask_exchangerate.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ExchangeValutesDataSource;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ValCursDataSource;
import space.lopatkin.spb.testtask_exchangerate.presentation.ui.ExchangeValutesViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    //3) имплементимся от ViewModelProvider.Factory и остальное по мелочи (готово)

    private final ExchangeValutesDataSource mExchangeValutesDataSource;
    private final ValCursDataSource mValCursDataSource;


    public ViewModelFactory(ExchangeValutesDataSource valutesDataSource, ValCursDataSource valCursDataSource) {
        mExchangeValutesDataSource = valutesDataSource;
        mValCursDataSource = valCursDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ExchangeValutesViewModel.class)) {
            return (T) new ExchangeValutesViewModel(mExchangeValutesDataSource, mValCursDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}


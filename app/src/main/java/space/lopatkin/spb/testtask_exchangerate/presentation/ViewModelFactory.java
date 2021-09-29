package space.lopatkin.spb.testtask_exchangerate.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.testtask_exchangerate.ExchangeValutesDataSource;
import space.lopatkin.spb.testtask_exchangerate.presentation.ui.ExchangeValutesViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    //3) имплементимся от ViewModelProvider.Factory и остальное по мелочи (готово)

    private final ExchangeValutesDataSource mExchangeValutesDataSource;


    public ViewModelFactory(ExchangeValutesDataSource valutesDataSource) {
        mExchangeValutesDataSource = valutesDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ExchangeValutesViewModel.class)) {
            return (T) new ExchangeValutesViewModel(mExchangeValutesDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}


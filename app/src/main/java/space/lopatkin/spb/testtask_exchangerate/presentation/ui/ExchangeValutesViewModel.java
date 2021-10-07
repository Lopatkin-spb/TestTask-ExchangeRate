package space.lopatkin.spb.testtask_exchangerate.presentation.ui;


import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ExchangeValutesDataSource;
import space.lopatkin.spb.testtask_exchangerate.data.local.Dates;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ValCursDataSource;
import space.lopatkin.spb.testtask_exchangerate.domain.usecase.GetValCursFromApiUseCase;
import space.lopatkin.spb.testtask_exchangerate.domain.usecase.GetValutesFromDbUseCase;
import space.lopatkin.spb.testtask_exchangerate.domain.usecase.SaveValutesUseCase;
import space.lopatkin.spb.testtask_exchangerate.utils.xvlConverter.Valute;
import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ValCurs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.*;

public class ExchangeValutesViewModel extends ViewModel {

    //4) создаем ExchangeValutesViewModel


    private final ExchangeValutesDataSource mDataSource;
    private final ValCursDataSource mNetworkDataSource;
    private MutableLiveData<States> mState;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<Valute> mListValutes = new ArrayList();
    private final ExchangeValutes mItemApi = new ExchangeValutes();

    private boolean mToast = true;
    private boolean mDialog = false;


    public ExchangeValutesViewModel(ExchangeValutesDataSource dataSource
            , ValCursDataSource valCursDataSource) {
        mDataSource = dataSource;
        mNetworkDataSource = valCursDataSource;
        if (mState == null) {
            mState = new MutableLiveData<>();
        }
        update();
    }


    public void update() {

        States.MESSAGE.setText(UPDATE);
        States.MESSAGE.setView(mToast);
        mState.setValue(States.MESSAGE);
        mCompositeDisposable.add(mGetValCursUseCase()
                .timeout(4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Thread.sleep(7000);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        //в любом случае в конце происходит загрузка из бд
                        getDataFromDb();
                    }
                })
                .subscribe(new Consumer<ValCurs>() {
                    @Override
                    public void accept(ValCurs valCurs) throws Exception {
                        //тут происходит импорт в бд
                        Log.d(TAG_MY_LOGS, "Update from API success.");
                        updateDB(valCurs);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG_MY_LOGS, "Unable to get data from API.", throwable);
                        States.MESSAGE.setText(ERROR_UPDATE);
                        States.MESSAGE.setView(mToast);
                        mState.setValue(States.MESSAGE);
                    }
                })
        );

    }


    private void updateDB(ValCurs valCurs) {

        mListValutes.clear();
        mListValutes = valCurs.getListValutes();

        mItemApi.setId(1);
        mItemApi.setDate(valCurs.getDate());

        mItemApi.setValute1(mListValutes.get(0).getNumCode() + "/" + mListValutes.get(0).getCharCode() + "/" + mListValutes.get(0).getNominal() + "/" + mListValutes.get(0).getName() + "/" + mListValutes.get(0).getValue());
        mItemApi.setValute2(mListValutes.get(1).getNumCode() + "/" + mListValutes.get(1).getCharCode() + "/" + mListValutes.get(1).getNominal() + "/" + mListValutes.get(1).getName() + "/" + mListValutes.get(1).getValue());
        mItemApi.setValute3(mListValutes.get(2).getNumCode() + "/" + mListValutes.get(2).getCharCode() + "/" + mListValutes.get(2).getNominal() + "/" + mListValutes.get(2).getName() + "/" + mListValutes.get(2).getValue());
        mItemApi.setValute4(mListValutes.get(3).getNumCode() + "/" + mListValutes.get(3).getCharCode() + "/" + mListValutes.get(3).getNominal() + "/" + mListValutes.get(3).getName() + "/" + mListValutes.get(3).getValue());
        mItemApi.setValute5(mListValutes.get(4).getNumCode() + "/" + mListValutes.get(4).getCharCode() + "/" + mListValutes.get(4).getNominal() + "/" + mListValutes.get(4).getName() + "/" + mListValutes.get(4).getValue());
        mItemApi.setValute6(mListValutes.get(5).getNumCode() + "/" + mListValutes.get(5).getCharCode() + "/" + mListValutes.get(5).getNominal() + "/" + mListValutes.get(5).getName() + "/" + mListValutes.get(5).getValue());
        mItemApi.setValute7(mListValutes.get(6).getNumCode() + "/" + mListValutes.get(6).getCharCode() + "/" + mListValutes.get(6).getNominal() + "/" + mListValutes.get(6).getName() + "/" + mListValutes.get(6).getValue());
        mItemApi.setValute8(mListValutes.get(7).getNumCode() + "/" + mListValutes.get(7).getCharCode() + "/" + mListValutes.get(7).getNominal() + "/" + mListValutes.get(7).getName() + "/" + mListValutes.get(7).getValue());
        mItemApi.setValute9(mListValutes.get(8).getNumCode() + "/" + mListValutes.get(8).getCharCode() + "/" + mListValutes.get(8).getNominal() + "/" + mListValutes.get(8).getName() + "/" + mListValutes.get(8).getValue());
        mItemApi.setValute10(mListValutes.get(9).getNumCode() + "/" + mListValutes.get(9).getCharCode() + "/" + mListValutes.get(9).getNominal() + "/" + mListValutes.get(9).getName() + "/" + mListValutes.get(9).getValue());

        mItemApi.setValute11(mListValutes.get(10).getNumCode() + "/" + mListValutes.get(10).getCharCode() + "/" + mListValutes.get(10).getNominal() + "/" + mListValutes.get(10).getName() + "/" + mListValutes.get(10).getValue());
        mItemApi.setValute12(mListValutes.get(11).getNumCode() + "/" + mListValutes.get(11).getCharCode() + "/" + mListValutes.get(11).getNominal() + "/" + mListValutes.get(11).getName() + "/" + mListValutes.get(11).getValue());
        mItemApi.setValute13(mListValutes.get(12).getNumCode() + "/" + mListValutes.get(12).getCharCode() + "/" + mListValutes.get(12).getNominal() + "/" + mListValutes.get(12).getName() + "/" + mListValutes.get(12).getValue());
        mItemApi.setValute14(mListValutes.get(13).getNumCode() + "/" + mListValutes.get(13).getCharCode() + "/" + mListValutes.get(13).getNominal() + "/" + mListValutes.get(13).getName() + "/" + mListValutes.get(13).getValue());
        mItemApi.setValute15(mListValutes.get(14).getNumCode() + "/" + mListValutes.get(14).getCharCode() + "/" + mListValutes.get(14).getNominal() + "/" + mListValutes.get(14).getName() + "/" + mListValutes.get(14).getValue());
        mItemApi.setValute16(mListValutes.get(15).getNumCode() + "/" + mListValutes.get(15).getCharCode() + "/" + mListValutes.get(15).getNominal() + "/" + mListValutes.get(15).getName() + "/" + mListValutes.get(15).getValue());
        mItemApi.setValute17(mListValutes.get(16).getNumCode() + "/" + mListValutes.get(16).getCharCode() + "/" + mListValutes.get(16).getNominal() + "/" + mListValutes.get(16).getName() + "/" + mListValutes.get(16).getValue());
        mItemApi.setValute18(mListValutes.get(17).getNumCode() + "/" + mListValutes.get(17).getCharCode() + "/" + mListValutes.get(17).getNominal() + "/" + mListValutes.get(17).getName() + "/" + mListValutes.get(17).getValue());
        mItemApi.setValute19(mListValutes.get(18).getNumCode() + "/" + mListValutes.get(18).getCharCode() + "/" + mListValutes.get(18).getNominal() + "/" + mListValutes.get(18).getName() + "/" + mListValutes.get(18).getValue());
        mItemApi.setValute20(mListValutes.get(19).getNumCode() + "/" + mListValutes.get(19).getCharCode() + "/" + mListValutes.get(19).getNominal() + "/" + mListValutes.get(19).getName() + "/" + mListValutes.get(19).getValue());

        mItemApi.setValute21(mListValutes.get(20).getNumCode() + "/" + mListValutes.get(20).getCharCode() + "/" + mListValutes.get(20).getNominal() + "/" + mListValutes.get(20).getName() + "/" + mListValutes.get(20).getValue());
        mItemApi.setValute22(mListValutes.get(21).getNumCode() + "/" + mListValutes.get(21).getCharCode() + "/" + mListValutes.get(21).getNominal() + "/" + mListValutes.get(21).getName() + "/" + mListValutes.get(21).getValue());
        mItemApi.setValute23(mListValutes.get(22).getNumCode() + "/" + mListValutes.get(22).getCharCode() + "/" + mListValutes.get(22).getNominal() + "/" + mListValutes.get(22).getName() + "/" + mListValutes.get(22).getValue());
        mItemApi.setValute24(mListValutes.get(23).getNumCode() + "/" + mListValutes.get(23).getCharCode() + "/" + mListValutes.get(23).getNominal() + "/" + mListValutes.get(23).getName() + "/" + mListValutes.get(23).getValue());
        mItemApi.setValute25(mListValutes.get(24).getNumCode() + "/" + mListValutes.get(24).getCharCode() + "/" + mListValutes.get(24).getNominal() + "/" + mListValutes.get(24).getName() + "/" + mListValutes.get(24).getValue());
        mItemApi.setValute26(mListValutes.get(25).getNumCode() + "/" + mListValutes.get(25).getCharCode() + "/" + mListValutes.get(25).getNominal() + "/" + mListValutes.get(25).getName() + "/" + mListValutes.get(25).getValue());
        mItemApi.setValute27(mListValutes.get(26).getNumCode() + "/" + mListValutes.get(26).getCharCode() + "/" + mListValutes.get(26).getNominal() + "/" + mListValutes.get(26).getName() + "/" + mListValutes.get(26).getValue());
        mItemApi.setValute28(mListValutes.get(27).getNumCode() + "/" + mListValutes.get(27).getCharCode() + "/" + mListValutes.get(27).getNominal() + "/" + mListValutes.get(27).getName() + "/" + mListValutes.get(27).getValue());
        mItemApi.setValute29(mListValutes.get(28).getNumCode() + "/" + mListValutes.get(28).getCharCode() + "/" + mListValutes.get(28).getNominal() + "/" + mListValutes.get(28).getName() + "/" + mListValutes.get(28).getValue());
        mItemApi.setValute30(mListValutes.get(29).getNumCode() + "/" + mListValutes.get(29).getCharCode() + "/" + mListValutes.get(29).getNominal() + "/" + mListValutes.get(29).getName() + "/" + mListValutes.get(29).getValue());

        mItemApi.setValute31(mListValutes.get(30).getNumCode() + "/" + mListValutes.get(30).getCharCode() + "/" + mListValutes.get(30).getNominal() + "/" + mListValutes.get(30).getName() + "/" + mListValutes.get(30).getValue());
        mItemApi.setValute32(mListValutes.get(31).getNumCode() + "/" + mListValutes.get(31).getCharCode() + "/" + mListValutes.get(31).getNominal() + "/" + mListValutes.get(31).getName() + "/" + mListValutes.get(31).getValue());
        mItemApi.setValute33(mListValutes.get(32).getNumCode() + "/" + mListValutes.get(32).getCharCode() + "/" + mListValutes.get(32).getNominal() + "/" + mListValutes.get(32).getName() + "/" + mListValutes.get(32).getValue());
        mItemApi.setValute34(mListValutes.get(33).getNumCode() + "/" + mListValutes.get(33).getCharCode() + "/" + mListValutes.get(33).getNominal() + "/" + mListValutes.get(33).getName() + "/" + mListValutes.get(33).getValue());

//        getListIdAndDateFromDb();
        insertDataToDbMain(mItemApi);
    }

    private void insertDataToDbMain(ExchangeValutes in) {
        mCompositeDisposable.add(mGetValutesUseCase()
                .delaySubscription(2, TimeUnit.SECONDS)
                .map(new Function<ExchangeValutes, ExchangeValutes>() {
                    @Override
                    public ExchangeValutes apply(@NonNull ExchangeValutes exchangeValutes) {
                        if (in.getDate().equals(exchangeValutes.getDate())) {
                            in.setId(exchangeValutes.getId());
                        } else if (!in.getDate().equals(exchangeValutes.getDate())) {
                            in.setId(exchangeValutes.getId() + 1);
                        } else if (in.getDate() == null) {
                            in.setId(1);
                        }
                        return in;
                    }
                })
                .ignoreElement()
                //переключение потока для отредактированных данных
                .concatWith(mSaveValutesUseCase(in))
                .onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    @Override
                    public CompletableSource apply(@NonNull Throwable throwable) throws Exception {
                        //(если нет записей в бд - ошибка)
                        // при ошибке включение потока заново без редактирования данных
                        return mSaveValutesUseCase(in);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Action() {
                               @Override
                               public void run() throws Exception {
                                   Log.d(TAG_MY_LOGS, "Insert to db success.");
                                   States.MESSAGE.setText(GOOD_UPDATE_GOOD_INSERT);
                                   States.MESSAGE.setView(mToast);
                                   mState.setValue(States.MESSAGE);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d(TAG_MY_LOGS, "Unable to insert ExchangeValutes to db.", throwable);
                                   States.MESSAGE.setText(GOOD_UPDATE_ERROR_INSERT);
                                   States.MESSAGE.setView(mToast);
                                   mState.setValue(States.MESSAGE);
                               }
                           }
                ));


    }

    private void getDataFromDb() {
        mCompositeDisposable.add(mDataSource
                .getLastExchangeValutes()
                .delaySubscription(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent(new BiConsumer<ExchangeValutes, Throwable>() {
                    @Override
                    public void accept(ExchangeValutes exchangeValutes, Throwable throwable) throws Exception {
                        //показ ЛОАДИНГ
                        States.MESSAGE.setText(LOADING);
                        States.MESSAGE.setView(mToast);
                        mState.setValue(States.MESSAGE);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnEvent(new BiConsumer<ExchangeValutes, Throwable>() {
                    @Override
                    public void accept(ExchangeValutes exchangeValutes, Throwable throwable) throws Exception {
                        Thread.sleep(3000);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ExchangeValutes>() {
                               @Override
                               public void accept(ExchangeValutes valutes) throws Exception {
                                   Log.d(TAG_MY_LOGS, "Get data from db success.");
                                   States.MESSAGE.setText(GOOD_LOAD);
                                   States.MESSAGE.setView(mDialog);
                                   mState.setValue(States.MESSAGE);
                                   States.NORMAL_VIEW.setData(valutes);
                                   mState.setValue(States.NORMAL_VIEW);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d(TAG_MY_LOGS, "Unable to get data from db.", throwable);
                                   States.MESSAGE.setText(ERROR_LOAD);
                                   States.MESSAGE.setView(mDialog);
                                   mState.setValue(States.MESSAGE);
                                   mState.setValue(States.DEFAULT_VIEW);
                               }
                           }
                ));
    }

    private void getListIdAndDateFromDb() {
        mCompositeDisposable.add(mDataSource
                .getAllDates()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Dates>>() {
                    @Override
                    public void accept(List<Dates> dates) throws Exception {
                        Log.d(TAG_MY_LOGS, "--> VIEWMODEL test: list dates=" + dates);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG_MY_LOGS, "--> VIEWMODEL test: error", throwable);
                    }
                }));

    }

    public MutableLiveData<States> getState() {
        return mState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }


    private Single<ValCurs> mGetValCursUseCase() {
        GetValCursFromApiUseCase getValCursFromApiUseCase = new GetValCursFromApiUseCase(mNetworkDataSource);
        return getValCursFromApiUseCase
                .execute();
    }


    private Single<ExchangeValutes> mGetValutesUseCase() {
        GetValutesFromDbUseCase getValutesFromDbUseCase = new GetValutesFromDbUseCase(mDataSource);
        return getValutesFromDbUseCase
                .execute();
    }


    private Completable mSaveValutesUseCase(ExchangeValutes ex) {
        SaveValutesUseCase saveValutesUseCase = new SaveValutesUseCase(mDataSource);
        return saveValutesUseCase
                .execute(ex);
    }

}


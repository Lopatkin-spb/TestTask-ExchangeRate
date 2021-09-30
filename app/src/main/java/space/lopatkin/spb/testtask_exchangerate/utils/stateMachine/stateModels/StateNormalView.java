package space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.stateModels;

import space.lopatkin.spb.testtask_exchangerate.data.db.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;

public class StateNormalView implements States {

    private ExchangeValutes mValutes;


    public StateNormalView() {
    }

    public StateNormalView(ExchangeValutes valutes) {
        this.mValutes = valutes;
    }

    public ExchangeValutes getValutes() {
        return mValutes;
    }

    public void setData(ExchangeValutes valutes) {
        this.mValutes = valutes;
    }


    @Override
    public String toString() {
        return "StateNormalView{" +
                "mValutes=" + mValutes +
                '}';
    }
}

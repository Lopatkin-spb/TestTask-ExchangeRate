package space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.stateModels;

import space.lopatkin.spb.testtask_exchangerate.data.db.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;

public class StateNormalView implements States {

    private ExchangeValutes valutes;


    public StateNormalView() {
    }

    public StateNormalView(ExchangeValutes valutes) {
        this.valutes = valutes;
    }

    public ExchangeValutes getValutes() {
        return valutes;
    }

    public void setData(ExchangeValutes valutes) {
        this.valutes = valutes;
    }


    @Override
    public String toString() {
        return "StateNormalView{" +
                "valutes=" + valutes +
                '}';
    }
}

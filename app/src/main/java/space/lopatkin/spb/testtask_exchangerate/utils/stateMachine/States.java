package space.lopatkin.spb.testtask_exchangerate.utils.stateMachine;

import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.stateModels.*;

public interface States {

    public StateMessage MESSAGE = new StateMessage();

    public StateDefaultView DEFAULT_VIEW = new StateDefaultView();

    public StateNormalView NORMAL_VIEW = new StateNormalView();


}


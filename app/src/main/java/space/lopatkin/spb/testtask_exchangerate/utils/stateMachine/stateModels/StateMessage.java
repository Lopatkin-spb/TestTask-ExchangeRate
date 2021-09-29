package space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.stateModels;

import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;

public class StateMessage implements States {

    private int text;
    private boolean toast;


    public StateMessage() {
    }

    public StateMessage(int text) {
        this.text = text;
    }

    public int getText() {
        return text;
    }

    public void setText(int statusStates) {
        this.text = statusStates;
    }

    public boolean isToast() {
        return toast;
    }

    public void setToast(boolean toast) {
        this.toast = toast;
    }

    @Override
    public String toString() {
        return "StateMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}

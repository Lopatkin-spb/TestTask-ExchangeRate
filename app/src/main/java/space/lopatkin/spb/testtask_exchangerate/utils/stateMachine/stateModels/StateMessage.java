package space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.stateModels;

import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;

public class StateMessage implements States {

    private int mText;
    private boolean mView;


    public StateMessage() {
    }

    public StateMessage(int text) {
        this.mText = text;
    }

    public int getText() {
        return mText;
    }

    public void setText(int statusStates) {
        this.mText = statusStates;
    }

    public boolean isToast() {
        return mView;
    }

    public void setView(boolean toast) {
        this.mView = toast;
    }

    @Override
    public String toString() {
        return "StateMessage{" +
                "text=" + mText +
                ", view=" + mView +
                '}';
    }
}

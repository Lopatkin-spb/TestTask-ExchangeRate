package space.lopatkin.spb.testtask_exchangerate.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.DialogFragment;
import space.lopatkin.spb.testtask_exchangerate.R;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.DIALOG_BUTTON_NEGATIVE;

public class MiniDialog extends DialogFragment {
    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    private int message;

    public static MiniDialog newInstance(int param1) {
        MiniDialog fragment = new MiniDialog();
        Bundle args = new Bundle();
        args.putInt(KEY_MESSAGE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getInt(KEY_MESSAGE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setNegativeButton(DIALOG_BUTTON_NEGATIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}

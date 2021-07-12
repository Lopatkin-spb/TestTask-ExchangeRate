package space.lopatkin.spb.testtask_exchangerate.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import space.lopatkin.spb.testtask_exchangerate.R;

public class DialogManager {
    private NetworkReceiver receiver = new NetworkReceiver();
    private AlertDialog dialog;
    private View dialogLayout;
    private TextView dialogText;
    private AppCompatButton dialogButtonRetry;

    public void showDialog(Context context, Intent intent, int message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        dialogLayout = LayoutInflater.
                from(context).
                inflate(R.layout.dialog_layout, null);
        builder.setView(dialogLayout);

        dialogText = dialogLayout.findViewById(R.id.dialog_view_text);
        dialogButtonRetry = dialogLayout.findViewById(R.id.dialog_button_retry);

        dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

        dialog.getWindow().setGravity(Gravity.CENTER);

        dialogText.setText(message);
        dialogButtonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiver.onReceive(context, intent);
                dialog.dismiss();
            }
        });
    }
}

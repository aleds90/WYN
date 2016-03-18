package wyc.whatyouneed.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by sjnao on 15/03/2016.
 */
public class Utility {

    public AlertDialog get_generic_alert_dialog(Context context, String message){

        AlertDialog.Builder alertdialogbuilder =  new AlertDialog.Builder(
                context);
        alertdialogbuilder.setTitle("Something went wrong :(");
        alertdialogbuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertdialogbuilder.create();

        return  alertDialog;
    }

}

package com.wms.mwt.e_wejob.utility;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class WeJobUtil {
    public static boolean isEmpty( EditText...  edts){
        for (EditText edt : edts){
            if(TextUtils.isEmpty(edt.getText()))
                return true;
        }

        return false;
    }

    public static void showToast(Context context,String msg){
        Toast toast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}

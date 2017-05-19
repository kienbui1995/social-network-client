package com.joker.thanglong.Ultil;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.joker.thanglong.R;

/**
 * Created by joker on 5/11/17.
 */

public class DialogUlti {
    public static void initDiaglog(Context context, String content, MaterialDialog.SingleButtonCallback callback){
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText(R.string.agree)
                .theme(Theme.LIGHT)
                .onPositive(callback)
                .negativeText(R.string.disagree)
                .show();
    }
}

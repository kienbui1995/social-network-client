package com.joker.thanglong.Ultil;

import android.content.Context;
import android.net.Uri;

import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;

/**
 * Created by joker on 3/15/17.
 */

public class ImageUlti {
    Context context;

    public ImageUlti(Context context) {
        this.context = context;
    }
    public void loadImage(BigImageView bigImageView,String src){

        bigImageView.setProgressIndicator(new ProgressPieIndicator());
        bigImageView.showImage(
                Uri.parse(src)
        );
    }
}

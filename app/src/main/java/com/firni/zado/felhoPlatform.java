package com.firni.zado;

import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class felhoPlatform extends Platform {

    public felhoPlatform(int screenWidth, int screenHeight, int x, int y, Context context) {
        super(screenWidth, screenHeight, x, y, context);
        vx = 0;
        vy = 0;
        width = baseWidth;
        height = baseHeight;
        type = PlatType.onetouch;
        if (!setBitmap(context, R.drawable.feherplatform)) Log.e(TAG, "Nem elérhető Platform.bitmap.");
    }
}

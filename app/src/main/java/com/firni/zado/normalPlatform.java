package com.firni.zado;

import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class normalPlatform extends Platform {
    public normalPlatform(int screenWidth, int screenHeight, int x, int y, int score, Context context) {
        super(screenWidth, screenHeight, x, y, context);
        int rv = (int)(Math.random() * 1000);
        int prop;
        double tempVx;
        if(score < 4000) {
            prop = 900;
            tempVx = 0.1;
        }
        else if(score < 8000) {
            prop = 700;
            tempVx = 0.15;
        }
        else if(score < 12000) {
            prop = 500;
            tempVx = 0.2;
        }
        else if(score < 16000) {
            prop = 300;
            tempVx = 0.3;
        }
        else if(score < 20000) {
            prop = 100;
            tempVx = 0.4;
        }
        else {
            prop = 10;
            tempVx = 0.5;
        }
        if(rv > prop + (1000 - prop) / 2) vx = tempVx;
        else if(rv > prop) vx = -tempVx;
        else vx = 0;
        vy = 0;
        width = baseWidth;     height = baseHeight;
        type = PlatType.normal;
        int drawable = vx == 0 ? R.drawable.alapplatform : R.drawable.kekplatform;
        if (!setBitmap(context, drawable)) Log.e(TAG, "Nem elérhető Platform.bitmap.");
    }
}

package com.firni.zado;

import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class ugrosPlatform extends Platform {

    public ugrosPlatform(int screenWidth, int screenHeight, int x, int y, int score, Context context){
        super(screenWidth, screenHeight, x, y, context);
        int rv = (int)(Math.random() * 1000);
        double tempVx;
        if(score < 4000) tempVx = 0.1;
        else if(score < 8000) tempVx = 0.15;
        else if(score < 12000) tempVx = 0.2;
        else if(score < 16000) tempVx = 0.3;
        else if(score < 20000) tempVx = 0.4;
        else tempVx = 0.5;
        if(rv > 750) vx = tempVx;
        else if(rv > 500) vx = -tempVx;
        else vx = 0;
        vy = 0;
        width = baseWidth;    height = 81;
        type = PlatType.withspring;
        if (!setBitmap(context, R.drawable.ugrasalapplatform)) Log.e(TAG, "Nem elérhető Platform.bitmap.");
    }
}

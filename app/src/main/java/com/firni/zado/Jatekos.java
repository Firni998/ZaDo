package com.firni.zado;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import static android.content.ContentValues.TAG;

class Jatekos extends Sprite {
    private boolean still;
    private boolean jatekVege;
    private boolean isEquipJetPack;
    private int jetPackLastTime;
    private boolean isWearingCloak;
    private int maxCloakLastTime;
    private int cloakLastTime;
    protected int direction;

    Jatekos(int screenWidth, int screenHeight, Context context){
        still = false;
        jatekVege = false;
        isEquipJetPack = false;
        jetPackLastTime = 0;
        isWearingCloak = false;
        maxCloakLastTime = 240;
        cloakLastTime = 0;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        width = 138;
        height = 135;
        x = (screenWidth - 138) / 2;
        y = screenHeight - 135;
        direction = 0;
        vx = 0;
        vy = -1.96;
        additionVy = 0;
        g = 0.00322;
        if (!setBitmap(context, R.drawable.lmainkarakter)) Log.e(TAG, "Unable to set jatekos.bitmap.");
        if (!setSecBitmap(context, R.drawable.rmainkarakter)) Log.e(TAG, "Unable to set jatekos.secBitmap.");
    }
    public void refresh(Context context) {

        if(isEquipJetPack) {
            isWearingCloak = false;
            cloakLastTime = 0;
            vy = -6;
            g = 0;
            jetPackLastTime--;
            if(jetPackLastTime <= 0) {
                isEquipJetPack = false;
                g = 0.00322;
                vy = -1.96;
                width = 138;
                height = 135;
                if (!setBitmap(context, R.drawable.lmainkarakter)) Log.e(TAG, "Unable to set jatekos.bitmap.");
                if (!setSecBitmap(context, R.drawable.rmainkarakter)) Log.e(TAG, "Unable to set jatekos.secBitmap.");
            }
        }

        if(isWearingCloak) {
            cloakLastTime--;
            if(cloakLastTime <= 0) {
                isWearingCloak = false;
                if (!setBitmap(context, R.drawable.lmainkarakter)) Log.e(TAG, "Unable to set doodle.bitmap.");
                if (!setSecBitmap(context, R.drawable.rmainkarakter)) Log.e(TAG, "Unable to set doodle.secBitmap.");
            }
        }

        x += (int) (vx * interval);
        if (x < -width / 2) x = screenWidth - width / 2 - 1;
        else if (x > screenWidth - width / 2) x = -width / 2 + 1;

        if(!still) y = y + (int) (vy * interval);
        vy = vy + g * interval;
        if (y <= screenHeight / 2 && vy < 0) still = true;
        else still = false;

        if (y > screenHeight)
            jatekVege = true;
    }

    public boolean isStill() {
        return still;
    }

    public void setVx(double roll) {
        vx = - 4.8 * Math.sin(roll / 180 * Math.PI);
        if(direction == 0 && vx > 0) {

            direction = 1;
            x = x + 46;
        }
        else if(direction == 1 && vx < 0) {

            direction = 0;
            x = x - 46;
        }

    }

    public void setJetPackLastTime(int lastTime) {
        jetPackLastTime = lastTime;
    }

    public void yesJetPackOn(Context context) {

        isEquipJetPack = true;
        width = 222;
        height = 212;
        if (!setBitmap(context, R.drawable.ljetpackmainkarakter)) Log.e(TAG, "Unable to set jatekos.bitmap.");
        if (!setSecBitmap(context, R.drawable.rjetpackmainkarakter)) Log.e(TAG, "Unable to set jatekos.secBitmap.");
    }

    public boolean isEquipJetPack() {
        return isEquipJetPack;
    }

    public void wearCloak(Context context){
        if(!isWearingCloak) {
            if (!setBitmap(context, R.drawable.ltpmainkarakter)) Log.e(TAG, "Unable to set jatekos.bitmap.");
            if (!setSecBitmap(context, R.drawable.rtpmainkarakter)) Log.e(TAG, "Unable to set jatekos.secBitmap.");
        }
        isWearingCloak = true;
        cloakLastTime = maxCloakLastTime;
    }

    public boolean isWearingCloak() {
        return isWearingCloak;
    }

    public void yesJatekVege() {
        jatekVege = true;
    }

    public boolean isJatekVege() {
        return jatekVege;
    }


    public void drawBitmap(Canvas canvas, Paint paint) {
        if(direction == 0) drawBitmap(canvas, paint, 0);
        else drawBitmap(canvas, paint, 1);
    }
}

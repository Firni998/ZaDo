package com.firni.zado;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import static android.content.ContentValues.TAG;

class JetPack extends Sprite {
    private boolean isEquipped;
    private boolean isTimeOut;
    private int maxLastTime;
    private int lastTime;
    private int numPlat;
    JetPack(int screenWidth, int screenHeight, Platform plat, int numPlat, Context context) {
        isEquipped = false;
        isTimeOut = false;
        maxLastTime = 160;
        lastTime = 0;
        this.numPlat = numPlat;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        width = 82;     height = 124;
        x = plat.x + plat.width / 2 - width / 2;
        y = plat.y - height;
        vx = plat.vx;       vy = plat.vy;
        additionVy = plat.additionVy;
        g = plat.g;

        if(!setBitmap(context, R.drawable.jetpack)) Log.e(TAG, "Nem elérhető Jetpack.bitmap");
        if(!setSecBitmap(context, R.drawable.jetpackfel)) Log.e(TAG, "Nem elérhető Jetpack.secBitmap");
    }

    boolean refresh(Platform [] plat, Jatekos jatekos) {

        if(!isEquipped && !isTimeOut) {
            if(numPlat >= 0) {

                x = plat[numPlat].x + plat[numPlat].width / 2 - width / 2;
                y = plat[numPlat].y - height;
            }
            else {

                vy = 1;
                double sumVy = vy + additionVy;
                y = y + (int) (sumVy * interval);
                if(y > screenHeight)
                    return false;
            }
            return true;
        }
        else if(isEquipped && !isTimeOut) {

            lastTime--;
            if(lastTime <= 2) {
                lastTime = 0;
                isEquipped = false;
                isTimeOut = true;
                if(jatekos.direction == 0) {

                    x = jatekos.x + 149;
                    y = jatekos.y + 5;
                    vx = 1;
                    vy = 0;
                    g = 0.00322;
                }
                else {

                    x = jatekos.x + jatekos.width - 149;
                    y = jatekos.y + 5;
                    vx = -1;
                    vy = 0;
                    g = 0.00322;
                }
            }
            return true;
        }
        else if(!isEquipped) {
            x += (int) (vx * interval);
            if(x < -width / 2) return false;
            else if(x > screenWidth - width / 2) return  false;

            double sumVy = vy + additionVy;
            y = y + (int) (sumVy * interval);
            vy = vy + g * interval;
            if(y > screenHeight)
                return false;
        }
        else {

            return false;
        }
        return  true;
    }

    void drawBitmap(Canvas canvas, Paint paint) {
        if(!isEquipped && !isTimeOut)
            drawBitmap(canvas, paint, 0);
        else if(!isEquipped)
            drawBitmap(canvas, paint, 1);
    }

    void impactCheck(Jatekos jatekos, Context context) {

        if(!isEquipped && !isTimeOut) {

            if (jatekos.y < this.y + this.height
                    && jatekos.y + jatekos.height > this.y
                    && jatekos.x < this.x + this.width
                    && jatekos.x + jatekos.width > this.x) {
                this.isEquipped = true;
                this.isTimeOut = false;
                this.numPlat = -1;
                this.lastTime = maxLastTime;
                jatekos.setJetPackLastTime(maxLastTime);
                jatekos.yesJetPackOn(context);
            }
        }
    }

    int getNumPlat() {
        return numPlat;
    }

    void platInvalidate() {

        if(!isEquipped && !isTimeOut)
            numPlat = -1;
    }
}

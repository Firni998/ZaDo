package com.firni.zado;

import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

class Szornyek extends Sprite {
    private int localX;
    private int localY;
    private double localVx, localVy, maxLocalVx, maxLocalVy;
    Szornyek(int screenWidth, int screenHeight, int baseY, Context context) {
        localX = 0;
        localY = 0;
        maxLocalVx = 0.3;
        maxLocalVy = 0.3;
        localVx = maxLocalVx;
        localVy = maxLocalVy;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        vx = 0;
        vy = 0;
        additionVy = 0;
        g = 0;
        int rv = (int)(Math.random() * 1000);
        int drawable;
        if(rv > 700) {
            drawable = R.drawable.szorny1;
            width = 187;
            height = 166;
        }
        else if(rv > 300) {
            drawable = R.drawable.szorny2;
            width = 262;
            height = 158;
        }
        else {
            drawable = R.drawable.szorny3;
            width = 157;
            height = 120;
        }
        this.x = (int) (Math.random() * (screenWidth - width - 50) + 50);
        this.y = baseY - height - 10;
        if(!setBitmap(context, drawable)) Log.e(TAG, "Nem elérhető Monster.bitmap");
    }

    boolean refresh() {


        localY += (int) (localVy * interval);
        if(localY > 30) localVy = -maxLocalVy;
        else if(localY < -30) localVy = maxLocalVy;

        double sumVy = vy + localVy + additionVy;
        int tempY = y + (int) (sumVy * interval);
        if(tempY > screenHeight) return false;
        y = tempY;

        localX += (int) (localVx * interval);
        if(localX > 30) localVx = -maxLocalVx;
        else if(localX < -30) localVx = maxLocalVx;

        double sumVx = vx + localVx;
        int tempX = x + (int)(sumVx * interval);
        if(tempX >= screenWidth - this.width || tempX <= 0)
            vx = -vx;
        else x = tempX;
        return true;
    }

    void impactCheck(Jatekos jatekos, Context context) {

        int footX1, footX2;
        if (jatekos.direction == 0) {

            footX1 = jatekos.x + 46;
            footX2 = jatekos.x + jatekos.width - 11;
        } else {
            footX1 = jatekos.x + 11;
            footX2 = jatekos.x + jatekos.width - 46;
        }

        if (jatekos.vy > 0) {

            if (jatekos.y + jatekos.height < this.y + this.height
                    && jatekos.y + jatekos.height > this.y
                    && footX1 < this.x + this.width
                    && footX2 > this.x) {

                jatekos.vy = -3;
                vy = 1.7;
            }
        } else if (jatekos.vy <= 0) {

            if (!jatekos.isWearingCloak()
                    &&jatekos.y < this.y + this.height
                    && jatekos.y + jatekos.height > this.y
                    && jatekos.x < this.x + this.width
                    && jatekos.x + jatekos.width > this.x
                    && !jatekos.isEquipJetPack())
                jatekos.yesJatekVege();
        }
    }
}

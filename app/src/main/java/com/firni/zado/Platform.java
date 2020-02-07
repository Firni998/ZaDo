package com.firni.zado;

import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

class Platform extends Sprite {
    protected int type;
    protected int baseWidth, baseHeight;
    protected boolean valid;
    public Platform(int screenWidth, int screenHeight, int x, int y, Context context){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = x;
        this.y = y;
        additionVy = 0;
        g = 0;
        baseWidth = 194;
        baseHeight = 52;
        valid = true;
    }

    boolean refresh(){

        double sumVy = vy + additionVy;
        int tempY = y + (int)(sumVy * interval);
        if(tempY > screenHeight ) {
            if (type == PlatType.broken && height == 108)
                valid = false;
            return false;
        }
        y = tempY;

        int tempX = x + (int)(vx * interval);
        if(tempX >= screenWidth - this.width || tempX <= 0)
            vx = -vx;
        else x = tempX;
        return true;
    }

    void impactCheck(Jatekos jatekos, Context context) {

        int footX1, footX2;
        if(jatekos.direction == 0) {

            footX1 = jatekos.x + 46;
            footX2 = jatekos.x + jatekos.width - 11;
        }
        else {
            footX1 = jatekos.x + 11;
            footX2 = jatekos.x + jatekos.width - 46;
        }


        if (jatekos.y + jatekos.height < this.y + this.height
                && jatekos.y + jatekos.height > this.y + this.height - this.baseHeight
                && footX1 < this.x + this.width
                && footX2 > this.x) {

            if(type != PlatType.broken && valid) {
                jatekos.vy = -1.96;
                if(type == PlatType.onetouch) valid = false;
            }
            else if(type == PlatType.broken) {
                width = 194;
                height = 108;
                vy = 1;
                if (!setBitmap(context, R.drawable.torotteltortplatform))
                    Log.e(TAG, "Nem elérhető Platform.bitmap.");
            }
        }


        if(type == PlatType.withspring
                && jatekos.vy > 0
                && jatekos.y + jatekos.height < this.y + 29
                && jatekos.y + jatekos.height > this.y
                && footX1 < this.x + this.width - 91
                && footX2 > this.x + 48) {

            jatekos.vy = -3.5;
            this.y = this.y - (132 - this.height);
            this.height = 132;
            if (!setBitmap(context, R.drawable.ugrasaktivplatform))
                Log.e(TAG, "Nem elérhető Platform.bitmap.");
        }

    }
}

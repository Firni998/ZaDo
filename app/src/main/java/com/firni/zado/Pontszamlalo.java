package com.firni.zado;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import static android.content.ContentValues.TAG;

class Pontszamlalo extends Sprite {
    private int score;
    private int scoreX, scoreY;
    Pontszamlalo(int screenWidth, int screenHeight, Context context) {
        score = 0;
        scoreX = 45;
        scoreY = 70;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        width = screenWidth;
        height = (int) (screenWidth / 1080 * 148);
        x = 0;      y = 0;
        vx = 0;     vy = 0;
        additionVy = 0;
        g = 0;

        if(!setBitmap(context, R.drawable.title)) Log.e(TAG, "Nem elérhető pontszamlalo.bitmap.");
    }

    void addScore(int deltaY) {
        score += deltaY / 2;
    }

    int getScore(){
        return score;
    }

    @Override
    public void drawBitmap(Canvas canvas, Paint paint, int num) {
        super.drawBitmap(canvas, paint, 0);
        try {
            canvas.drawText(Integer.toString(score), scoreX, 70, paint);

        } catch (Exception e) {
            Log.e(TAG, "sprite.drawText() hiba.");
        }
    }
}

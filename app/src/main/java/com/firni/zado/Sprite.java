package com.firni.zado;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import static android.content.ContentValues.TAG;

class Sprite {
    int interval = 16;
    private Bitmap bitmap = null;
    private Bitmap secBitmap = null;
    int screenWidth, screenHeight;
    int width, height;
    int x, y;
    double vx, vy;
    double additionVy;
    double g;

    Bitmap getBitmap() {
        return bitmap;
    }

    public int getWidth() { return width; }

    public int getHeight(){ return height; }

    boolean setBitmap(Context context, int src) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inSampleSize = 2;
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), src, options);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        } catch (Exception e) {
            bitmap = null;
            return false;
        }
        if(bitmap == null) return false;
        else return true;
    }

    boolean setSecBitmap(Context context, int src) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inSampleSize = 2;
        try {
            secBitmap = BitmapFactory.decodeResource(context.getResources(), src, options);
            secBitmap = Bitmap.createScaledBitmap(secBitmap, width, height, false);
        } catch (Exception e) {
            secBitmap = null;
            return false;
        }
        if(secBitmap == null) return false;
        else return true;
    }

    public void drawBitmap(Canvas canvas, Paint paint, int num) {

        if(num == 0) {
            try {
                canvas.drawBitmap(bitmap, x, y, paint);
            } catch (Exception e) {
                Log.e(TAG, "sprite.drawBitmap() hiba.");
            }
        }
        else if(num == 1) {
            try {
                canvas.drawBitmap(secBitmap, x, y, paint);
            } catch (Exception e) {
                Log.e(TAG, "sprite.drawBitmap() hiba.");
            }
        }
    }
}

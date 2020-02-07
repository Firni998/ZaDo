package com.firni.zado;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Felulet extends View {

    private boolean gameStart;
    private int count;
    private int screenWidth, screenHeight;
    private Paint paint;
    private Jatekos jatekos = null;
    private Platforms platforms = null;
    private Pontszamlalo pontszamlalo = null;

    public Felulet(Context context) { super(context ); }

    public Felulet(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameStart = false;
        count = 0;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
    }

    private void InitBitmap() {
        screenWidth = getWidth();
        screenHeight = getHeight();
        jatekos = new Jatekos(screenWidth, screenHeight, getContext());
        platforms = new Platforms(screenWidth, screenHeight, 20, getContext());
        pontszamlalo = new Pontszamlalo(screenWidth, screenHeight, getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!gameStart) {
            InitBitmap();
            gameStart = true;
        }
        count ++;
        platforms.drawBitmap(canvas, paint);
        platforms.refresh(getContext(), pontszamlalo, jatekos);
        jatekos.drawBitmap(canvas, paint);
        jatekos.refresh(getContext());
        pontszamlalo.drawBitmap(canvas, paint, 0);
        platforms.inform(jatekos.isStill(), jatekos.vy);
        platforms.impactCheck(jatekos, getContext());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!jatekos.isEquipJetPack()) jatekos.wearCloak(getContext());
                break;
        }
        return true;
    }

    public void setDoodleVx(double roll){
        if(jatekos != null) jatekos.setVx(roll);
    }

    public boolean isGameOver() {
        if(jatekos != null)
            return jatekos.isJatekVege();
        else return false;
    }

    public int getScore() {
        if(pontszamlalo != null)
            return pontszamlalo.getScore();
        else return 0;
    }
}

package com.firni.zado;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import static android.content.ContentValues.TAG;

class Platforms  {

    private int size;
    private int num;
    private int score;
    private Platform[] platform;
    private JetPack jetpack;
    private Szornyek szornyek;
    private int screenWidth, screenHeight;
    private int baseMaxInterval;
    private int baseMaxBrotInterval;
    private int head, rear;
    private int numWhitePlat;
    private int maxWhitePlat;

    Platforms(int screenWidth, int screenHeight, int size, Context context) {
        this.size = size;
        this.num = size;
        score = 0;
        platform = new Platform[size];
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        baseMaxInterval = 400;
        baseMaxBrotInterval = 200;
        head = 0;   rear = 0;
        numWhitePlat = 0;
        maxWhitePlat = 10;
        for(int i = 0; i < num; i++) {
            platform[i] = new normalPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.normal, context), score, context);
            rear = i;
        }
        szornyek = null;
        jetpack = null;
    }

    private int randomX() {
        return (int) (Math.random() * (screenWidth - 185));
    }

    private int randomY(int type, Context context) {
        int highestY;
        int deltaY;
        int maxInterval, maxBroInterval;
        if(score < 3000) {
            maxInterval = 100;
            maxBroInterval = 0;
        }
        else if(score < 6000) {
            maxInterval = 200;
            maxBroInterval = 100;
        }
        else if(score < 9000) {
            maxInterval = 300;
            maxBroInterval = 100;
        }
        else if(score < 12000) {
            maxInterval = 350;
            maxBroInterval = 200;
        }
        else {
            maxInterval = baseMaxInterval;
            maxBroInterval = baseMaxBrotInterval;
        }

        if(platform[head] == null) {
            highestY = screenHeight - 55;
            deltaY = (int) (Math.random() * maxInterval + 100);
        }
        else {
            highestY = platform[rear].y;
            if(type == PlatType.broken)
                deltaY = (int) ((Math.random() * maxBroInterval + 100));
            else if(platform[rear].type == PlatType.broken)
                deltaY = (int) ((Math.random() * (maxInterval - maxBroInterval - 100) + 100));
            else deltaY = (int) (Math.random() * maxInterval + 100);
        }
        if(deltaY >= 170 + 100) {

            int rv = (int) (Math.random() * 1000);
            if(rv < 500) {
                if(szornyek == null) {
                    szornyek = new Szornyek(screenWidth, screenHeight, highestY, context);

                }
            }
            else if(platform[rear].type == PlatType.normal && jetpack == null) {
                jetpack = new JetPack(screenWidth, screenHeight, platform[rear], rear, context);

            }
        }
        highestY -= deltaY;
        return highestY;
    }

    public int getSize() { return size; }

    public int getNum() { return num; }

    public Bitmap getBitmap(int i) {

        i = head + i;
        i = i % size;
        return platform[i].getBitmap();
    }

    public int getX(int i) {

        i = head + i;
        i = i % size;
        return platform[i].x;
    }

    public int getY(int i) {

        i = head + i;
        i = i % size;
        return platform[i].y;
    }

    void drawBitmap(Canvas canvas, Paint paint) {
        for(int i = 0, j = head; i < num; i++, j = (j+1) % size)
            if(platform[j].valid) platform[j].drawBitmap(canvas, paint, 0);
        if(szornyek != null) szornyek.drawBitmap(canvas, paint, 0);
        if(jetpack != null) {
            jetpack.drawBitmap(canvas, paint);

        }
    }

    void refresh(Context context, Pontszamlalo pontszamlalo, Jatekos jatekos) {
        boolean flag = false;
        int deltaY = 0;
        int originHead = head;
        for(int i = 0, j = head; i < num; i++, j = (j+1) % size) {
            if(!flag) {
                deltaY = (int) (platform[j].additionVy * platform[j].interval);
                flag = true;
            }
            if (!platform[j].refresh()) {

                if(j == originHead) {
                    if(jetpack != null && j == jetpack.getNumPlat())

                        jetpack.platInvalidate();
                    deleteHead();
                    newRear(context, pontszamlalo);
                }
            }
        }
        if(szornyek != null)
            if(!szornyek.refresh()) szornyek = null;
        if(jetpack != null)
            if(!jetpack.refresh(platform, jatekos)) jetpack = null;
        pontszamlalo.addScore(deltaY);
    }

    private void deleteHead() {
        if(num <= 0) Log.e(TAG, "wrong: probáljon törölni elemet a listából .");
        platform[head] = null;
        head = (head + 1) % size;

        num--;
    }

    private void newRear(Context context, Pontszamlalo pontszamlalo) {
        if(num == size) {
            Log.e(TAG, "wrong: Teljes listához akar elemet adni.");
            return;
        }
        score = pontszamlalo.getScore();
        int temp = (rear + 1) % size;
        int rv = (int)(Math.random() * 1000);
        if(platform[rear] == null)
            platform[temp] = new normalPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.normal, context), score, context);
        else {
            switch (platform[rear].type) {
                case PlatType.broken:
                    if(rv > 200) platform[temp] = new normalPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.normal, context), score, context);
                    else if(rv > 10) platform[temp] = new ugrosPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.withspring, context), score, context);
                    else  {
                        platform[temp] = new felhoPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.onetouch, context), context);
                        numWhitePlat++;
                    }
                    break;
                case PlatType.onetouch:
                    if(numWhitePlat < maxWhitePlat) {
                        platform[temp] = new felhoPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.onetouch, context), context);
                        numWhitePlat++;
                    }
                    else {
                        platform[temp] = new normalPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.normal, context), score, context);
                        numWhitePlat = 0;
                    }
                    break;
                default:
                    if(rv > 300)
                        platform[temp] = new normalPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.normal, context), score, context);
                    else if(rv > 200)
                        platform[temp] = new torottPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.broken, context), score, context);
                    else if(rv > 10)
                        platform[temp] = new ugrosPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.withspring,context), score, context);
                    else {
                        platform[temp] = new felhoPlatform(screenWidth, screenHeight, randomX(), randomY(PlatType.onetouch, context), context);
                        numWhitePlat++;
                    }
            }
        }
        rear = temp;
        num++;
    }

    public void inform(boolean still, double doodleVy) {

        if(still) {

            for (int i = 0, j = head; i < num; i++, j = (j + 1) % size)
                platform[j].additionVy = -doodleVy;
            if(szornyek != null)
                szornyek.additionVy = -doodleVy;
            if(jetpack != null)
                jetpack.additionVy = -doodleVy;
        }
        else {
            for (int i = 0, j = head; i < num; i++, j = (j + 1) % size)
                platform[j].additionVy = 0;
            if(szornyek != null)
                szornyek.additionVy = 0;
            if(jetpack != null)
                jetpack.additionVy = 0;
        }
    }

    public void impactCheck(Jatekos jatekos, Context context) {
        if(jatekos.vy > 0)

            for(int i = 0, j = head; i < num; i++, j = (j+1) % size)
                platform[j].impactCheck(jatekos, context);
        if(szornyek != null) szornyek.impactCheck(jatekos, context);
        if(jetpack != null) jetpack.impactCheck(jatekos, context);
    }
}

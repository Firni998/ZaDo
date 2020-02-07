package com.firni.zado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class JatekVege extends Activity {

    Button sUjraBtn;
    TextView TxtHScore, TxtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jatek_vege);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TxtScore = findViewById(R.id.TxtScore);
        TxtHScore = findViewById(R.id.TxtHScore);

        int score, hScore;
        String strScore, strHScore = "";
        String fileName = "DATA";
        String str = "*";


        score = bundle.getInt("SCORE");
        strScore = Integer.toString(score);
        TxtScore.setText(strScore);

        StringBuffer strBuf = readFile(fileName);
        if(strBuf != null)
            strHScore = getNum(strBuf);
        if(strHScore != null && strHScore.length() != 0) {

            TxtHScore.setText(strHScore);
            hScore = Integer.parseInt(strHScore);
            if(score > hScore) {
                try {

                    FileOutputStream fOut = openFileOutput(fileName, MODE_PRIVATE);
                    BufferedOutputStream bufOut = new BufferedOutputStream(fOut);
                    strScore = str + strScore + str;
                    bufOut.write(strScore.getBytes("UTF-8"));
                    bufOut.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            TxtHScore.setText(strScore);
            try {

                FileOutputStream fOut = openFileOutput(fileName, MODE_PRIVATE);
                BufferedOutputStream bufOut = new BufferedOutputStream(fOut);
                strScore = str + strScore + str;
                bufOut.write(strScore.getBytes("UTF-8"));
                bufOut.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        sUjraBtn = findViewById(R.id.UjraBtn);
        sUjraBtn.setOnClickListener(GOverBtnRtnClick);
    }
    private Button.OnClickListener GOverBtnRtnClick = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {

            Intent it2 = new Intent();
            it2.setClass(JatekVege.this, MenuActivity.class);
            startActivity(it2);
            finish();
        }
    };

    private StringBuffer readFile(String fileName) {

        StringBuffer strBuf = new StringBuffer("");
        byte[] bufBytes = new byte[10];
        try {
            FileInputStream fIn = openFileInput(fileName);
            BufferedInputStream bufIn = new BufferedInputStream(fIn);

            while(true) {
                if(bufIn.read(bufBytes) == -1) break;
                else strBuf.append(new String(bufBytes));
            }
            fIn.close();
            bufIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strBuf;
    }

    private String getNum(StringBuffer strBuf) {

        int startPos = 1;
        int endPos = strBuf.indexOf("*", startPos);
        if(endPos < 0)
            return null;
        char num[] = new char[endPos - startPos];
        strBuf.getChars(startPos, endPos, num, 0);
        return new String(num);
    }

}

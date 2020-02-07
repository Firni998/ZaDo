package com.firni.zado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;


public class MenuActivity extends Activity {

    Button BegBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BegBtnStart = findViewById(R.id.BegBtnStart);
        BegBtnStart.setOnClickListener(BegBtnStartClick);

        try {
            FileOutputStream fOut = openFileOutput("DATA", MODE_APPEND);
            fOut.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private Button.OnClickListener BegBtnStartClick = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent();
            try {
                it.setClass(MenuActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}


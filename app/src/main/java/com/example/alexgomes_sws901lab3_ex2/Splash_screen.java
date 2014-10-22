package com.example.alexgomes_sws901lab3_ex2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Splash_screen extends Activity {

    Button btnSplashNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        btnSplashNext = (Button)findViewById(R.id.btnSplashNext);

        btnSplashNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash_screen.this, Player.class);
                startActivity(intent);
            }
        });

    }


}

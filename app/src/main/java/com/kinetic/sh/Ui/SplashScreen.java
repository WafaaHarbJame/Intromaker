package com.kinetic.sh.Ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        lottieAnimationView=findViewById(R.id.lottie);

        lottieAnimationView.animate().translationY(5).setDuration(5).setStartDelay(5);

        Thread thread=new Thread(){
            public void run(){
                try{
                    sleep(2100);
                }
                catch (Exception e){
                    e.printStackTrace();

                }
                finally {
                    Intent intent=new Intent(com.kinetic.sh.Ui.SplashScreen.this,TemplatePickerActivity.class);
                    startActivity(intent);
                    finish();

                }

            }

        };thread.start();
    }
}
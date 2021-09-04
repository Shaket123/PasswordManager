package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("inside the main");

        preferences =this.getSharedPreferences("first",MODE_PRIVATE);
        editor=preferences.edit();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {



                if(preferences.getBoolean("shaket",false))
                {

//                    BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
//                            .setTitle("please verify")
//                            .setDescription("User authentication is required")
//                            .setNegativeButtonText("cancel")
//                            .build();
//                    getPrompt().authenticate(promptInfo);

                    Intent intent = new Intent(MainActivity.this,biometr.class);
                    startActivity(intent);
                    finish();


            }
                else {
                    editor.putBoolean("shaket",true);
                    editor.apply();
                    TaskStackBuilder.create(MainActivity.this)
                            .addNextIntentWithParentStack(new Intent(MainActivity.this,register.class))
                            .addNextIntent(new Intent(MainActivity.this,onboarding.class))
                            .startActivities();

                }
//                Intent intent = new Intent(MainActivity.this,onboarding.class);
//                startActivity(intent);
//                finish();

                }
        },2000);

    }

//    private BiometricPrompt getPrompt(){
//        Executor executor= ContextCompat.getMainExecutor(this);
//        BiometricPrompt.AuthenticationCallback callback=new BiometricPrompt.AuthenticationCallback() {
//            @Override
//            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                super.onAuthenticationError(errorCode, errString);
//                Toast.makeText(MainActivity.this, errString, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
//                super.onAuthenticationSucceeded(result);
//                Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(MainActivity.this,register.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onAuthenticationFailed() {
//                super.onAuthenticationFailed();
//                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
//            }
//        };
//        BiometricPrompt biometricPrompt =new BiometricPrompt(this,executor,callback);
//        return biometricPrompt;
//    }

}
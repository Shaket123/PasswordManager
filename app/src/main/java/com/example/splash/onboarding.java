package com.example.splash;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.PersistableBundle;

import io.github.dreierf.materialintroscreen.MaterialIntroActivity;
import io.github.dreierf.materialintroscreen.SlideFragment;
import io.github.dreierf.materialintroscreen.SlideFragmentBuilder;

public class onboarding extends MaterialIntroActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
                .title("Save your password all at\none place ")
                .image(R.drawable.bglock)
                .buttonsColor(R.color.b1)
                .backgroundColor(R.color.nak)
        .build());
        addSlide(new SlideFragmentBuilder()
                .neededPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
                .title("Get notified with location every time you open the app")
                .image(R.drawable.mail)
                .buttonsColor(R.color.b1)
                .backgroundColor(R.color.nak)
                .build());
        addSlide(new SlideFragmentBuilder()
                .title("Encryption algorithm protects\nyour data")
                .image(R.drawable.bgshield)
                .buttonsColor(R.color.b1)
                .backgroundColor(R.color.nak)
                .build());
        addSlide(new SlideFragmentBuilder()
                .title("Added security with fingerprint\nauthentication")
                .image(R.drawable.finger)
                .buttonsColor(R.color.b1)
                .backgroundColor(R.color.nak)
                .build());
    }
}

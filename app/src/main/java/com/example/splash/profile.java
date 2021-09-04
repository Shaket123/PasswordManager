package com.example.splash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class profile extends AppCompatActivity {

    Button b1;
    private ImageView im;
    private TextView na, cu, add;
    SharedPreferences prefer,preferences;
    SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        na = findViewById(R.id.name);
        im = findViewById(R.id.pp);
        cu = findViewById(R.id.count);

        final String name = getIntent().getStringExtra("name");
        final String image = getIntent().getStringExtra("image");
        im.setImageURI(Uri.parse(image));
        na.setText(name);
        Cursor cursor = new dbhelper(this).getdata();
        int a = cursor.getCount();
        cu.setText("" + a);
        preferences = this.getSharedPreferences("second", MODE_PRIVATE);






        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(profile.this)
//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.botn);
        bottomNavigationView.setSelectedItemId(R.id.item3);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item1:
                        Intent intent = new Intent(profile.this, mainpage.class);
                        intent.putExtra("name", name);
                        intent.putExtra("image", preferences.getString("image", "null"));
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.item2:
                        //search
                        Intent intent2 = new Intent(profile.this, search.class);
                        intent2.putExtra("name", name);
                        intent2.putExtra("image", preferences.getString("image", "null"));
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.item3:
                        return true;


                    //
                    case R.id.item4:
                        Intent intent4 = new Intent(profile.this, addnewpassword.class);
                        intent4.putExtra("name", name);
                        intent4.putExtra("image", preferences.getString("image", "null"));
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        im.setImageURI(uri);
        prefer = this.getSharedPreferences("second", MODE_PRIVATE);
        edit = prefer.edit();
        edit.putString("image",String.valueOf(uri));
        edit.commit();
    }
}
package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class mainpage extends AppCompatActivity {
    private TextView e1;
    private Button b1;
    private ImageView hb;
    private ArrayList<model> dataholder=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage2);
        e1=findViewById(R.id.userna);
        hb=findViewById(R.id.hp);

//        b1=findViewById(R.id.b1);
        final String name=getIntent().getStringExtra("name");
        final String image=getIntent().getStringExtra("image");


        if(image != null)
        {
            hb.setImageURI(Uri.parse(image));
        }

        e1.setText(name);
        RecyclerView pl=findViewById(R.id.recycler);


        BottomNavigationView bottomNavigationView=findViewById(R.id.botn);
        bottomNavigationView.setSelectedItemId(R.id.item1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.item1:
                        return true;

                    case R.id.item2:
                        //search
                        Intent intent2 = new Intent(mainpage.this,search.class);
                        intent2.putExtra("name",name);
                        intent2.putExtra("image",image);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.item3:
                        Intent intent3 = new Intent(mainpage.this,profile.class);
                        intent3.putExtra("name",name);
                        intent3.putExtra("image",image);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;
                        //
                    case R.id.item4:
                        Intent intent4 = new Intent(mainpage.this,addnewpassword.class);
                        intent4.putExtra("name",name);
                        intent4.putExtra("image",image);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        Cursor cursor=new dbhelper(this).getdata();
        while (cursor.moveToNext()){
            model obj=new model(cursor.getString(0),cursor.getString(1));
            dataholder.add(obj);

        }


        pl.setLayoutManager(new LinearLayoutManager(this));
        pl.setAdapter(new adapter(this,dataholder,name,image));

//       b1.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//               Intent intent = new Intent(mainpage.this,addnewpassword.class);
//               startActivity(intent);
//               finish();
//
//           }
//       });


    }


}
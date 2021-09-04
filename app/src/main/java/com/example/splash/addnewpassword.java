package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class addnewpassword extends AppCompatActivity {
    private EditText e1,e2;
    private Button b1,b2;
    dbhelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewpassword);
        final String name=getIntent().getStringExtra("name");
        final String image=getIntent().getStringExtra("image");
        e1=findViewById(R.id.userna);
        e2=findViewById(R.id.pass);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        db=new dbhelper(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=e1.getText().toString();
                String password=e2.getText().toString();
                if(username.matches("")){
                    Toast.makeText(addnewpassword.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else if(username.matches("")){
                    Toast.makeText(addnewpassword.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else{

                    Boolean checkinsertdata = db.insertdata(username,password);
                    if(checkinsertdata){
                        Toast.makeText(addnewpassword.this, "data inserted", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(addnewpassword.this,mainpage.class);
                        intent2.putExtra("name",name);
                        intent2.putExtra("image",image);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                    }
                    else{
                        Toast.makeText(addnewpassword.this, "data is not inserted", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                e2.setText(String.valueOf(geek_Password(10)));
            }
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.botn);
        bottomNavigationView.setSelectedItemId(R.id.item4);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.item1:
                        Intent intent2 = new Intent(addnewpassword.this,mainpage.class);
                        intent2.putExtra("name",name);
                        intent2.putExtra("image",image);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.item2:
                        Intent intent4 = new Intent(addnewpassword.this,search.class);
                        intent4.putExtra("name",name);
                        intent4.putExtra("image",image);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        //search
                        return true;


                    case R.id.item3:
                        Intent intent3 = new Intent(addnewpassword.this,profile.class);
                        intent3.putExtra("name",name);
                        intent3.putExtra("image",image);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;
                    //
                    case R.id.item4:

                        return true;
                }
                return false;
            }
        });
    }


    public static char[] geek_Password(int len)
    {
        System.out.println("Generating password using random() : ");
        System.out.print("Your new password is : ");

        // A strong password has Cap_chars, Lower_chars,
        // numeric value and symbols. So we are using all of
        // them to generate our password
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";


        String values = Capital_chars + Small_chars +
                numbers + symbols;

        // Using random method
        Random rndm_method = new Random();

        char[] password = new char[len];

        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            password[i] =
                    values.charAt(rndm_method.nextInt(values.length()));

        }
        return password;
    }
}










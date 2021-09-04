package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editpass extends AppCompatActivity {

    private EditText user,pa;
    private Button up,de;
    dbhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpass);

        final String name1=getIntent().getStringExtra("texttitle");
        final String pass=getIntent().getStringExtra("password");
        user=findViewById(R.id.usern);
        pa=findViewById(R.id.passw);
        up=findViewById(R.id.upd);
        de=findViewById(R.id.del);
        user.setText(name1);
        pa.setText(pass);
        final String name=getIntent().getStringExtra("name");
        final String image=getIntent().getStringExtra("image");


        db=new dbhelper(this);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a=user.getText().toString();
                String b=pa.getText().toString();
                System.out.println(a);
                System.out.println(b);
                Boolean chceckin=db.updatedata(a,b);
                if(chceckin==true)
                {
                    Toast.makeText(editpass.this, "data updated", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(editpass.this,mainpage.class);
                    intent2.putExtra("name",name);
                    intent2.putExtra("image",image);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                }

                else
                    Toast.makeText(editpass.this, "data not updated", Toast.LENGTH_SHORT).show();
            }
        });

        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a=user.getText().toString();
                String b=pa.getText().toString();
                System.out.println(a);
                System.out.println(b);
                Boolean chceckin=db.deletedata(a);
                if(chceckin==true)
                {
                    Toast.makeText(editpass.this, "data deleted", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(editpass.this,mainpage.class);
                    intent2.putExtra("name",name);
                    intent2.putExtra("image",image);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                }


                else
                    Toast.makeText(editpass.this, "data not deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
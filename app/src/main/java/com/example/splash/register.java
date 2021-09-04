package com.example.splash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;


public class register extends AppCompatActivity {
    private EditText email, name;
    private Button b1;
    private ImageView pf;
    SharedPreferences prefer;
    SharedPreferences.Editor edit;
//    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        b1 = findViewById(R.id.b1);
        pf=findViewById(R.id.pf);

        prefer = this.getSharedPreferences("second", MODE_PRIVATE);
        edit = prefer.edit();
//        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


        System.out.println("inside the register");
        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(register.this)
//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });




        if (prefer.getBoolean("registered", false)) {

//            System.out.println("inside if");
//            String s1 = prefer.getString("name", "null");
//            String s2 = prefer.getString("email", "null");
//            String s3 = prefer.getString("image", "null");
//
//            String me="Hii\nDear "+s1+" your app was acessed from ";
//
//            showlocation(s2,s1,s3,"App is Acessed",me);

        }
        else{
            System.out.println("inside else");

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit.putBoolean("registered",true);
                    edit.putString("name", name.getText().toString());
                    edit.putString("email", email.getText().toString());
                    edit.apply();
                    if(name.getText().toString().matches("")){
                        Toast.makeText(register.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    }
                    else if(email.getText().toString().matches("")){
                        Toast.makeText(register.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        String me="Hii\nDear "+name.getText()+" we are glag to tell you that you have sucessfully registerd with\n" +
                                "passco now you can manage all your passords at single place with advance security";
                        sendEmail(email.getText().toString(),"Registration at Passco",me);
                        Intent intent = new Intent(register.this, mainpage.class);
                        intent.putExtra("name", name.getText().toString());
                        intent.putExtra("email", email.getText().toString());
                        intent.putExtra("image", prefer.getString("image", "null"));


                        startActivity(intent);
                        finish();

                    }


                }
            });

        }
    }

    private void sendEmail(String em,String su,String me) {
        //Getting content for email
        String email =em;
        String subject = su;
        String message = me;

        //Creating SendMail object
        sendmail sm = new sendmail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

//    private void showlocation(String ema,String s1,String s3,String sub,String msg) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//
//                Location location=task.getResult();
//                if(location!=null){
//                    Geocoder geocoder=new Geocoder(register.this, Locale.getDefault());
//                    try {
//                        List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),
//                                location.getLongitude(),1);
//                        String a=addressList.get(0).getAddressLine(0);
//                        String b=addressList.get(0).getLocality();
//                        String c=addressList.get(0).getCountryName();
//                        String q=a+","+b+","+c;
//                        String modmsg=msg+""+q+".We hope it was you in alternate situation please take valid actions";
//                        sendEmail(ema,sub,modmsg);
//                        System.out.println(addressList.get(0).getLocality());
//                        Intent intent = new Intent(register.this, mainpage.class);
//                        intent.putExtra("name", s1);
//                        intent.putExtra("image", s3);
//
//                        startActivity(intent);
//                        finish();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else
//                    Toast.makeText(register.this, "LOCATION null error", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        pf.setImageURI(uri);
        edit.putString("image",String.valueOf(uri));
        edit.commit();

    }
}
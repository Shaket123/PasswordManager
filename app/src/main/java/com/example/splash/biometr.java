package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class biometr extends AppCompatActivity {

    SharedPreferences prefer;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometr);


        prefer = this.getSharedPreferences("second", MODE_PRIVATE);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        if (prefer.getBoolean("registered", false)){
            System.out.println("inside if");

            BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("please verify")
                    .setDescription("User authentication is required")
                    .setNegativeButtonText("cancel")
                    .build();
            getPrompt().authenticate(promptInfo);
        }
        else{
            Toast.makeText(this, "inside Elseeee", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(biometr.this,register.class);
            startActivity(intent);
            finish();
        }




    }


    private BiometricPrompt getPrompt(){
        Executor executor= ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback=new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                System.out.println(errString);
                Log.d("hiii","hello");
                Toast.makeText(biometr.this, errString, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                String s1 = prefer.getString("name", "null");
                String s2 = prefer.getString("email", "null");
                String s3 = prefer.getString("image", "null");

                String me="Hii\nDear "+s1+" your app was acessed from ";

                showlocation(s2,s1,s3,"App is Acessed",me);
                Intent intent = new Intent(biometr.this,mainpage.class);
                intent.putExtra("image", s3);
                intent.putExtra("name", s1);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(biometr.this, "failed", Toast.LENGTH_SHORT).show();
            }
        };
        BiometricPrompt biometricPrompt =new BiometricPrompt(this,executor,callback);
        return biometricPrompt;
    }

    private void showlocation(String ema,String na,String im,String sub,String msg) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location=task.getResult();
                if(location!=null){
                    Geocoder geocoder=new Geocoder(biometr.this, Locale.getDefault());
                    try {
                        List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(),1);
                        String a=addressList.get(0).getAddressLine(0);
                        String b=addressList.get(0).getLocality();
                        String c=addressList.get(0).getCountryName();
                        String q=a+","+b+","+c;
                        String modmsg=msg+""+q+".We hope it was you in alternate situation please take valid actions";
                        sendEmail(ema,sub,modmsg);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(biometr.this, "LOCATION null error", Toast.LENGTH_SHORT).show();

            }
        });
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
}
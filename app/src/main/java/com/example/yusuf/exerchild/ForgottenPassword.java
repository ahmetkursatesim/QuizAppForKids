package com.example.yusuf.exerchild;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
public class ForgottenPassword extends AppCompatActivity {
    EditText K_adı;
    EditText E_adı;
    Button   Send;
    FirebaseDatabase database;
    DatabaseReference userpass;

    @Override
public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgottenpassword);

        K_adı=findViewById(R.id.k_f_giris);
        E_adı=findViewById(R.id.k_e_giris);
        Send=findViewById(R.id.f_giris);
        database=FirebaseDatabase.getInstance();
        userpass=database.getReference("Users");

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                forgotpassword(K_adı.getText().toString(),E_adı.getText().toString());
            }
        });




    }

    public void forgotpassword(final String kadı, final String email) {
        userpass.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                if(dataSnapshot.hasChild(kadı)){


                    if(dataSnapshot.child(kadı).child("email").getValue().toString().equals(email)){
                             sendTestEmail(email,dataSnapshot.child(kadı).child("password").getValue().toString());

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Email Adresiniz yanlış", Toast.LENGTH_SHORT).show();


                    }

                }
                else{

                    Toast.makeText(getApplicationContext(), "Bu isimde bir kullanıcı bulunamadı.", Toast.LENGTH_SHORT).show();

                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    private void sendTestEmail(final String Email,final String Password){
        BackgroundMail.newBuilder(this)
                .withUsername("exerchild@gmail.com")
                .withPassword("Exerchild12345")
                .withMailto(Email)
                .withSubject("Your Password")
                .withBody("Your Exerchild Password:"+Password)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
    }

    public void onBackPressed(){
        Intent intocan = new Intent(ForgottenPassword.this, Login.class);
        startActivity(intocan);


    }
}

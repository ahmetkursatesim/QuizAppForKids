package com.example.yusuf.exerchild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Star extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        addListenerOnRatingBar();


    }

    public void addListenerOnRatingBar() {

        ratingBar = findViewById(R.id.yildiz_sayi);
        txtRatingValue =findViewById(R.id.yildiz_goster);


    }
}

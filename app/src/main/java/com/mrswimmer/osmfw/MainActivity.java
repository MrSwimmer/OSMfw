package com.mrswimmer.osmfw;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    LinearLayout get, info;
    ImageView im1, im2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint);
        get = findViewById(R.id.menu_get);
        info = findViewById(R.id.menu_info);
        im1 = findViewById(R.id.menu_im1);
        im2 = findViewById(R.id.menu_im2);
        im1.setImageResource(R.drawable.nnote);
        im2.setImageResource(R.drawable.statist);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OSM.class);
                startActivity(i);
            }
        });
    }
}

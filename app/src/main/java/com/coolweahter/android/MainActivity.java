package com.coolweahter.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.coolweahter.android.db.Province;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weather = prefs.getString("weather", null);
        if(weather != null){
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }

//        Button btnCreateDatabase = (Button) findViewById(R.id.test_button);
//        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Province province = new Province();
////                province.setProvinceName("hello");
////                province.setProvinceCode(1233333);
////                province.save();
//                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
//                LitePal.getDatabase();
//            }
//        });


    }
}

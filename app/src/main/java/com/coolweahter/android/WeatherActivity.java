package com.coolweahter.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolweahter.android.gson.Weather;
import com.coolweahter.android.util.HttpUtil;
import com.coolweahter.android.util.Utility;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Button navButton;

    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;

    private ScrollView weatherLayout;
    private ImageView bingpicImg;

    private String MY_PRE_NAME = "preference_weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);

        navButton = (Button) findViewById(R.id.nav_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        bingpicImg = (ImageView) findViewById(R.id.bing_pic_img);

        String weatherId = getIntent().getStringExtra("weather_id");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherStr = prefs.getString("weather", null);
        String bingPic = prefs.getString("bing_pic", null);

        if(bingPic != null){
            Glide.with(this).load(bingPic).into(bingpicImg);
        }else{
            loadBingPic();
        }

        Toast.makeText(WeatherActivity.this, weatherStr, Toast.LENGTH_SHORT).show();
        if(weatherStr != null){
            Weather weather = Utility.handleWeatherResponse(weatherStr);
            showWeatherInfo(weather);
        }else{

            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
            // Toast.makeText(WeatherActivity.this, weatherId, Toast.LENGTH_SHORT).show();
        }


        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }

    public void requestWeather(final String weatherId){
        // String address = "https://mocks.alibaba-inc.com/mock/2oNHyx1Sj/api/weather?cityId" + weatherId + "&key=ac529c422b34424983b2c7764fca7fd5";
        String address = "https://free-api.heweather.net/s6/weather/now?location=" + weatherId + "&key=a72fad4e8c51455eaef450fc1e620cb7";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responstText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responstText);

                if(weather != null && "ok".equals(weather.status)){
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                    editor.putString("weather", responstText);
                    editor.apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeatherInfo(weather);
                        }
                    });
                }else{
                    Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadBingPic();
    }
    public void showWeatherInfo(Weather weather){
        String cityName = weather.basic.cityName;
        String updateTime = weather.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "C";
        String weatherInfo = weather.now.more;

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);

        weatherLayout.setVisibility(View.VISIBLE);
    }
    public void loadBingPic(){
        String address = "http://guolin.tech/api/bing_pic";

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String bingPic = response.body().string();

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingpicImg);
                    }
                });
            }
        });
    }


}

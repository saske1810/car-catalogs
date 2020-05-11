package com.example.diplom1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemsPreviewActivity extends AppCompatActivity {

    private ImageView imageView;

    private TextView carName;

    private EditText batteryDensity;

    private EditText sparkPlugResistance;

    private EditText powerSteeringFluid;

    private EditText ignitionTiming;

    private EditText brakeFluid;

    private EditText knockSensor;

    private EditText coolantFluid;

    private EditText speakerImpedance;

    private CarSpec carSpec;

    private RequestQueue queue;

    private void loadCar (int car) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, HttpApiUtil.URL_API+"/car/" + car, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            carSpec = new CarSpec(
                                    response.getString("name"),
                                    response.getString("batteryDensity"),
                                    response.getDouble("sparkPlugResistance"),
                                    response.getString("powerSteeringFluid"),
                                    response.getString("ignitionTiming"),
                                    response.getString("brakeFluid"),
                                    response.getDouble("knockSensor"),
                                    response.getString("coolantFluid"),
                                    response.getDouble("speakerImpedance"),
                                    response.getString("image")
                            );

                            carName.setText(carSpec.getName());
                            batteryDensity.setText(carSpec.getBatteryDensity());
                            sparkPlugResistance.setText(carSpec.getSparkPlugResistance()+"");
                            powerSteeringFluid.setText(carSpec.getPowerSteeringFluid());
                            ignitionTiming.setText(carSpec.getIgnitionTiming());
                            brakeFluid.setText(carSpec.getBrakeFluid());
                            knockSensor.setText(carSpec.getKnockSensor()+ "");
                            coolantFluid.setText(carSpec.getCoolantFluid());
                            speakerImpedance.setText(carSpec.getSpeakerImpedance() + "");


                            URL url = null;
                            try {
                                url = new URL(HttpApiUtil.URL_IMAGE_STORAGE + "/car/" + carSpec.getImage());
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            try {
                                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                imageView.setImageBitmap(bmp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_preview);


        queue               = Volley.newRequestQueue(this);
        imageView           = findViewById(R.id.imageView);
        carName             = findViewById(R.id.textView);
        batteryDensity      = findViewById(R.id.editText);
        sparkPlugResistance = findViewById(R.id.editText2);
        powerSteeringFluid  = findViewById(R.id.editText3);
        ignitionTiming      = findViewById(R.id.editText4);
        brakeFluid          = findViewById(R.id.editText6);
        knockSensor         = findViewById(R.id.editText7);
        coolantFluid        = findViewById(R.id.editText8);
        speakerImpedance    = findViewById(R.id.editText1);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Car car = (Car) intent.getSerializableExtra("car");
            this.loadCar(car.getId());
        }
    }
}
package mealme.mariahwang.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MealCompare extends AppCompatActivity {

    ImageView myImage;
    ImageView compareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_compare);

        File imgFilePath = (File) getIntent().getSerializableExtra("file_key");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath.getAbsolutePath());
        myImage = findViewById(R.id.myMealID);
        myImage.setImageBitmap(myBitmap);

        compareImage = findViewById(R.id.compareMealID);
        String category = (String) getIntent().getSerializableExtra("category");

        if (category.equals("Fish")) {
            compareImage.setImageResource(R.drawable.sushi);
        }
        if (category.equals("MeatVeg")) {
            compareImage.setImageResource(R.drawable.burger);
        }
        if (category.equals("Meat")) {
            compareImage.setImageResource(R.drawable.steak);
        }
        if (category.equals("VegGrain")) {
            compareImage.setImageResource(R.drawable.veggies);
        }
        if (category.equals("Beverage")) {
            compareImage.setImageResource(R.drawable.beverage);
        }


        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myImage.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startMain();
                    }
                }, 2000);
            }
        });

        compareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compareImage.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startMain();
                    }
                }, 2000);
            }
        });
    }

    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
package mealme.mariahwang.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MealCategorize extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_categorize);

        final File imgFilePath = (File ) getIntent().getSerializableExtra("file_key");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath.getAbsolutePath());
        ImageView myImage = (ImageView) findViewById(R.id.imageViewID);
        myImage.setImageBitmap(myBitmap);


        Button fishButton = (Button) findViewById(R.id.fishvegID);
        fishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"Fish");
            }
        });

        Button meatvegButton = (Button) findViewById(R.id.meatvegID);
        meatvegButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"MeatVeg");
            }
        });

        Button meatButton = (Button) findViewById(R.id.meatID);
        meatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"Meat");
            }
        });

        Button veggrainButton = (Button) findViewById(R.id.veggrainID);
        veggrainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"VegGrain");
            }
        });

        Button beverageButton = (Button) findViewById(R.id.beverageID);
        beverageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"Beverage");
            }
        });
    }



    private void startCompareActivity(File imgFile, String category) {
        Intent intent = new Intent(this, MealCompare.class);
        intent.putExtra("file_key", imgFile);
        intent.putExtra("category", category);

        startActivity(intent);
    }
}

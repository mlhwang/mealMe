package mealme.mariahwang.com.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class MealCompare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_compare);

        File imgFilePath = (File ) getIntent().getSerializableExtra("file_key");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath.getAbsolutePath());
        ImageView myImage = (ImageView) findViewById(R.id.myMealID);
        myImage.setImageBitmap(myBitmap);

        ImageView compareImage = (ImageView) findViewById(R.id.compareMealID);
        String category = (String) getIntent().getSerializableExtra("category");

        if (category.equals("Fish")){compareImage.setImageResource(R.drawable.sushi);}
        if (category.equals("MeatVeg")){compareImage.setImageResource(R.drawable.burger);}
        if (category.equals("Meat")){compareImage.setImageResource(R.drawable.steak);}
        if (category.equals("VegGrain")){compareImage.setImageResource(R.drawable.veggies);}
        if (category.equals("Beverage")){compareImage.setImageResource(R.drawable.beverage);}

    }
}
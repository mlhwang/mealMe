package mealme.mariahwang.com.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class MealCategorize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_categorize);

        File imgFilePath = (File ) getIntent().getSerializableExtra("file_key");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath.getAbsolutePath());
        ImageView myImage = (ImageView) findViewById(R.id.imageViewID);
        myImage.setImageBitmap(myBitmap);
    }
}

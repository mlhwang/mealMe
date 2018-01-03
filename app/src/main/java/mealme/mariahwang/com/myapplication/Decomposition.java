package mealme.mariahwang.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class Decomposition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decomposition);

        final File imgFilePath = (File) getIntent().getSerializableExtra("file_key");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath.getAbsolutePath());
        ImageView myImage = findViewById(R.id.imageDecompView);
        myImage.setImageBitmap(myBitmap);

        findViewById(R.id.decompDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText d = findViewById(R.id.userDecomps);
                //TODO use a better interface than edittext to ensure it is easy to parse each component
                String [] components = d.getText().toString().split(", ");
                startNextStep(imgFilePath,components);
            }
        });
    }

    private void startNextStep(File imgFile, String[] components) {
        Intent intent = new Intent(this, PortionEstimation.class);
        intent.putExtra("file_key", imgFile);
        intent.putExtra("components", components);
        startActivity(intent);
    }
}

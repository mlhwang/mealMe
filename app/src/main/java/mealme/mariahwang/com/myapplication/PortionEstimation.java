package mealme.mariahwang.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

public class PortionEstimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portion_estimation);

        final String[] components = (String []) getIntent().getSerializableExtra("components");

        final File imgFilePath = (File ) getIntent().getSerializableExtra("file_key");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath.getAbsolutePath());
        ImageView myImage = findViewById(R.id.imagePortionView);
        myImage.setImageBitmap(myBitmap);

        LinearLayout linearLayout = findViewById(R.id.portionSelection);
        for( int i = 0; i < components.length; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(components[i]);
            linearLayout.addView(textView);
            //TODO replace with drag and drop portion size images
            SeekBar seek = new SeekBar(this);
            seek.setMax(50);
            linearLayout.addView(seek);

        }

        findViewById(R.id.portionDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextStep();
            }
        });
    }
    private void startNextStep() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

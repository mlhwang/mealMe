package mealme.mariahwang.com.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meals")
                .whereEqualTo("ownerUid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e("Error", task.getResult().toString());
                            for (DocumentSnapshot document : task.getResult()) {
                                // TODO - it might be better to use a ListView here
                                // listView has better memory usage properties
                                addMealToViewList((LinearLayout) findViewById(R.id.mealList), document.toObject(Meal.class));
                            }
                        }
                    }
                });

    }

    private void addMealToViewList(LinearLayout mealList, Meal meal) {
        TextView textView = new TextView(this);
        textView.setText(meal.getCategory());
        mealList.addView(textView);
        ImageView mealImage = new ImageView(this);
        mealList.addView(mealImage);
        Log.e("Error", meal.toString());

        // show The Image in a ImageView
        new DownloadImageTask(mealImage)
                .execute(meal.getImageUrl());

    }

    //taken from https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap imageBitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                //scale the images down in size so we dont run out of memory
                final int THUMBNAIL_SIZE = 512;
                imageBitmap = BitmapFactory.decodeStream(in);
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return imageBitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


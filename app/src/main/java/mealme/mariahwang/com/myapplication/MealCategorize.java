package mealme.mariahwang.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MealCategorize extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_categorize);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        final File imgFilePath = (File ) getIntent().getSerializableExtra("file_key");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath.getAbsolutePath());
        ImageView myImage = findViewById(R.id.imageViewID);
        myImage.setImageBitmap(myBitmap);


        Button fishButton = findViewById(R.id.fishvegID);
        fishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"Fish");
            }
        });

        Button meatvegButton = findViewById(R.id.meatvegID);
        meatvegButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"MeatVeg");
            }
        });

        Button meatButton = findViewById(R.id.meatID);
        meatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"Meat");
            }
        });

        Button veggrainButton = findViewById(R.id.veggrainID);
        veggrainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startCompareActivity(imgFilePath,"VegGrain");
            }
        });

        Button beverageButton = findViewById(R.id.beverageID);
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
        uploadImage(mStorageRef, imgFile, category);

        startActivity(intent);
    }

    private void uploadImage(StorageReference storageRef, File imgFile, final String category) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Uri file = Uri.fromFile(imgFile);
        final String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        StorageReference newFoodPic =
                storageRef.child("images/" + category + "/" + user.getUid() + "_" + timeStamp + ".jpg");

        newFoodPic.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        addMealtoFirestore(downloadUrl, user, category, timeStamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("g", exception.toString());
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.d("MealMe", "Upload is " + progress + "% done");
                    }
                });

    }

    private void addMealtoFirestore(Uri downloadUrl, FirebaseUser user, String category, String timeStamp) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.e("g", downloadUrl.toString());

        Map<String, Object> newMeal = new HashMap<>();
        newMeal.put("ownerUid", user.getUid());
        newMeal.put("imageUrl", downloadUrl.toString());
        newMeal.put("category", category);
        //The document id should always be unique
        db.collection("meals").document(user.getUid() + timeStamp)
                .set(newMeal);
    }
}

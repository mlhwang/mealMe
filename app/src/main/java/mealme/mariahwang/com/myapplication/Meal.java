package mealme.mariahwang.com.myapplication;

/**
 * Created by mhwang502 on 1/5/18.
 */


/*
 For a class to work with Firestore, it must...
  "Each custom class must have a public constructor that takes no arguments.
  In addition, the class must include a public getter for each property."
  more info: https://firebase.google.com/docs/firestore/query-data/get-data#custom_objects
*/

class Meal {
    private String ownerUid;
    private String imageUrl;
    private String category;

    public Meal() {

    }

    public Meal(String ownerUid, String imageUrl, String category) {

    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }
}

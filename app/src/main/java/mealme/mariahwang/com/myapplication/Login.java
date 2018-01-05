package mealme.mariahwang.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // NB - the authentication database and firestore database are seperate
                createUserInFirestore(user);


                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                // TODO - Sign in failed, check response for error code
            }
        }
    }

    private void createUserInFirestore(FirebaseUser user) {
        //TODO - this tries to create a new user every time you login
        //the SetOptions.merge means we do not overwrite existing users
        //but this still seems very wasteful
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        int monsterType = user.getUid().hashCode() % 6;
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("monsterType", monsterType);
        newUser.put("carbLevel", 0);
        newUser.put("fatLevel", 0);
        db.collection("users").document(user.getUid())
                .set(newUser, SetOptions.merge());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }
}

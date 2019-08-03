package com.crucial.travelmantic;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;
    private TextView mTxtDealsHeader;
    private RecyclerView mRecycDeals;
    private FirebaseAuth mAuth;
    List<AuthUI.IdpConfig> providers;
    public static boolean isAdmin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mTxtDealsHeader = findViewById(R.id.txt_deals_header);
        mRecycDeals = findViewById(R.id.recyc_deals);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Travel Deals");
        mAuth = FirebaseAuth.getInstance();
        
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
                
        );
        
        showSignInOptions();
                


    }

    private void showSignInOptions() {
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                //.setTheme(R.style.AppTheme_MyTheme)
                .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Travel Deals");
        TravelDealAdapter adapter = new TravelDealAdapter();
        mRecycDeals.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecycDeals.setLayoutManager(layoutManager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_activity_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_save_deal);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_new_deal:
                Intent intent = new Intent(this,AdminActivity.class);
                startActivity(intent);
            case R.id.logout:
                AuthUI.getInstance().signOut(UserActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showSignInOptions();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                Toast.makeText(this, "Welcome back " +user.getDisplayName(), Toast.LENGTH_SHORT).show();
            }
        }
    }


}

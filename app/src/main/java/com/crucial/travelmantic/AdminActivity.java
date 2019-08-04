package com.crucial.travelmantic;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    private TextView mTxtViewTitle;
    private EditText mEdtDealName;
    private TextView mTxtDealName;
    private CardView mCardViewDealName;
    private EditText mEdtDealPrice;
    private TextView mTxtDealPrice;
    private CardView mCardViewDealPrice;
    private EditText mEdtDealDescription;
    private TextView mTxtDealDescription;
    private CardView mCardViewDealDescription;
    private Button mBtnSelectImage;
    private ImageView mImgDealImage;
    public static boolean isAdmin;



    TravelDeal deal;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mMyRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");

        if(deal == null){
            deal = new TravelDeal();
        }

        this.deal = deal;


        mTxtViewTitle = findViewById(R.id.txt_view_title);
        mEdtDealName = findViewById(R.id.edt_deal_name);
        mTxtDealName = findViewById(R.id.txt_deal_name);
        mCardViewDealName = findViewById(R.id.card_view_deal_name);
        mEdtDealPrice = findViewById(R.id.edt_deal_price);
        mTxtDealPrice = findViewById(R.id.txt_deal_price);
        mCardViewDealPrice = findViewById(R.id.card_view_deal_price);
        mEdtDealDescription = findViewById(R.id.edt_deal_description);
        mTxtDealDescription = findViewById(R.id.txt_deal_description);
        mCardViewDealDescription = findViewById(R.id.card_view_deal_description);
        mBtnSelectImage = findViewById(R.id.btn_select_image);
        mImgDealImage = findViewById(R.id.img_deal_image);

        deal = new TravelDeal();


        mBtnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Insert Picture"),6425);
            }
        });

        



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.admin_activity_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_save_deal);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save_deal:
                saveDeal();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDeal() {
        deal.setTitle(mEdtDealName.getText().toString());
        deal.setDescription(mEdtDealDescription.getText().toString());
        deal.setPrice(mEdtDealPrice.getText().toString());
        clearEntries();

        mDatabase = FirebaseDatabase.getInstance();
        mMyRef = mDatabase.getReference().child("Travel Deals");
        mMyRef.push().setValue(deal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AdminActivity.this, "Travel Deal Successfully Saved",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        Intent intent = new Intent(this,UserActivity.class);
        startActivity(intent);


    }

    private void clearEntries() {
        mEdtDealName.setText("");
        mEdtDealDescription.setText("");
        mEdtDealPrice.setText("");
        mEdtDealName.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        FirebaseStorage mStorage;
        StorageReference mStorageRef;

        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference().child("Deal Pics");

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 6425 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            assert imageUri != null;
            final StorageReference ref = mStorageRef.child(Objects.requireNonNull(imageUri.getLastPathSegment()));
            ref.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return ref.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        String uri = Objects.requireNonNull(task.getResult()).toString();
                        String finalUri = uri.replace("\"","");
                        String pictureName = ref.getName();
                        deal.setImageName(pictureName);
                        deal.setImageUrl(finalUri);
                        showImage(uri);
                    } else {
                        Toast.makeText(AdminActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showImage(String url) {
        if(url != null && !url.isEmpty()) {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get().load(Uri.parse(url)).resize(width,width * 2/3).centerCrop().into(mImgDealImage);
        }
    }
}

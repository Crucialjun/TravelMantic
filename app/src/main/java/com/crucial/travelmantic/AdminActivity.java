package com.crucial.travelmantic;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    TravelDeal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


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

        mFirebaseDatabase = FirebaseUtility.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtility.mDatabaseReference;





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.admin_activity_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_save_deal);

        return super.onCreateOptionsMenu(menu);
    }
}

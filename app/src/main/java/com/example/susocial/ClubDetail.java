package com.example.susocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.susocial.Club.ClubModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class ClubDetail extends AppCompatActivity implements View.OnClickListener {
    private Button clubRate;
    private Button clubChat;
    private DocumentReference clubRef;
    private DocumentReference collectRef;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private List<ClubModel> clublist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);

        getSupportActionBar().setTitle("Club Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clubRate = findViewById(R.id.navg_rate);
        clubRate.setOnClickListener(this);

        clubChat = findViewById(R.id.navg_chat);
        clubChat.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String clubName = intent.getStringExtra("clubName");

        TextView nameTextView = findViewById(R.id.cdetail_name);
        TextView descripTextView = findViewById(R.id.cdetail_descrip);
        TextView rateTextView = findViewById(R.id.cdetail_rate);
        TextView contactTextView = findViewById(R.id.cdetail_contact);
        ImageView clubimageView = findViewById(R.id.cdetail_image);
        TextView leaderTextView = findViewById(R.id.cdetail_leader);
        // clubimageView.setImageResource(R.drawable.ic_profile);

        DocumentReference clubsList = db.collection("Clubs").document(clubName);
        clubsList.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot Data: " + document.getData());
                        String cname = document.getString("Name");
                        String cdescription = document.getString("Description");
                        String ccontact = document.getString("ContactInfo");
                        String crate = "N/A";
                        String cleader = document.getString("President");
                        int cimage = R.drawable.ic_profile;

                        nameTextView.setText(cname);
                        descripTextView.setText(cdescription);
                        rateTextView.setText(crate);
                        contactTextView.setText(ccontact);
                        clubimageView.setImageResource(cimage);
                        leaderTextView.setText(cleader);
                    }
                    else {
                        Log.d("TAG", "No Such Document");
                        Log.d("TAG", clubName);
                    }
                }
                else {
                    Log.d("TAG", "Get Failed with, " + task.getException());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navg_rate:
                startActivity(new Intent(ClubDetail.this, Review.class));
                break;
            case R.id.navg_chat:
                startActivity(new Intent(ClubDetail.this, Message.class));

        }
    }
}
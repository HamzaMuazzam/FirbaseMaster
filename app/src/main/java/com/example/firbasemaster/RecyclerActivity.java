package com.example.firbasemaster;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecyclerActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        String key = getIntent().getStringExtra("KEY");
        mDatabase = FirebaseDatabase.getInstance();
        // u can give any node reference of database here
        mRef = mDatabase.getReference("users").child(key);





        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                PojoClasss value = dataSnapshot.getValue(PojoClasss.class);

//                String name = value.getName();
//
//                Toast.makeText(RecyclerActivity.this, name, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toast.makeText(this,"Key\n"+key,Toast.LENGTH_SHORT).show();
    }
}

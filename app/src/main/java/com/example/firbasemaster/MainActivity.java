package com.example.firbasemaster;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MYTAG";
    private TextView tv_test;
    private List<PojoClasss> list = new ArrayList<>();

    private static final int STORAGE_PERMISSION_CODE = 100;
    private boolean mGranted;
    private EditText input, age;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private ValueEventListener valueEventListener, sQLvalueEventListener;
    private RecyclerView rv_rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();
        getpermission();
        final MyAdapter adapter = new MyAdapter(MainActivity.this, list);
        rv_rest.setAdapter(adapter);
        rv_rest.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        rv_rest.setLayoutManager(new GridLayoutManager(MainActivity.this, 3, RecyclerView.HORIZONTAL, false));

        sQLvalueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PojoClasss value = snapshot.getValue(PojoClasss.class);
                    value.setKey(snapshot.getKey());
                    list.add(value);

                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

//        getdata();
//        valueEventListener_for_once();
    }

    public void readDataBySQLQuery(View view) {

        /**
         * select*from tablename;
         * **/
//        mRef.addValueEventListener(sQLvalueEventListener);
        /**
         * select*from tablename where name="Ali";
         * **/
//        Query query = mRef.orderByChild("name").equalTo("Ali");
//        query.addValueEventListener(sQLvalueEventListener);
        /**
         * select*from tablename order by name;
         * **/
//        Query query2 = mRef.orderByChild("name");
//        query2.addValueEventListener(sQLvalueEventListener);


/**
 * select*from tablename where age<30;
 * **/
//        Query query2 = mRef.orderByChild("age").startAt("30");
//        query2.addValueEventListener(sQLvalueEventListener);


/**
 * select*from tablename where age<30;
 * **/
//        Query query2 = mRef.orderByChild("age").endAt("30");
//        query2.addValueEventListener(sQLvalueEventListener);


/**
 * select*from tablename where limit by 3 (for first elemets);
 * **/
//        Query query2 = mRef.limitToFirst(3);
//        query2.addValueEventListener(sQLvalueEventListener);


/**
 * select*from tablename where limit by 3(for last elemets);
 * **/
        Query query2 = mRef.limitToLast(3);
        query2.addValueEventListener(sQLvalueEventListener);

    }

    private void valueEventListener_for_once() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                String name = data.get("Name").toString();
                Log.d(TAG, "onDataChange: " + data.get("Name"));
                Log.d(TAG, "onDataChange: " + data.get("Age"));
                Log.d(TAG, "onDataChange: " + data);
//                String name = data.get("Name").toString();
//                data.get("Age");
                tv_test.setText(name);


//                String read_value = dataSnapshot.getValue().toString();
//                Toast.makeText(MainActivity.this, read_value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, " " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        mRef.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mRef.child("user one").removeEventListener(valueEventListener);
    }

    private void initviews() {
        rv_rest = findViewById(R.id.rv_rest);
        tv_test = findViewById(R.id.tv_test);
        input = findViewById(R.id.et_input);
        age = findViewById(R.id.et_input_age);
        mDatabase = FirebaseDatabase.getInstance();
        // u can give any node reference of database here
        mRef = mDatabase.getReference("users");

    }

    public void runcode(View view) {
        String name = input.getText().toString().trim();
        String str_age = age.getText().toString().trim();
        if (name.isEmpty() && str_age.isEmpty()) {
            Toast.makeText(this, "Field is Empty", Toast.LENGTH_SHORT).show();
        } else {
            String key = mRef.push().getKey();
            Map<String, Object> insertValue = new HashMap<>();
            insertValue.put("Name", name);
            insertValue.put("Age", str_age);
            mRef.child(key).setValue(insertValue);
            Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();


//working with push id
//mRef.child("/Employes/Name").setValue("Guryaa");
            /**      String key = mRef.push().getKey();
             mRef.child("Mojam").child("Name").setValue(text);
             mRef.child("Mojam").child("Age").setValue("24");
             mRef.child("Mojam").child("Phone Number").setValue("09007860one");
             **/
//        create child and set  value here
            /**
             * mRef.child("Name").setValue("Hamza");
             mRef.child("Age").setValue(90);
             //            mRef.child("user one").setValue(text).addOnSuccessListener(new OnSuccessListener<Void>() {
             //                @Override
             //                public void onSuccess(Void aVoid) {
             //                    Toast.makeText(MainActivity.this, " Inserted" , Toast.LENGTH_SHORT).show();
             //
             //                }
             //            }).addOnFailureListener(new OnFailureListener() {
             //                @Override
             //                public void onFailure(@NonNull Exception e) {
             //                    Toast.makeText(MainActivity.this, " OnFailure " + e.getMessage(), Toast.LENGTH_SHORT).show();
             //                }
             //            });
             //            input.setText("");
             **/
        }
    }

    public void ReadData(View view) {
        //if u dnt pass child it will  fetch the given above whole node
        //if we add addValueEventListener for single valuee eveent then it will run once auto/
        //agrr hum is button ko br br click kryn gyn to her br ik new intance create ho ga is se bacnhy k liye
        //oncreat mn attach kro aik br then on destroy mn remove kro addValueEventListener.
        Toast.makeText(this, "Read the comments i have typed", Toast.LENGTH_LONG).show();
/*
        mRef.child("user one").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String read_value = dataSnapshot.getValue().toString();
                tv_test.setText(read_value);
//                Toast.makeText(MainActivity.this, read_value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(MainActivity.this," "+databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
*/
    }

    public void updateData(View view) {

        /**
         * dont use Desi Tareeka
         * best professional tareeka is   Map<String, Object> updatedValues = new HashMap<>();
         * to update firebase keys in the form of object
         * **/


        Map<String, Object> updatedValues = new HashMap<>();
        updatedValues.put("/Employes/Name", "Guryaa g");
        updatedValues.put("/Employes/Age", "Guryaa g");
        updatedValues.put("/Employes/Phone", "Guryaa g");


        updatedValues.put("/Mojam/Name", "Guryaa g");
        updatedValues.put("/Mojam/Age", "Guryaa g");
        updatedValues.put("/Mojam/Phone Number", "Guryaa g");

        updatedValues.put("/-M1U2O0VIM2zhfVeTEzM/Name", "Guryaa g");
        updatedValues.put("/-M1U2O0VIM2zhfVeTEzM/Age", "Guryaa g");
        updatedValues.put("/-M1U2O0VIM2zhfVeTEzM/Phone Number", "Guryaa g");


        Task<Void> task = mRef.updateChildren(updatedValues);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, " Updated", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, " OnFailure " + e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void deleteData(View view) {
        Task<Void> task;

        task = mRef.child("Employes").removeValue();

        task = mRef.child("Mojam").removeValue();


        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_LONG).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "OnFailure : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void ReadListData(View view) {
        //there are two mathods
        /**mRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
        Map<String,Object> objectMap= (Map<String, Object>) snapshot.getValue();
        Log.d(TAG, "onDataChange +Key : "+snapshot.getKey());
        Log.d(TAG, "onDataChange: "+ objectMap.get("Name"));

        }
        }

        @Override public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        });**/


        /**
         isko aik barr oncreate men call krdo is se hogia kia k ye aik dfa sara data read kr ly ga or then her dfa new data add hony ya delete hny ya
         update hny py execute hoga .. so kisi button k liye ye code mat krna hamza kun k ye her dfa button pressed hony py
         br br new instance create kry ga . jiss se hogia k ye duss intance create krny py duss dfa data read kry ga **/

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Map<String, Object> objectMap = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "onChildAdded: " + objectMap.get("Name"));
                Log.d(TAG, "onChildAdded: " + objectMap.get("Age"));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Map<String, Object> objectMap = (Map<String, Object>) dataSnapshot.getValue();
//                Log.d(TAG, "onChildChanged: " +objectMap.get("Name"));
                Log.d(TAG, "onChildChanged: ");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void readPojoClass_from_Firebase(View view) {

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PojoClasss pojoClasss = dataSnapshot.getValue(PojoClasss.class);

                Log.d(TAG, "onChildAdded: Name : " + pojoClasss.getName());
                Log.d(TAG, "onChildAdded: Age: " + pojoClasss.getAge());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void insertPojoClass_to_Firebase(View view) {
        String childkey = mRef.push().getKey();

        PojoClasss pojoClasss = new PojoClasss("Hamza ", "24", " 24 may", "Matric", "Lahore", childkey);
        String key = mRef.push().getKey();
        mRef.child(key).setValue(pojoClasss);
        Toast.makeText(this, "Pojo Inserted", Toast.LENGTH_SHORT).show();

    }

    public void getdata() {

        final MyAdapter adapter = new MyAdapter(MainActivity.this, list);
        rv_rest.setAdapter(adapter);
        rv_rest.setLayoutManager(new GridLayoutManager(MainActivity.this, 3, RecyclerView.HORIZONTAL, false));
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PojoClasss pojoClasss = dataSnapshot.getValue(PojoClasss.class);
                pojoClasss.setKey(dataSnapshot.getKey());

                Log.d(TAG, "onChildAdded: " + pojoClasss.getName());
                list.add(pojoClasss);

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                PojoClasss value = dataSnapshot.getValue(PojoClasss.class);
                value.setKey(dataSnapshot.getKey());
                list.remove(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getpermission() {
        String externalReadPermission = Manifest.permission.READ_EXTERNAL_STORAGE.toString();
        String externalWritePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE.toString();

        if (ContextCompat.checkSelfPermission(this, externalReadPermission) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, externalWritePermission) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{externalReadPermission, externalWritePermission}, STORAGE_PERMISSION_CODE);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                mGranted = true;
            } else {
                Toast.makeText(this, "Please allow the permission to read data", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void GotofirebaseStorage(View view) {
        Intent intent = new Intent(this, FBSorageActivity.class);
        startActivity(intent);
    }
}

package com.example.firbasemaster;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class MyUtils {

    public static Task<Void> removeValue(String uid_or_key) {
        Task<Void> task = FirebaseDatabase.getInstance().getReference("users").child(uid_or_key).setValue(null);

        return task;
    }
}

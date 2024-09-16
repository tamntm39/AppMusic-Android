package com.example.musicapp.Service;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FirebaseService {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public interface FirebaseCallback<T> {
        void onSuccess(ArrayList<T> result);

        void onError(DatabaseError databaseError);
    }

    public static <T> void fetchDataAndMap(String path, Class<T> modelClass, FirebaseCallback<T> callback) {
        DatabaseReference ref = database.getReference(path);
        ref.get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot != null && dataSnapshot.exists()) {
                                try {
                                    ArrayList<T> resultList = new ArrayList<>();
                                    for (DataSnapshot modelSnapshot : dataSnapshot.getChildren()) {
                                        T model = modelSnapshot.getValue(modelClass);
                                        resultList.add(model);
                                    }
                                    callback.onSuccess(resultList);
                                } catch (Exception e) {
                                    Log.e("TAG", "Error message");

                                }
                            }
                        }
                    }
                }
        );
    }

    public static <T> void fetchDataAndMapWithCondition(String path, String field, String valueField, Class<T> modelClass, FirebaseCallback<T> callback) {
        DatabaseReference ref = database.getReference(path);
        Query query = ref.orderByChild(field).equalTo(valueField);
        final CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Handle the data result
                ArrayList<T> resultList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    T model = snapshot.getValue(modelClass);
                    resultList.add(model);

                }
                latch.countDown();
                callback.onSuccess(resultList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error querying data: " + databaseError.getMessage());
                latch.countDown();
            }
        });
    }
    public static <T> void fetchDataAndMapWithConditionLike(String path, String field, String valueField, Class<T> modelClass, FirebaseCallback<T> callback) {
        DatabaseReference ref = database.getReference(path);
        Query query = ref.orderByChild(field).startAt(valueField).endAt(valueField + "\uf8ff");
        final CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Handle the data result
                ArrayList<T> resultList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    T model = snapshot.getValue(modelClass);
                    resultList.add(model);

                }
                latch.countDown();
                callback.onSuccess(resultList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error querying data: " + databaseError.getMessage());
                latch.countDown();
            }
        });
    }
}

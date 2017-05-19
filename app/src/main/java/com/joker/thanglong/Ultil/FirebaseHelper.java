package com.joker.thanglong.Ultil;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import Entity.EntityUserProfile;

/**
 * Created by joker on 4/17/17.
 */

public class FirebaseHelper {
    private static FirebaseHelper firebaseHelper = null;
    DatabaseReference databaseReference;
    private static EntityUserProfile entityUserProfile;
    private static Context context;
    private FirebaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseHelper getFirebaseHelper(Context context) {
        if (entityUserProfile == null){
           entityUserProfile = ProfileInstance.getProfileInstance(context).getProfile();
        }
        if(firebaseHelper == null)
        {
            firebaseHelper = new FirebaseHelper();
        }
        return firebaseHelper;
    }


    public void setOnline(int code){
        HashMap<String,Integer> status = new HashMap<>();
        status.put("code",code);
        databaseReference.child("user").child(entityUserProfile.getuID()).child("status").setValue(status);
    }

    public void checkOnline(int uId,final callbackStatus callback){
        databaseReference.child("user").child(uId+"").child("status").child("code").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onSuccess(Integer.parseInt(dataSnapshot.getValue().toString()));
                Log.d("Status",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public interface callbackStatus {
        void onSuccess(Integer status);
    }
}

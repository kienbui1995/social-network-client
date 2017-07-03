package com.joker.thanglong.Ultil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import Entity.EntityUserProfile;

/**
 * Created by joker on 4/17/17.
 */

public class FirebaseHelper {
    private  FirebaseHelper firebaseHelper = null;
    DatabaseReference databaseReference;
    private  EntityUserProfile entityUserProfile;
    private Context context;
    StorageReference mStorageReference;
    public FirebaseHelper(Context context) {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        entityUserProfile = ProfileInstance.getProfileInstance(context).getProfile();
    }

//    public static FirebaseHelper getFirebaseHelper(Context context) {
//        if (entityUserProfile == null){
//           entityUserProfile = ProfileInstance.getProfileInstance(context).getProfile();
//        }
//        if(firebaseHelper == null)
//        {
//            firebaseHelper = new FirebaseHelper();
//        }
//        return firebaseHelper;
//    }


    public void setOnline(int code,int uID,String idRoom){
//        HashMap<String,Integer> status = new HashMap<>();
//        status.put("timeRead",code);
        databaseReference.child("user").child(uID+"").child("conversation").child(idRoom+"").child("timeRead")
        .setValue(code);
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

    public void UploadFile(String fileName,Bitmap bmp,final FirebaseCallback callback){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] data = stream.toByteArray();
        StorageReference filepath = mStorageReference.child(fileName);
        UploadTask uploadTask = filepath.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onSuccess(taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void changeAvatar(Bitmap bmp,String type,final FirebaseCallback callback){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (type.equals("small")){
            bmp.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        }else {
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        }
        byte[] data = stream.toByteArray();
        StorageReference filepath = mStorageReference.child("images/avatar/" + type +"/"+SystemHelper.getTimeStamp()+"_"+ entityUserProfile.getUserName());
        UploadTask uploadTask = filepath.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onSuccess(taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void changeImageAvatar(String grName,Bitmap bmp,String type,final FirebaseCallback callback){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] data = stream.toByteArray();
        StorageReference filepath = mStorageReference.child("images/" + type +"/"+SystemHelper.getTimeStamp()+"_"+ grName);
        UploadTask uploadTask = filepath.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onSuccess(taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }



    public interface FirebaseCallback{
        void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
    }
}

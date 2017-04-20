package com.joker.hoclazada.Ultil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import android.content.Context;
import android.net.Uri;

/**
 * Created by joker on 4/17/17.
 */

public class FirebaseHelper {
    private final Context context;
    private final StorageReference storageReference;
    public FirebaseHelper(Context context, StorageReference storageReference) {
        this.context = context;
        this.storageReference = storageReference;
    }
    public void putFile(StorageReference folder, Uri stringPath, OnSuccessListener onSuccessListener
            , OnFailureListener onFailureListener)
    {
            folder.putFile(stringPath).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }
}

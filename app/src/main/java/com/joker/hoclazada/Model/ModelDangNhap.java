package com.joker.hoclazada.Model;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

/**
 * Created by joker on 2/14/17.
 */

public class ModelDangNhap {
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    public AccessToken layTokenFacebookHienTai() {

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    accessToken = currentAccessToken;
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken;
    }
    public void huyToken()
    {
        accessTokenTracker.stopTracking();
    }

}

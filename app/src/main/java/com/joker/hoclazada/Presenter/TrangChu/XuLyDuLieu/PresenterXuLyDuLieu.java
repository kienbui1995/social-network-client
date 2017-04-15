package com.joker.hoclazada.Presenter.TrangChu.XuLyDuLieu;

import android.app.Activity;

import com.facebook.AccessToken;
import com.joker.hoclazada.Model.ModelDangNhap;

/**
 * Created by joker on 2/14/17.
 */

public class PresenterXuLyDuLieu implements IPresenterXuLyMenu {
    String tennguoidung="";
    Activity activity;

    public PresenterXuLyDuLieu(Activity activity) {
        this.activity = activity;
    }

    @Override
    public AccessToken LayTenNguoiDungFacebook() {
        ModelDangNhap modelDangNhap = new ModelDangNhap(activity);
        AccessToken accessToken = modelDangNhap.layTokenFacebookHienTai();
        return accessToken;
    }
    public void HuyToken(){
        ModelDangNhap modelDangNhap = new ModelDangNhap(activity);
        modelDangNhap.huyToken();

    }
}

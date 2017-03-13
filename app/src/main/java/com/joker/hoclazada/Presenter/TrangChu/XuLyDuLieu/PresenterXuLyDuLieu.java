package com.joker.hoclazada.Presenter.TrangChu.XuLyDuLieu;

import android.content.Context;

import com.facebook.AccessToken;
import com.joker.hoclazada.Model.ModelDangNhap;

/**
 * Created by joker on 2/14/17.
 */

public class PresenterXuLyDuLieu implements IPresenterXuLyMenu {
    String tennguoidung="";
    public PresenterXuLyDuLieu(Context context) {

    }

    @Override
    public AccessToken LayTenNguoiDungFacebook() {
        ModelDangNhap modelDangNhap = new ModelDangNhap();
        AccessToken accessToken = modelDangNhap.layTokenFacebookHienTai();
        return accessToken;
    }
    public void HuyToken(){
        ModelDangNhap modelDangNhap = new ModelDangNhap();
        modelDangNhap.huyToken();
    }
}

package Entity;

/**
 * Created by joker on 3/16/17.
 */

public class BaiVietCaNhan {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public long getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(long ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    private int id;
    private String noiDung;
    private long ngayTao;
    private String anh;

    public BaiVietCaNhan(int id, String noiDung, long ngayTao, String anh) {
        this.id = id;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
        this.anh = anh;
    }

}

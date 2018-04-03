package abdul.malik.intaihere.Model;

import android.app.Application;

/**
 * Created by Lenovo on 23/03/2018.
 */

public class ModelTask {
    String  username, waktu, image, status, tujuan, foto_status;

    public ModelTask(){}



    public ModelTask(String username, String waktu, String image, String status, String tujuan, String foto_status) {

        this.username = username;
        this.waktu = waktu;
        this.image = image;
        this.status = status;
        this.tujuan = tujuan;
        this.foto_status = foto_status;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getGambar() {
        return image;
    }

    public void setGambar(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getFoto_status() {
        return foto_status;
    }

    public void setFoto_status(String foto_status) {
        this.foto_status = foto_status;
    }

}

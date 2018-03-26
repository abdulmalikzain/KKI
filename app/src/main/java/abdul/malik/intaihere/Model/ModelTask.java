package abdul.malik.intaihere.Model;

import android.app.Application;

/**
 * Created by Lenovo on 23/03/2018.
 */

public class ModelTask {
    String  username, waktu, image;

    public ModelTask(){}

    public ModelTask( String username, String waktu, String image) {

        this.username = username;
        this.waktu = waktu;
        this.image = image;
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

}

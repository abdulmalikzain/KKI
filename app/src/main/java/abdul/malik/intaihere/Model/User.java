package abdul.malik.intaihere.Model;

import android.app.Application;

/**
 * Created by Lenovo on 04/01/2018.
 */

public class User extends Application {
    private String id, username, email, telephone, latitude, longitude, image;

    public User(String id, String username, String email, String telephone, String latitude, String longitude, String image) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImage() {
        return image;
    }
}

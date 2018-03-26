package abdul.malik.intaihere.MenuProfile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import abdul.malik.intaihere.R;

import static abdul.malik.intaihere.LoginActivity.my_shared_preferences;
import static abdul.malik.intaihere.LoginActivity.session_status;
import static abdul.malik.intaihere.Utils.Server.URL_UBAH_DATA;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText ubah_idp, ubah_namap, ubah_emailp, ubah_telpp, ubah_alamat;
    Button btn_ubahdata;
    Toolbar mActionToolbar;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;

    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ID = "id";
//    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Ubah Data");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ubah_idp = (EditText) findViewById(R.id.ubah_id);
        ubah_idp.setVisibility(View.INVISIBLE);
        ubah_namap = (EditText) findViewById(R.id.eduser);
        ubah_emailp = (EditText) findViewById(R.id.edemail);
        ubah_telpp = (EditText) findViewById(R.id.edtelephone);
        ubah_alamat = (EditText) findViewById(R.id.edtalamat);
        btn_ubahdata = (Button) findViewById(R.id.btn_ubahdata);

        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        String username = (sharedpreferences.getString("username", ""));
        ubah_namap.setText(username);
        String email = (sharedpreferences.getString("email", ""));
        ubah_emailp.setText(email);
        String alamat = (sharedpreferences.getString("alamat", ""));
        ubah_alamat.setText(alamat);
        String telephone = (sharedpreferences.getString("telephone", ""));
        ubah_telpp.setText(telephone);
        String id = (sharedpreferences.getString("id", ""));
        ubah_idp.setText(id);

        btn_ubahdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UbahData();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //method ubah data ke database
    public void UbahData(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.show();
        final String id = ubah_idp.getText().toString();
        final String username = ubah_namap.getText().toString();
        final String email = ubah_emailp.getText().toString();
        final String telephone = ubah_telpp.getText().toString();
        final String alamat = ubah_alamat.getText().toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UBAH_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d( "Respon: ", response.toString());
                            JSONObject obj = new JSONObject(response);

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_ID, id);
                            editor.putString(TAG_EMAIL, email);
                            editor.putString(TAG_USERNAME, username);
                            editor.putString("alamat", alamat);
                            editor.putString("telephone", telephone);
                            editor.commit();


                            Toast.makeText(UpdateProfileActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();

//                            Intent intent = new Intent (UpdateProfileActivity.this, ProfileActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);

                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
//                            Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("username", username);
                params.put("email", email);
                params.put("telephone", telephone);
                params.put("alamat", alamat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    @Override
//    public void onClick(View view) {
//        if (view == btn_ubahdata){
//            UbahData(view);
//        }
//    }

}

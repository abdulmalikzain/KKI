package abdul.malik.intaihere.MenuTask;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.R;
import abdul.malik.intaihere.Utils.Server;

import static abdul.malik.intaihere.LoginActivity.my_shared_preferences;

public class InputTaskActivity extends AppCompatActivity {
    Toolbar mActionToolbar;
    ImageView ivFoto, ivBtnFoto;
    AutoCompleteTextView acStatus;
    MultiAutoCompleteTextView macLokasi;
    Button btnInpBuat;
    SharedPreferences sharedpreferences;
    ProgressDialog progressDialog;

    String image;
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        mActionToolbar = findViewById(R.id.tabs);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Buat Status");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ivFoto     = findViewById(R.id.ivinp_foto);
        ivBtnFoto  = findViewById(R.id.ivinp_btnfoto);
        acStatus   = findViewById(R.id.edinp_status);
        macLokasi  = findViewById(R.id.edinp_lokasitujuan);
        btnInpBuat = findViewById(R.id.btninp_buat);



        btnInpBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTask();
            }
        });

        fotoProfile();

    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void inputTask(){
        String url_inputTask = Server.URL_INPUT_TASK;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengirim status...");
        progressDialog.show();
        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        final String idx = (sharedpreferences.getString("id", ""));
        final String username = (sharedpreferences.getString("username", ""));
        final String lokasi = macLokasi.getText().toString();
        final String status = acStatus.getText().toString();

        long date = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String dateNow = dateFormat.format(date);

        StringRequest request = new StringRequest(Request.Method.POST, url_inputTask, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);

                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_users", idx);
                params.put("username", username);
                params.put("tujuan", lokasi);
                params.put("waktu", dateNow);
                params.put("status", status);
                params.put("image", image);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void fotoProfile(){
        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        final String id = (sharedpreferences.getString("id", ""));
        String url_fotoProfile = Server.URL_DATA_BY + id;

        StringRequest request = new StringRequest(Request.Method.GET, url_fotoProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    image = object.getString("image");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);
    }
}

package abdul.malik.intaihere.MenuSetting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.HomeActivity;
import abdul.malik.intaihere.LoginActivity;
import abdul.malik.intaihere.MenuProfile.ProfileActivity;
import abdul.malik.intaihere.R;
import abdul.malik.intaihere.RegisterActivity;
import abdul.malik.intaihere.Utils.Server;
import de.hdodenhof.circleimageview.CircleImageView;

import static abdul.malik.intaihere.LoginActivity.TAG_EMAIL;
import static abdul.malik.intaihere.LoginActivity.TAG_ID;
import static abdul.malik.intaihere.LoginActivity.TAG_USERNAME;
import static abdul.malik.intaihere.LoginActivity.my_shared_preferences;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    SharedPreferences sharedpreferences;
    Dialog dialog;
    TextView tentang, keluar, hapusakun, tvNama, tvInfoAplikasi;
    RelativeLayout relProfile;
    CircleImageView civFoto;
    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Log.d(TAG, "onCreate: started.");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tabs);
        setTitle("Pengaturan");
        setSupportActionBar(myToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        hapusakun       = (TextView) findViewById(R.id.hapusakun);
        tentang         = (TextView) findViewById(R.id.tentang);
        keluar          = (TextView) findViewById(R.id.keluar);
        civFoto         = findViewById(R.id.civ_settFoto);
        tvNama          = findViewById(R.id.tv_settProfile);
        tvInfoAplikasi  = findViewById(R.id.tv_infoaplikasi);
        relProfile      = findViewById(R.id.relSettProfile);
        relProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        fotoProfile();

        dialog = new Dialog(this, RelativeLayout.LayoutParams.MATCH_PARENT);

        hapusakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);

                sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
                final String username1 = (sharedpreferences.getString("username", ""));
                builder.setTitle("Hapus Akun " )
                        .setMessage(username1)
                        .setIcon(R.drawable.alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeAkun();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
//                        .setIcon(android.R.drawable.alert)
                        .show();
            }
        });
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup();
            }
        });
        tvInfoAplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoAplikasi();
            }
        });
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("Keluar dari Akun ini?")
                        .setIcon(android.R.drawable.ic_lock_power_off)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                keluar();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void ShowPopup (){
        TextView txtclose;
        dialog.setContentView(R.layout.popuptentang);
        txtclose = (TextView) dialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showInfoAplikasi(){
        TextView textView;
        dialog.setContentView(R.layout.popup_info_aplikasi);
        textView = dialog.findViewById(R.id.tv_close_info);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void keluar(){
        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(LoginActivity.session_status, false);
        editor.putString(TAG_ID, null);
        editor.putString(TAG_USERNAME, null);
        editor.putString(TAG_EMAIL, null);
        editor.putString("telephone", null);
        editor.putString("alamat", null);
        editor.putString("image", null);
        editor.clear();
        editor.commit();
        finish();

        Intent intent1 = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent1);
    }

    public void removeAkun() {
        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        final String id = (sharedpreferences.getString("id", ""));
        final String url_removeAkun = "http://trackingeye.000webhostapp.com/trackingeye/delete_akun.php?id="+id;
        StringRequest strReq = new StringRequest(Request.Method.GET, url_removeAkun, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(LoginActivity.session_status, false);
                    editor.putString(TAG_ID, null);
                    editor.putString(TAG_USERNAME, null);
                    editor.putString(TAG_EMAIL, null);
                    editor.putString("telephone", null);
                    editor.putString("alamat", null);
                    editor.putString("image", null);
                    editor.clear();
                    editor.commit();
                    finish();
                        Toast.makeText(getApplicationContext() ,"Akun Berhasil dihapus", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (SettingActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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
                    String foto = object.getString("image");
                    String nama = object.getString("username");
                    tvNama.setText(nama);

                    if (foto.equals("")){
                        foto = "0";
                    }
                    Picasso.with(SettingActivity.this)
                            .load(foto)
                            .placeholder(R.drawable.boy)
                            .into(civFoto);
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

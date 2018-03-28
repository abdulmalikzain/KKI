package abdul.malik.intaihere.MenuAnggota;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.R;
import abdul.malik.intaihere.Utils.Server;

public class DetailUserActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    NetworkImageView thumb_image;
    TextView username, email, telephone, alamat;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    SwipeRefreshLayout swipe;
    String id_news;

    private static final String TAG = DetailUserActivity.class.getSimpleName();

    public static final String TAG_ID       = "id";
    public static final String TAG_USER    = "username";
    public static final String TAG_EMAIL      = "email";
    public static final String TAG_TELEPHONE      = "telephone";
    public static final String TAG_ALAMAT   = "alamat";
    public static final String TAG_GAMBAR   = "image";

    private static final String url_detail  = Server.URL + "getDetailUser.php";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tabs);
        setTitle("Detail User");
        setSupportActionBar(myToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        thumb_image = (NetworkImageView) findViewById(R.id.gambar_detail);
        username       = (TextView) findViewById(R.id.detailuser);
        email         = (TextView) findViewById(R.id.detailemail);
        telephone         = (TextView) findViewById(R.id.detailtelephone);
        alamat          = (TextView) findViewById(R.id.detailalamat);
        swipe       = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_detail);

        id_news = getIntent().getStringExtra(TAG_ID);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           if (!id_news.isEmpty()) {
                               callDetailAnggota(id_news);
                           }
                       }
                   }
        );

    }

    private void callDetailAnggota(final String id){
        swipe.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);

                    String Username    = obj.getString(TAG_USER);
                    String Gambar   = obj.getString(TAG_GAMBAR);
                    String Email      = obj.getString(TAG_EMAIL);
                    String Telephone      = obj.getString(TAG_TELEPHONE);
                    String Alamat      = obj.getString(TAG_ALAMAT);

                    username.setText(Username);
                    email.setText(Email);
                    alamat.setText(Alamat);
                    telephone.setText(Html.fromHtml(Telephone));
                    Picasso.with(getApplication()).load(Gambar).centerCrop().resize(300, 300)
                            .error(R.drawable.boy).into(thumb_image);
//                    if (obj.getString(TAG_GAMBAR)!=""){
//                        thumb_image.setImageUrl(Gambar, imageLoader);
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail News Error: " + error.getMessage());
                Toast.makeText(DetailUserActivity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        callDetailAnggota(id_news);
    }
}

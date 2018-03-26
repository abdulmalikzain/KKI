package abdul.malik.intaihere.MenuAnggota;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import abdul.malik.intaihere.Adapter.NewsAdapter;
import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.HomeActivity;
import abdul.malik.intaihere.Model.ModelData;
import abdul.malik.intaihere.R;
import abdul.malik.intaihere.Utils.Server;
import de.hdodenhof.circleimageview.CircleImageView;

public class AnggotaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    SwipeRefreshLayout swipe;
    List<ModelData> newsList = new ArrayList<ModelData>();

    private static final String TAG = AnggotaActivity.class.getSimpleName();

    private static String url_list   = Server.URL + "getusers.php?offset=";

    private int offSet = 0;

    int no;

    NewsAdapter adapter;

    public static final String TAG_NO       = "no";
    public static final String TAG_ID       = "id";
    public static final String TAG_USER    = "username";
    public static final String TAG_EMAIL      = "email";
    public static final String TAG_TELEPHONE      = "telephone";
    public static final String TAG_GAMBAR   = "image";
    public static final String TAG_ALAMAT   = "alamat";
    Toolbar mActionToolbar;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Anggota");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_anggota);
        list = (ListView) findViewById(R.id.Listview);
        newsList.clear();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(AnggotaActivity.this, DetailUserActivity.class);
                intent.putExtra(TAG_ID, newsList.get(position).getId());
                startActivity(intent);
            }
        });

        adapter = new NewsAdapter(AnggotaActivity.this, newsList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           newsList.clear();
                           adapter.notifyDataSetChanged();
                           callAnggota(0);
                       }
                   }
        );

        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {

                    swipe.setRefreshing(true);
                    handler = new Handler();

                    runnable = new Runnable() {
                        public void run() {
                            callAnggota(offSet);
                        }
                    };

                    handler.postDelayed(runnable, 3000);
                }
            }

        });

    }

    @Override
    public void onRefresh() {
        newsList.clear();
        adapter.notifyDataSetChanged();
        callAnggota(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void callAnggota(int page){

        swipe.setRefreshing(true);

        // Creating volley request obj
        JsonArrayRequest arrReq = new JsonArrayRequest(url_list + page,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    final ModelData news = new ModelData();

                                    no = obj.getInt(TAG_NO);

                                    news.setUsername(obj.getString(TAG_USER));
                                    news.setEmail(obj.getString(TAG_EMAIL));
                                    news.setId(obj.getString(TAG_ID));
                                    news.setAlamat(obj.getString(TAG_ALAMAT));
                                    news.setGambar(obj.getString("image"));
                                    news.setTelephone(obj.getString(TAG_TELEPHONE));

                                    // adding news to news array
                                    newsList.add(news);

                                    if (no > offSet)
                                        offSet = no;

                                    Log.d(TAG, "offSet " + offSet);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data
                                adapter.notifyDataSetChanged();
                            }
                        }
                        swipe.setRefreshing(false);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrReq);
    }
}

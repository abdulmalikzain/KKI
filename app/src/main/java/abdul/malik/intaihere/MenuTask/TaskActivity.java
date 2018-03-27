package abdul.malik.intaihere.MenuTask;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import abdul.malik.intaihere.Adapter.TaskAdapter;
import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.Model.ModelTask;
import abdul.malik.intaihere.R;
import abdul.malik.intaihere.Utils.Server;

import static abdul.malik.intaihere.LoginActivity.my_shared_preferences;

public class TaskActivity extends AppCompatActivity {

    private static final String TAG = "a" ;
    RecyclerView recyclerView;
    ImageView ivFotoProfile;
    LinearLayout linearLayout;

    List<ModelTask> modelTasks;
    String tag_json_obj = "json_obj_req";
    Toolbar mActionToolbar;
    Dialog dialog;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mActionToolbar = (Toolbar) findViewById(R.id.tabs);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Status");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ivFotoProfile = findViewById(R.id.civ_profiletask);
        linearLayout = findViewById(R.id.linear1);

        recyclerView = findViewById(R.id.rv_task);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelTasks = new ArrayList<>();

        tampilStatus();
        fotoProfile();
//        covertTimeToText("datDate", "Nama");

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this, InputTaskActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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

    private void tampilStatus(){
        String url_getTask = Server.URL_GET_TASK;
        StringRequest request = new StringRequest(Request.Method.GET, url_getTask, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("task");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject data = jsonArray.getJSONObject(i);

//                        final ModelTask task= new ModelTask();
//                        task.setUsername(data.getString("username"));
//                        task.setGambar(data.getString("image"));
                        String dataDate = data.getString("waktu");
                        String Nama = data.getString("username");
                        String image = data.getString("image");
                        covertTimeToText(dataDate, Nama, image);

//                        modelTasks.add(task);
                        //creating adapter object and setting it to recyclerview
                        TaskAdapter adapter = new TaskAdapter(TaskActivity.this, modelTasks);
                        recyclerView.setAdapter(adapter);
                    }


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

    private void fotoProfile(){
        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        final String id = (sharedpreferences.getString("id", ""));
        String url_fotoProfile = Server.URL_DATA_BY + id;

        StringRequest request = new StringRequest(Request.Method.GET, url_fotoProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String FotoProfile = object.getString("image");
                    if (FotoProfile.equals("")){
                        FotoProfile = "0";
                    }
                    Picasso.with(TaskActivity.this)
                            .load(FotoProfile)
                            .placeholder(R.drawable.boy)
                            .into(ivFotoProfile);
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

    public String covertTimeToText(String dataDate, String Nama, String image) {

        String convTime = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long detik = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long menit = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long jam   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long hari  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (detik < 60) {
                convTime = detik+"detik lalu";
            } else if (menit < 60) {
                convTime = menit+"menit lalu";
            } else if (jam < 24) {
                convTime = jam+"jam lalu";
            } else if (hari >= 7) {
                if (hari > 30) {
                    convTime = (hari / 30)+"bulan lalu";
                } else if (hari > 360) {
                    convTime = (hari / 360)+"tahun lalu";
                } else {
                    convTime = (hari / 7) + "minggu lalu";
                }
            } else if (hari < 7) {
                convTime = hari+"hari lalu";
            }

            final ModelTask modelTask = new ModelTask();
            modelTask.setWaktu(convTime);
            modelTask.setUsername(Nama);
            modelTask.setGambar(image);
            modelTasks.add(modelTask);


        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;

    }

}

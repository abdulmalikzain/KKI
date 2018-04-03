package abdul.malik.intaihere.MenuTask;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailTaskActivity extends AppCompatActivity {
   private TextView tvDetUsername, tvDetWaktu, tvDetStatus, tvDetTujuan, tvFotoStatus;
   private ImageView ivFotoStatus;
   private String fotoStatus, fotoImage;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        tvDetUsername = findViewById(R.id.tv_detUsername);
        tvDetWaktu    = findViewById(R.id.tv_detWaktu);
        tvDetStatus   = findViewById(R.id.tv_detStatus);
        tvDetTujuan   = findViewById(R.id.tv_detTujuan);
        ivFotoStatus  = findViewById(R.id.iv_detGambar);

        Bundle bundle = getIntent().getExtras();
        tvDetUsername.setText(bundle.getString("username"));
        tvDetWaktu.setText(bundle.getString("waktu"));
        tvDetStatus.setText(bundle.getString("status"));
        tvDetTujuan.setText(bundle.getString("tujuan"));
        tvFotoStatus.setText(bundle.getString("foto_status"));
        fotoStatus = bundle.getString("foto_status");
        Picasso.with(getApplication()).load(fotoStatus).centerCrop().into(ivFotoStatus);
    }
}

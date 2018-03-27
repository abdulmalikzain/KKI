package abdul.malik.intaihere.MenuTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import abdul.malik.intaihere.R;

public class DetailTaskActivity extends AppCompatActivity {
    TextView tvDetUsername, tvDetWaktu, tvDetStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        tvDetUsername = findViewById(R.id.tv_detUsername);
        tvDetWaktu    = findViewById(R.id.tv_detWaktu);
        tvDetStatus   = findViewById(R.id.tv_detStatus);

        Bundle bundle = getIntent().getExtras();
        tvDetUsername.setText(bundle.getString("username"));
        tvDetWaktu.setText(bundle.getString("waktu"));
    }
}

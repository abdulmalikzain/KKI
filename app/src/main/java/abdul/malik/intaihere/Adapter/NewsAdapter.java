package abdul.malik.intaihere.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.HomeActivity;
import abdul.malik.intaihere.MenuAnggota.AnggotaActivity;
import abdul.malik.intaihere.Model.ModelData;
import abdul.malik.intaihere.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lenovo on 04/01/2018.
 */

public class NewsAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelData> newsItems;
    ImageLoader imageLoader;

    public NewsAdapter(Activity activity, List<ModelData> newsItems) {
        this.activity = activity;
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return newsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.layout_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView telephone = (TextView) convertView.findViewById(R.id.telephone);
        CircleImageView imageView = (CircleImageView) convertView.findViewById(R.id.gambaruser);
//        CircleImageView telephone1 = (CircleImageView) convertView.findViewById(R.id.user_telephone);

        final ModelData news = newsItems.get(position);

//        telephone1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:"+news.getTelephone()));
//                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//        });

        Picasso.with(this.activity)
                .load(news.getGambar())
                .error(R.drawable.boy)
                .into(imageView);

        username.setText(news.getUsername());
        telephone.setText(news.getTelephone());

        return convertView;
    }
}

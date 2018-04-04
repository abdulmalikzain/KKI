package abdul.malik.intaihere.Adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import abdul.malik.intaihere.MenuTask.DetailTaskActivity;
import abdul.malik.intaihere.Model.ModelTask;
import abdul.malik.intaihere.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lenovo on 23/03/2018.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<ModelTask> taskList;
    private Context context;

    public TaskAdapter (Context context, List<ModelTask> list) {
        this.context = context;
        this.taskList = list;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        ModelTask modelTask = taskList.get(position);
        holder.tvAUsername.setText(modelTask.getUsername());
        holder.tvAWaktu.setText(modelTask.getWaktu());
        holder.tvStatus.setText(modelTask.getStatus());
        holder.tvTujuan.setText(modelTask.getTujuan());
        holder.tvFoto_status.setText(modelTask.getFoto_status());
        Picasso.with(context).load(modelTask.getGambar()).error(R.drawable.boy).into(holder.civFoto);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvAUsername, tvAWaktu, tvStatus, tvTujuan, tvFoto_status;
        CircleImageView civFoto;

        int post;
        public TaskViewHolder(View itemView) {
            super(itemView);
            tvAUsername = itemView.findViewById(R.id.rvtask_username);
            tvAWaktu = itemView.findViewById(R.id.rvtask_waktu);
            civFoto = itemView.findViewById(R.id.rvtask_foto);
            tvStatus    = itemView.findViewById(R.id.rvtask_status);
            tvTujuan    = itemView.findViewById(R.id.rvtask_tujuan);
            tvFoto_status   = itemView.findViewById(R.id.rvtask_fotostatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post = getAdapterPosition();

                    Intent intent = new Intent(context, DetailTaskActivity.class);
                    intent.putExtra("username", tvAUsername.getText().toString().trim());
                    intent.putExtra("waktu", tvAWaktu.getText().toString().trim());
                    intent.putExtra("status", tvStatus.getText().toString().trim());
                    intent.putExtra("tujuan", tvTujuan.getText().toString().trim());
                    intent.putExtra("foto_status", tvFoto_status.getText().toString().trim());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_task, parent, false);
        return new TaskAdapter.TaskViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


}

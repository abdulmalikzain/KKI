package abdul.malik.intaihere.Adapter;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

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
        Picasso.with(context).load(modelTask.getGambar()).error(R.drawable.boy).into(holder.civFoto);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvAUsername, tvAWaktu;
        CircleImageView civFoto;
        public TaskViewHolder(View itemView) {
            super(itemView);
            tvAUsername = itemView.findViewById(R.id.rvtask_username);
            tvAWaktu = itemView.findViewById(R.id.rvtask_waktu);
            civFoto = itemView.findViewById(R.id.rvtask_foto);
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

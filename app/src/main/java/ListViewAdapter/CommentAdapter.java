package ListViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.alimahmoodvan.getfollower.R;
import ir.alimahmoodvan.getfollower.SettingActivity;


/**
 * Created by acer on 10/7/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<String> comment=new ArrayList<>();
    private List<String> selected=new ArrayList<>();
    public CommentAdapter(List<String> comment){
        this.comment=comment;
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, final int position) {
        holder.comment.setText(comment.get(position));
        holder.comment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (isChecked) {
                        selected.add(comment.get(position));
                    } else {
                        selected.remove(position);
                    }
                    Log.d(SettingActivity.TAG, "onCheckedChanged: "+selected);
                }
                catch (Exception ex){
                    Log.d(SettingActivity.TAG, "onCheckedChanged: "+ex.getMessage());
                }
            }
        });
        holder.commentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comment.size();
    }
    public List<String> getSelected() {
        return selected;
    }

    public void addItem(String string) {
        comment.add(0,string);
        notifyDataSetChanged();
    }
}


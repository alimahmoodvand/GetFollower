package ListViewAdapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import ir.alimahmoodvan.getfollower.R;

/**
 * Created by acer on 10/7/2018.
 */

public class CommentViewHolder extends  RecyclerView.ViewHolder {
    public AppCompatCheckBox comment;
    public ImageButton commentDelete;

    public CommentViewHolder(View itemView) {
        super(itemView);
        comment=itemView.findViewById(R.id.comment_chkbox);
        commentDelete=itemView.findViewById(R.id.comment_dlt);

    }
}

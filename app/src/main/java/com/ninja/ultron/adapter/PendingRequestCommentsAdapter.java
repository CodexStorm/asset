package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.PendingRequestsCommentsEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 24-May-17.
 */

public class PendingRequestCommentsAdapter extends RecyclerView.Adapter<PendingRequestCommentsAdapter.ViewHolder> {

    List<PendingRequestsCommentsEntity> commentsList;
    PendingRequestsCommentsEntity comment;

    public PendingRequestCommentsAdapter(List<PendingRequestsCommentsEntity> commentsList) {
        this.commentsList = commentsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_request_comments_card,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        comment=commentsList.get(position);
        holder.tvId.setText(comment.getPostedBy()+"");
        holder.tvText.setText(comment.getText());

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvId,tvText;
        public ViewHolder(View itemView) {
            super(itemView);
            tvId=(TextView)itemView.findViewById(R.id.tvCommentId);
            tvText=(TextView)itemView.findViewById(R.id.tvCommentText);
        }
    }

}

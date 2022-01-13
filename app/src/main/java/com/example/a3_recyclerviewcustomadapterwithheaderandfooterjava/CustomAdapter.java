package com.example.a3_recyclerviewcustomadapterwithheaderandfooterjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_AVAILABLE_YES = 1;
    public static final int TYPE_UNAVAILABLE_NO = 2;
    public static final int TYPE_FOOTER = 3;

    Context context;
    ArrayList<Student> studentArrayList;
    OnBottomReachedListener listener;

    public CustomAdapter(Context context, ArrayList<Student> studentArrayList, OnBottomReachedListener listener) {
        this.context = context;
        this.studentArrayList = studentArrayList;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        } else if (studentArrayList.get(position).availabale) {
            return TYPE_AVAILABLE_YES;
        } else {
            return TYPE_UNAVAILABLE_NO;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AVAILABLE_YES) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
            return new CustomViewHolder(view);
        } else if (viewType == TYPE_UNAVAILABLE_NO) {
            View view = LayoutInflater.from(context).inflate(R.layout.unavailable_item, parent, false);
            return new CustomViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new CustomViewHolderHeader(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            return new CustomViewHolderFooter(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(position == studentArrayList.size() - 1){
            listener.onBottomReached(position);
        }

        if (isHeader(position))
            ((CustomViewHolderHeader) holder).tvHeaderClaim.setText("Header");
        else if (holder instanceof CustomViewHolder) {
            ((CustomViewHolder) holder).tvOrder.setText(String.valueOf(position));
            ((CustomViewHolder) holder).tvFullName.setText(studentArrayList.get(position).fullName);
            ((CustomViewHolder) holder).tvAge.setText(String.valueOf(studentArrayList.get(position).age));
        } else if (isFooter(position))
            ((CustomViewHolderFooter) holder).tvFooterClaim.setText("Footer");
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

     static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrder = itemView.findViewById(R.id.order_no_item);
        TextView tvFullName = itemView.findViewById(R.id.tv_fullname_item);
        TextView tvAge = itemView.findViewById(R.id.tv_age_item);

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class CustomViewHolderHeader extends RecyclerView.ViewHolder {
        TextView tvHeaderClaim = itemView.findViewById(R.id.item_header_claim);

        public CustomViewHolderHeader(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class CustomViewHolderFooter extends RecyclerView.ViewHolder {
        TextView tvFooterClaim = itemView.findViewById(R.id.item_footer_claim);

        public CustomViewHolderFooter(@NonNull View itemView) {
            super(itemView);
        }

    }

    boolean isHeader(int x) {
        return x == 0;
    }

    boolean isFooter(int x) {
        return x == studentArrayList.size() - 1;
    }
}

package com.snystudio.simpleaplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snystudio.simpleaplication.R;
import com.snystudio.simpleaplication.model.ListModel;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {


    Context mContext;
    List<ListModel> listModels;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    public ChecklistAdapter(Context mContext, List<ListModel> listModels) {
        this.mContext = mContext;
        this.listModels = listModels;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public void setOnLongClickListener(final OnItemLongClickListener mOnLongCLick){
        this.onItemLongClickListener=mOnLongCLick;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position, ListModel model);
    }
    public interface OnItemLongClickListener {
        void onItemClick(View view, int position, ListModel model);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.itemlist,parent,false);
        final ChecklistAdapter.ViewHolder mHolder=new ChecklistAdapter.ViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListModel listModelss=listModels.get(position);
        holder.nameList.setText(listModelss.getNameList());

    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameList;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameList=itemView.findViewById(R.id.textview);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(itemView,getAdapterPosition(),listModels.get(getAdapterPosition()));
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemClick(itemView,getAdapterPosition(),listModels.get(getAdapterPosition()));
                return false;
            }
        });
    }
}
}

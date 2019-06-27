package com.example.day_gouwuche.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.day_gouwuche.MainActivity;
import com.example.day_gouwuche.R;
import com.example.day_gouwuche.bean.CaiBean;



import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private MainActivity mainActivity;
    private ArrayList<CaiBean.DataBean> list;

    public MyAdapter(MainActivity mainActivity, ArrayList<CaiBean.DataBean> list) {

        this.mainActivity = mainActivity;
        this.list = list;
    }

    public void setList(ArrayList<CaiBean.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mainActivity, R.layout.layout_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CaiBean.DataBean dataBean = list.get(i);
        final ViewHolder holder = viewHolder;
        Glide.with(mainActivity)
                .load(dataBean.getPic())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImg);
        holder.mTitle.setText(dataBean.getTitle());
        holder.mPicer.setText(dataBean.getNum()+"");

        final Integer integer = new Integer(i);
        holder.mCheck.setTag(integer);
        if (MainActivity.map.containsKey(integer)){
            holder.mCheck.setChecked(true);
        }else {
            holder.mCheck.setChecked(false);
        }

        holder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mCheck.isChecked()){
                    MainActivity.map.put(integer,true);
                }else {
                    MainActivity.map.remove(integer);
                }
                if (onClick!=null){
                    onClick.onItem(v,dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
       private CheckBox mCheck;
        private ImageView mImg;
        private TextView mTitle;
        private TextView mPicer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheck =  itemView.findViewById(R.id.check);
            mImg =  itemView.findViewById(R.id.img);
            mTitle =  itemView.findViewById(R.id.title);
            mPicer =  itemView.findViewById(R.id.picer);
        }
    }
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void onItem(View view,CaiBean.DataBean dataBean);
    }

}

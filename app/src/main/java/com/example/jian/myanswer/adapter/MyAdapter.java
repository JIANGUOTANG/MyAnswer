package com.example.jian.myanswer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jian on 2017/2/2.
 */
public abstract class MyAdapter<T> extends RecyclerView.Adapter<ItemViewHolder>{
    protected List<T> mDatas;
    protected int mLayoutId;
    protected int mVariableId;
    public MyAdapter(int layoutId , int variableId , List<T> datas) {
        mDatas = datas;
        mVariableId = variableId;
        mLayoutId = layoutId;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ItemViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        convert(holder , mDatas.get(position),position);
    }
    public abstract void convert(ItemViewHolder holder, T t,int position);
    @Override
    public int getItemCount() {
        if(mDatas==null){
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
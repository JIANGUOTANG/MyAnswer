package com.example.jian.myanswer.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jian on 2017/2/2.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mBinding;

    public ItemViewHolder(View v) {
        super(v);
        mBinding = DataBindingUtil.bind(v);
    }

    /**
     * serBinding方法使用了Binding类中的setVariable()方法，为布局文件中<layout>标签下的<variable>赋值，完成数据的绑定。
     * @param variableId
     * @param object
     * @return
     */
    public ItemViewHolder setBinding(int variableId , Object object){
        mBinding.setVariable(variableId , object);//
        mBinding.executePendingBindings();
        return this;
    }
}

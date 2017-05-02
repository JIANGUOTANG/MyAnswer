package com.example.jian.myanswer.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by jian on 2017/4/13.
 */
public abstract class ChoiceAdapter<T extends ViewHolder> extends RecyclerView.Adapter<T>{
    private String[] options;
    private boolean onClickAble = true;
    public ChoiceAdapter(String[] options) {
        this.options = options;
    }
    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
    @Override
    public void onBindViewHolder(T holder, int position) {
        String str = options[position+1];
        String regex = "[A-J][.)，\\]]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher =pattern.matcher(str);
        String  option = matcher.replaceFirst("");//将ABCD等去掉
        char letter = (char) ('A'+position);
        onMyBindViewHolder(holder,position,option,letter);
    }
    @Override
    public int getItemCount() {
        return options.length -1;
    }
    /**
     *
     * @param holder
     * @param position 位置
     * @param text  //选项
     * @param letter  //ABCD
     */
    abstract void onMyBindViewHolder(T holder, int position,String text,char letter);

}

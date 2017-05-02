package com.example.jian.myanswer.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.view.MyCircleRadioButton;

/**
 * Created by jian on 2017/4/13.
 */

public class SingleChoiceAdapter extends ChoiceAdapter<SingleChoiceAdapter.MViewHolder>{
    private String[] options;
    private boolean onClickAble = true;

    public void setOnClickAble(boolean onClickAble) {
        this.onClickAble = onClickAble;
    }
    private Context context;
    private Item item;
    public SingleChoiceAdapter(Item item, String[] options,Context context) {
        this(options);
        this.item = item;
        this.context = context;
    }
    public SingleChoiceAdapter(String[] options) {
        super(options);
        this.options = options;
    }
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singlechoice, parent, false);
        return new MViewHolder(v);
    }
    public void setOnChoiceChangeListener(OnChoiceChangeListener onChoiceChangeListener) {
        this.onChoiceChangeListener = onChoiceChangeListener;
    }
    private OnChoiceChangeListener onChoiceChangeListener;
    @Override
    void onMyBindViewHolder(final MViewHolder holder, final int position, String option, char letter) {
        holder.tvOption.setText(option);
        initColor(holder.itemView,letter);
        holder.myCheckBox.setText(letter+"");
        //点击整一个选项
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置选项的选中状态
                if(onClickAble){
                    holder.myCheckBox.setChecked(true);
                    if(onChoiceChangeListener!=null){//不是空的时候返回选中的状态
                        onChoiceChangeListener.onChoice(position,holder.itemView);
                    }
                }
            }
        });
    }
    private void initColor(View v,char letter){
        //当答案和选中的一样的时候
        if (!item.getResponse().equals("")) {
            //答案不为空
            if (!item.getAnswer().equals("")) {
                //答案和答的相同
                if (item.getAnswer().equals(item.getResponse())&&item.getResponse().equals(letter+"")) {
                    onClickAble = false;
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
                    //答错的题目，显示错误的颜色
                } else  if (!item.getAnswer().equals(item.getResponse())&&item.getResponse().equals(letter+"")) {
                    onClickAble = false;
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.spark_secondary_color));
                }
            }
        }
    }
    class MViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOption;
        private MyCircleRadioButton myCheckBox;
        public MViewHolder(View itemView) {
            super(itemView);
            tvOption = (TextView) itemView.findViewById(R.id.tvOption);
            myCheckBox = (MyCircleRadioButton) itemView.findViewById(R.id.mCircleCheckBox);
        }
    }
   public interface OnChoiceChangeListener{
        void onChoice(int position,View v);

    }
}

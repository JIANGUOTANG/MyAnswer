package com.example.jian.myanswer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.view.MyCheckBox;

/**
 * Created by jian on 2017/4/13.
 */

public class MultipleChoiceAdapter  extends ChoiceAdapter<MultipleChoiceAdapter.MViewHolder>{
    private String[] options;
    public MultipleChoiceAdapter(String[] options) {
        super(options);
        this.options = options;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multiplechoice, parent, false);
        return new MViewHolder(v);
    }
    public void setOnChoiceChangeListener(OnChoiceChangeListener onChoiceChangeListener) {
        this.onChoiceChangeListener = onChoiceChangeListener;
    }
    private OnChoiceChangeListener onChoiceChangeListener;
    @Override
    void onMyBindViewHolder(final MViewHolder holder, final int position, String text, char letter) {
        holder.tvOption.setText(text);
        holder.myCheckBox.setText(letter+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.myCheckBox.toggle();
                onChoiceChangeListener.onChecked( holder.myCheckBox.isChecked(),position);
            }
        });
    }

    class MViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOption;
        private MyCheckBox myCheckBox;
        public MViewHolder(View itemView) {
            super(itemView);
            tvOption = (TextView) itemView.findViewById(R.id.tvOption);
            myCheckBox = (MyCheckBox) itemView.findViewById(R.id.mCheckBox);
        }
    }
   public interface OnChoiceChangeListener{
        void onChecked(boolean isChecked,int position);

    }
}

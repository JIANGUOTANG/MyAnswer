package com.example.jian.myanswer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.bean.Question;
import com.example.jian.myanswer.view.ErrorQuestionView;

import java.util.List;

/**
 * Created by jian on 2017/2/22.
 */

public class ErrorQuestionAdapter extends RecyclerView.Adapter<ErrorQuestionAdapter.MyViewhodler> {
    private List<Question> questions;
    private Context mContext;
    private ErrorQuestionView mainView;
    public ErrorQuestionAdapter(List<Question> questions, Context mContext, ErrorQuestionView mainView) {
        this.questions = questions;
        this.mContext = mContext;
        this.mainView = mainView;
    }

    @Override
    public MyViewhodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_error_questlist,parent,false);

        return new MyViewhodler(v);
    }

    @Override
    public void onBindViewHolder(MyViewhodler holder, final int position) {
        Question question= questions.get(position);
        if(question!=null) {
            holder.tvName.setText(question.getQuestionName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainView.toQuetionActivity(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class MyViewhodler extends RecyclerView.ViewHolder{
        TextView tvName;
        public MyViewhodler(View itemView) {

            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvErrorQuestionName);
        }
    }
}

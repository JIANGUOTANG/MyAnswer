package com.example.jian.myanswer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jian.myanswer.R;

/**
 * Created by jian on 2017/4/23.
 */


public class GraFillingAdapter extends RecyclerView.Adapter<GraFillingAdapter.Myholder> {
    private int size;//填空题有多少个空

    public GraFillingAdapter(int size) {
        this.size = size;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grafilling,parent,false);
        return new Myholder(v);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        holder.tvNumber.setText("("+(position+1)+")");
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class Myholder extends RecyclerView.ViewHolder{
        TextView tvNumber;//显示1,2,3
        EditText edtGraFilling;//
        public Myholder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            edtGraFilling = (EditText) itemView.findViewById(R.id.edtGraFilling);
        }
    }
}

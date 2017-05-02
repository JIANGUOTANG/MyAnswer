package com.example.jian.myanswer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.bean.SimulationInfo;

import java.util.List;

/**
 * Created by jian on 2017/2/27.
 */

public  abstract class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<SimulationInfo> simulationInfos;
    private Context context;

    public ListAdapter(List<SimulationInfo> simulationInfos, Context context) {
        this.simulationInfos = simulationInfos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulation, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SimulationInfo simulationInfo = simulationInfos.get(position);
        holder.textView.setText(simulationInfo.getName());
        convert(holder , simulationInfo,position);
    }
    public abstract void convert(MyViewHolder holder,SimulationInfo t, int position);
    @Override
    public int getItemCount() {
        return simulationInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
     TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvTestTitle);
        }
    }
}

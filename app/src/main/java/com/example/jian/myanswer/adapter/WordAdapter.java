package com.example.jian.myanswer.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.bean.localFileInfo;

import java.util.List;

/**
 * Created by jian on 2017/4/3.
 * 手机中的word
 */

public class  WordAdapter extends RecyclerView.Adapter<WordAdapter.docViewHolder>{
    private List<localFileInfo> fileNames;

    public WordAdapter(List<localFileInfo> fileNames) {
        this.fileNames = fileNames;
    }

    public void setFileSelectListener(WordAdapter.FileSelectListener fileSelectListener) {
        this.fileSelectListener = fileSelectListener;
    }

    private FileSelectListener fileSelectListener;
    @Override
    public docViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_docfile, parent, false);
        return  new docViewHolder(v);
    }

    @Override
    public void onBindViewHolder(docViewHolder holder, final int position) {
        final localFileInfo localFileInfo = fileNames.get(position);
         holder.fileName.setText(localFileInfo.getFileName());
        if(localFileInfo.getFileName().contains(".txt")){
            holder.imageView.setImageResource(R.drawable.ic_txt);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileSelectListener!=null) {
                    fileSelectListener.fileResolve(localFileInfo,position);
                }
            }
        });
    }
   public interface FileSelectListener{
       void fileResolve(localFileInfo localFileInfo,int position);//解析文件
   }
    @Override
    public int getItemCount() {
        return fileNames.size();
    }

    class docViewHolder extends RecyclerView.ViewHolder{
        TextView fileName;
        AppCompatImageView imageView ;
        public docViewHolder(View itemView) {
            super(itemView);
            fileName = (TextView) itemView.findViewById(R.id.tv_fileName);
            imageView  = (AppCompatImageView) itemView.findViewById(R.id.img_fileType);
        }

    }
}

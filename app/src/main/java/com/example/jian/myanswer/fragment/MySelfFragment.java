package com.example.jian.myanswer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.view.MainView;

/**
 * Created by jian on 2017/2/21.
 */

public class MySelfFragment extends Fragment {
   private View v ;
      private MainView mainView;
    private TextView tvCollect;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_myself,container,false  );
        initView();
        return v;

    }
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
    private void initView() {
        tvCollect = (TextView) v.findViewById(R.id.tvCollect);
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.toCollectAvtivity();
            }
        });
    }
}

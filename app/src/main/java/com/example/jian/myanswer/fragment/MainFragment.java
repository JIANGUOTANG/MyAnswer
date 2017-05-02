package com.example.jian.myanswer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.view.MainView;

/**
 * Created by jian on 2017/2/21.
 */

public class MainFragment  extends Fragment{
    private View v ;
    private CardView cvPractice;
    private CardView cvSimulation;
    private CardView cvPs;
    private CardView cvWps;
    private CardView cvIMPort;
    private CardView cvMyTestLibrary;
    private MainView  mainView;
    public void setMainViwe(MainView  mainView){
        this.mainView = mainView;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v  = inflater.inflate(R.layout.fragment_main,container,false);
        intitView();
        initEvent();
         return v;
    }


    private void initEvent() {
        cvPractice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int x = (int) motionEvent.getRawX();
                int y = (int) motionEvent.getRawY();
                mainView.toPracticeActivity(cvPractice,x,y);
                return false;
            }
        });
        cvSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.toTestActivity();
            }
        });
        cvPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.toPsActivity();
            }
        });
        cvWps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.toWpsActivity();
            }
        });
        cvIMPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.toImportActivity();
            }
        });
        cvMyTestLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.toMyTestLibraryActivity();
            }
        });
    }

    private void initData() {

    }

    private void intitView() {
        cvPractice = (CardView) v.findViewById(R.id.cvPractice);
        cvSimulation = (CardView) v.findViewById(R.id.cvSimulation);
        cvPs = (CardView) v.findViewById(R.id.cvPs);
        cvWps = (CardView) v.findViewById(R.id.cvWps);
        cvIMPort = (CardView) v.findViewById(R.id.cvImport);
        cvMyTestLibrary = (CardView) v.findViewById(R.id.cvMyTestLibrary);
    }


}

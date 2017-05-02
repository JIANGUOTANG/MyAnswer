package com.example.jian.myanswer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.adapter.MyPagerAdapter;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.bean.program.Items;
import com.example.jian.myanswer.fragment.ChoiceFragment;
import com.example.jian.myanswer.fragment.GraFillingFragment;
import com.example.jian.myanswer.fragment.MultipleChoiceFragment;
import com.example.jian.myanswer.fragment.OperationQuesiontFragment;
import com.example.jian.myanswer.fragment.SingleChoiceFragment;
import com.example.jian.myanswer.util.ImportProgramUtil;
import com.example.jian.myanswer.util.ProgramManager;
import com.example.jian.myanswer.view.ProgramView;
import com.example.jian.myanswer.viewpager.SuperViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.jian.myanswer.util.ProgramManager.CHECKING;
import static com.example.jian.myanswer.util.ProgramManager.GAPFILLING;
import static com.example.jian.myanswer.util.ProgramManager.MUTIPLECHOICE;
import static com.example.jian.myanswer.util.ProgramManager.OPERATING;
import static com.example.jian.myanswer.util.ProgramManager.SINGLECHOICE;

/**
 * 一完整的试卷显示界面，包括分析
 * Created by jian on 2017/4/16.
 */

public class ProgramActivity extends MyBaseActivity implements ProgramView {

    private String programPath;
    SuperViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();
    public TextView tvPosition;//显示当前的页数
    public int position;//当前第几页
    public MyPagerAdapter<Fragment> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        initView();
        initData();
    }
    ProgramManager ProgramManager;
    private void initData() {

        ProgramManager  = new ProgramManager();
        //判断数据库中是否存在该试卷，否则进行加载
        if(ProgramManager.getProgram(ImportProgramUtil.name)==null){
            ProgramManager.init(ImportProgramUtil.creatProgram());
        }
        List<Items> items = ProgramManager.getItems();
        //把各个大题排序
        Collections.sort(items, new Comparator<Items>() {
            @Override
            public int compare(Items o1, Items o2) {
                return o1.getType()-o2.getType();
            }
        });

        for(Items items1:items){
            switch (items1.getType()){
                case SINGLECHOICE:
                    List<Item> item = items1.getItem();
                    for(Item item1:item){
                        ChoiceFragment choiceFragment = new SingleChoiceFragment(item1,this,this);
                        mFragments.add(choiceFragment);
                    }
                    break;
                case MUTIPLECHOICE:
                    List<Item> item2 = items1.getItem();
                    for(Item item1:item2){
                        ChoiceFragment choiceFragment = new MultipleChoiceFragment(item1,this,this);
                        mFragments.add(choiceFragment);
                    }
                    break;
                case CHECKING:

                    break;
                case GAPFILLING:
                    List<Item> item3 = items1.getItem();
                    for(Item item1:item3){
                        GraFillingFragment graFillingFragment = new GraFillingFragment(item1);
                        mFragments.add(graFillingFragment);
                    }
                    break;
                case OPERATING:
                    List<Item> item4 = items1.getItem();
                    for(Item item1:item4){
                        OperationQuesiontFragment operationQuesiontFragment = new OperationQuesiontFragment(item1);
                        mFragments.add(operationQuesiontFragment);
                    }
                    break;
            }
        }
        ProgramManager.searchAnswer();
        adapter = new MyPagerAdapter<>(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);
    }

    public void initView() {
        mViewPager = (SuperViewPager) findViewById(R.id.viewpager);
        tvPosition = (TextView) findViewById(R.id.tvPosition);
    }


    @Override
    public void toNextPager() {
       mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
    }

    @Override
    public void finish() {
        ProgramManager.saveProgram();
        super.finish();

    }
}

package com.example.jian.myanswer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.fragment.ErrorQuestionFragment;
import com.example.jian.myanswer.fragment.MainFragment;
import com.example.jian.myanswer.fragment.MySelfFragment;
import com.example.jian.myanswer.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView{



    private Fragment mFragments[];
    private  FragmentTransaction fragmentTransaction;
    private  FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private MySelfFragment mySelfFragment;
    private ErrorQuestionFragment errorQuestionFragment;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initFragment();
        initEvent();

    }
    private void initEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                switch (radioGroup.getCheckedRadioButtonId()){
                    case  R.id.rdBtAnswer:
                        fragmentTransaction.show(mFragments[0]).commit();
                    break;
                    case  R.id.rdBtError:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;
                    case  R.id.rdBtMySelf:
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;
                }
            }
        });
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupMenu);

    }
     private void initFragment(){
         mFragments = new Fragment[3];
         mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMain);
         errorQuestionFragment = (ErrorQuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentError);
         mySelfFragment =(MySelfFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMySelf);
         mySelfFragment.setMainView(this);
         mFragments[0] = mainFragment;
         mainFragment.setMainViwe(this);
         mFragments[1] = errorQuestionFragment;
         errorQuestionFragment.setMainView(this);
         mFragments[2] = mySelfFragment;
         fragmentManager = getSupportFragmentManager();
         fragmentTransaction = fragmentManager.beginTransaction()
                 .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
         // 显示主页
         fragmentTransaction.show(mFragments[0]).commit();
     }
    private void initData() {

    }
    @Override
    public void showLoading() {

    }
private int REQUEST_CODE = 100;
    @Override
    public void toPracticeActivity(CardView v,int x,int y) {
//        Intent intent = new Intent(MainActivity.this,PracticeActivity.class);
//        CircularAnimUtil.startActivityForResult(MainActivity.this,  intent, REQUEST_CODE,null, v,R.color.colorBlue,200);
    }

    @Override
    public void toTestActivity() {
//        Intent intent = new Intent(MainActivity.this,SimulationActivity.class);
//        intent.putExtra("tag", "office");
//        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFaileError() {

    }

    /**
     * 回调函数
     * requestCode
     *           M--->E   M跳转时定义的标识
     * resultCode
     *           E--->M   E跳转时定义的标识
     * data
     *           E--->M  携带信息数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE){
            errorQuestionFragment.reFleshData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void toErrorQuestionActivity(int position) {
//        Intent intent = new Intent(MainActivity.this,ErrorQuestionActivity.class);
//        intent.putExtra("position",position);
//        startActivity(intent);

    }

    @Override
    public void toCollectAvtivity() {
//        Intent intent = new Intent(MainActivity.this,CollectionListActivity.class);
//
//        startActivity(intent);
    }

    @Override
    public void toWpsActivity() {
//        Intent intent = new Intent(MainActivity.this,SimulationActivity.class);
//        intent.putExtra("tag", "wps");
//        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void toPsActivity() {
        Toast.makeText(this,"敬请期待",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MainActivity.this,SimulationActivity.class);
//        intent.putExtra("tag","ps1");
//        startActivity(intent);
    }

    @Override
    public void toMyTestLibraryActivity() {
        Intent intent = new Intent(MainActivity.this,MyTestLibraryActivity.class);
        startActivity(intent);
    }

    @Override
    public void toImportActivity() {
        Intent intent = new Intent(MainActivity.this,ImportActivity.class);
        startActivity(intent);
    }

}

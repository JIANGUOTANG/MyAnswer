package com.example.jian.myanswer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.adapter.WordAdapter;
import com.example.jian.myanswer.bean.localFileInfo;
import com.example.jian.myanswer.presenter.ImportFilePresenter;
import com.example.jian.myanswer.util.ImportProgramUtil;
import com.example.jian.myanswer.util.PermissionUtils;
import com.example.jian.myanswer.view.ImportFileView;
import com.example.jian.myanswer.view.progressbar.LVBlock;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by jian on 2017/4/1.
 */
public class ImportActivity extends MyBaseActivity implements ImportFileView{
    private FrameLayout mFrtImportContent;
    private LinearLayout llytImportFile;
    private LinearLayout llytChoiceFile;
    private ImportFilePresenter importFilePresenter;//MVP模式中的P
    private WordAdapter wordAdapter;//文档适配器
    private List<localFileInfo>  localFileInfos;
    private RecyclerView recyclerView;
    private LVBlock  mLVBlock;//加载动画
    private TextView tvSeachFileStatus;//加载状态
    private TextView tvReFresh;//刷新
    private TextView tvFileName;//文件的名称
    private Button btFinished;//完成按钮
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        requestPermission();
        mFrtImportContent = (FrameLayout) findViewById(R.id.frt_importContent);
        importFilePresenter = new ImportFilePresenter(this);
        //申请权限

        setupToolbar();
        initView();
        initEvent();
       // initData();
    }

    private void initEvent() {
        btFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportActivity.this,ProgramActivity.class);
                ImportProgramUtil.path = localFileInfo.getFilePath();
                ImportProgramUtil.name = localFileInfo.getFileName();
                startActivity(intent);
            }
        });
    }

    private void initData() {
        localFileInfos = new ArrayList<>();
        importFilePresenter.loadFile();//加载本地种的文档
    }
    private void initView() {
        mLVBlock = (LVBlock) findViewById(R.id.lv_block);
        mLVBlock.setViewColor(Color.rgb(245,209,22));//设置加载动画的颜色
        mLVBlock.setShadowColor(Color.BLACK);//阴影设置
        tvReFresh = (TextView) findViewById(R.id.tvRefresh);
        btFinished = (Button) findViewById(R.id.btFinished);
        tvSeachFileStatus = (TextView) findViewById(R.id.tvSeachFileStatus);
        tvFileName= (TextView) findViewById(R.id.tv_fileName);
        tvReFresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvReFresh.setVisibility(View.INVISIBLE);
                startAnim();//显示加载动画
                localFileInfos.removeAll(localFileInfos);//清除原有的列表
                importFilePresenter.loadFile();//重新加载本地种的文档
            }
        });
        startAnim();//显示加载动画
        llytImportFile = (LinearLayout) findViewById(R.id.llytImportFile);
        llytChoiceFile  = (LinearLayout) findViewById(R.id.llytChoiceFile);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFile);
         initData();
        importFilePresenter.toChoiceFileScene();
    }


    //加载动画
    public void startAnim() {
        mLVBlock.startAnim();
    }
    //停止加载动画
    public void stopAnim() {
        mLVBlock.stopAnim();
        tvReFresh.setVisibility(View.VISIBLE);
        tvSeachFileStatus.setText(getResources().getString(R.string.referFinished));
    }
    @Override
    public void toImportScene() {

        llytChoiceFile.setVisibility(View.INVISIBLE);
    }

    @Override
    public void toChoiceFileSecne() {
        llytChoiceFile.setVisibility(View.VISIBLE);

    }
    localFileInfo localFileInfo;
    @Override
    public void upDateList(localFileInfo localFileInfo) {

        if(localFileInfos.size()==0){
            localFileInfos.add(localFileInfo);
            wordAdapter = new WordAdapter(localFileInfos);
            wordAdapter.setFileSelectListener(new WordAdapter.FileSelectListener() {
                @Override
                public void fileResolve(localFileInfo localFileInfo, int position) {
                    importFilePresenter.stopSeach();
                    ImportActivity.this.toImportScene();
                    tvFileName.setText(localFileInfo.getFileName());
                    ImportActivity.this.localFileInfo = localFileInfo;
                }
            });
            recyclerView.setAdapter(wordAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else{
            localFileInfos.add(localFileInfo);
            wordAdapter.notifyDataSetChanged();
        }

    }
    private final int REQUEST_CODE= 2;//请求码
    private void requestPermission(){
        if (PermissionUtils.isOwnPermisson(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //如果已经拥有改权限
        } else {
            //没有改权限，需要进行请求
            PermissionUtils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("request", "success");
                } else {
                    Log.i("request", "failed");
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

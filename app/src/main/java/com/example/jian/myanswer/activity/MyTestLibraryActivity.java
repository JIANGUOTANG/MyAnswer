package com.example.jian.myanswer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.viewpagercard.CardFragmentPagerAdapter;
import com.example.jian.myanswer.viewpagercard.ShadowTransformer;

public class MyTestLibraryActivity extends AppCompatActivity implements View.OnClickListener
    {
    private ViewPager mViewPager;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mFragmentCardShadowTransformer.enableScaling(true);
    }
    @Override
    public void onClick(View view) {

    }
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}

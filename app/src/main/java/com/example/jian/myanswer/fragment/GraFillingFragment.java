package com.example.jian.myanswer.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jian.myanswer.R;
import com.example.jian.myanswer.adapter.GraFillingAdapter;
import com.example.jian.myanswer.bean.program.Item;
/**
 * Created by jian on 2017/4/24.
 */
public class GraFillingFragment extends Fragment {
    private View v;
    private RecyclerView recyclerViewGraFilling;
    private Item item;
    private GraFillingAdapter graFillingAdapter;
    public GraFillingFragment(Item item) {
        this.item = item;
    }
    private TextView tvQuestion;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_garfilling,container,false);
        initView();
        initData();
        return v;
    }
    String str[];
    private void initData() {
         str = item.getQuestion().split("@@@");
        tvQuestion.setText(str[0]);
        graFillingAdapter = new GraFillingAdapter(str.length-1);
    }
    private void initView() {
        recyclerViewGraFilling = (RecyclerView) v.findViewById(R.id.recyclerViewGraFilling);
        tvQuestion = (TextView) v.findViewById(R.id.tvQuestion);

    }


}

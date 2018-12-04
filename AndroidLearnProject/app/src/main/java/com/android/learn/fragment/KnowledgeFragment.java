package com.android.learn.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.android.learn.R;
import com.android.learn.adapter.HorizontalPagerAdapter;
import com.android.learn.base.activity.BaseActivity;
import com.android.learn.base.fragment.BaseMvpFragment;
import com.android.learn.base.mmodel.TreeBean;
import com.android.learn.base.thirdframe.rxjava.BaseObserver;
import com.android.learn.base.view.CustomProgressDialog;
import com.android.learn.mcontract.KnowledgeContract;
import com.android.learn.mpresenter.KnowledgePresenter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.List;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class KnowledgeFragment extends BaseMvpFragment<KnowledgePresenter> implements KnowledgeContract.View {
    //    @BindView(R.id.hicvp)
    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    private HorizontalPagerAdapter mPagerAdapter;

    @Override
    public void initData(Bundle bundle) {
    }

    @Override
    public void initView(View view) {
        horizontalInfiniteCycleViewPager = view.findViewById(R.id.hicvp);


//        horizontalInfiniteCycleViewPager.setMaxPageScale(0.9f);
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public void reload() {
    }

    @Override
    public KnowledgePresenter initPresenter() {
        return new KnowledgePresenter();
    }

    @Override
    protected void loadData() {
        BaseObserver.createProgressDialog(getActivity());

        mPresenter.getKnowledge();
    }

    @Override
    public void showKnowledge(List<TreeBean> datas) {
        mPagerAdapter = new HorizontalPagerAdapter(getContext(), datas);
        horizontalInfiniteCycleViewPager.setAdapter(mPagerAdapter);
        if (horizontalInfiniteCycleViewPager != null && datas.size() > 1)
            horizontalInfiniteCycleViewPager.setCurrentItem(1);
    }
}

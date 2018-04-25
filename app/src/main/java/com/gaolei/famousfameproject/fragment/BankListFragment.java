package com.gaolei.famousfameproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gaolei.famousfameproject.R;
import com.gaolei.famousfameproject.mmodel.BankInfoResponse;
import com.gaolei.famousfameproject.mview.BankListView;
import com.gaolei.famousfameproject.net.client.BankListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 支持的银行卡列表
 * Created by caiwancheng on 2017/8/28.
 */

public class BankListFragment extends BaseMvpFragment<BankListView, BankListPresenter> implements BankListView {

    @BindView(R.id.rv_costom_recycler_view)
    RecyclerView mRvBank;
    TextView mTvName;

//    public BankListAdapter mBankListAdapter;

    public List<BankInfoResponse> mItemBeans = new ArrayList<>();

    private String mBankName;

    @Override
    public BankListPresenter initPresenter() {
        return new BankListPresenter();
    }


    @Override
    public void showLoading() {
        setLoading(true);
    }

    @Override
    public void hideLoading() {
        setLoading(false);
    }

    @Override
    public int setContentLayout() {
        return R.layout.costom_recyclerview;
    }

    @Override
    public void finishCreateView(Bundle bundle) {
        setWhiteStateBar();

        setTitle(getString(R.string.bank_account_select), true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRvBank.setLayoutManager(layoutManager);
//        mBankListAdapter = new BankListAdapter(R.layout.item_bank_list, mItemBeans);
        mPresenter.loadBankList(this);
//        mRvBank.setAdapter(mBankListAdapter);
//        View mHeadView = LayoutInflater.from(mContext).inflate(R.layout.bank_list_header, null);
//        mBankListAdapter.addHeaderView(mHeadView);
//        mTvName = ButterKnife.findById(mHeadView,R.id.tv_bank_select_title);
//        mTvName.setText(getString(R.string.cardholder , UserUtils.getInstance().getUser().getRealname()));
//        mBankListAdapter.addFooterView(LayoutInflater.from(mContext).inflate(R.layout.bank_list_footer, null));
//        mBankListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseRecycleViewAdapter adapter, View view, int position) {
//                Intent intent = new Intent();
//                intent.putExtra(ConstantUtils.KEY_DATA, mItemBeans.get(position));
//                getActivity().setResult(BankCardInfoFragment.sRequestCode, intent);
//                getActivity().finish();
//            }
//        });
    }

    @Override
    public void requstBankList(List<BankInfoResponse> itemBeans) {
        mItemBeans.addAll(itemBeans);
//        mBankListAdapter.notifyDataSetChanged();
    }


}

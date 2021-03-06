package com.framgia.forder.screen.searchshop;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.forder.R;
import com.framgia.forder.data.model.Shop;
import com.framgia.forder.screen.BaseRecyclerViewAdapter;
import com.framgia.forder.screen.shopDetail.ShopDetailFragment;
import com.framgia.forder.utils.navigator.Navigator;
import java.util.List;

/**
 * Exposes the data to be used in the Searchshop screen.
 */

public class ShopSearchResultViewModel extends BaseObservable
        implements ShopSearchResultContract.ViewModel,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> {

    private ShopSearchResultContract.Presenter mPresenter;
    private ShopSearchResultAdapter mAdapter;
    private Navigator mNavigator;
    private boolean mIsHaveData;

    ShopSearchResultViewModel(ShopSearchResultAdapter adapter, Navigator navigator) {
        mAdapter = adapter;
        mNavigator = navigator;
        mAdapter.setItemClickListener(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(ShopSearchResultContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ShopSearchResultAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onSearchSuccess(List<Shop> shops) {
        if (shops.size() == 0) {
            setHaveData(false);
            return;
        }
        setHaveData(true);
        mAdapter.updateData(shops);
    }

    @Override
    public void onItemRecyclerViewClick(Object item) {
        if (!(item instanceof Shop)) {
            return;
        }
        Shop shop = (Shop) item;
        mNavigator.goNextChildFragment(R.id.layout_content, ShopDetailFragment.newInstance(shop),
                true, Navigator.RIGHT_LEFT, "ShopDetailFragment");
    }

    @Bindable
    public boolean isHaveData() {
        return mIsHaveData;
    }

    public void setHaveData(boolean haveData) {
        mIsHaveData = haveData;
        notifyPropertyChanged(BR.haveData);
    }
}

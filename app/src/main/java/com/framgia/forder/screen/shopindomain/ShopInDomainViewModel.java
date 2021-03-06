package com.framgia.forder.screen.shopindomain;

import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import com.framgia.forder.BR;
import com.framgia.forder.R;
import com.framgia.forder.data.model.DomainManagement;
import com.framgia.forder.data.model.OwnerShop;
import com.framgia.forder.data.model.ShopInDomain;
import com.framgia.forder.data.source.remote.api.error.BaseException;
import com.framgia.forder.screen.requestshopindomain.RequestShopInDomainFragment;
import com.framgia.forder.utils.navigator.Navigator;
import com.framgia.forder.widgets.dialog.DialogManager;
import java.util.List;

/**
 * Exposes the data to be used in the Shopindomain screen.
 */

public class ShopInDomainViewModel extends BaseObservable
        implements ShopInDomainContract.ViewModel, ShopInDomainListener {

    private static final String TAG = "ManagerInShopFragment";
    private static final String MEMBER = "member";
    private static final String OWNER = "owner";

    private final ShopInDomainAdapter mAdapter;
    private final Navigator mNavigator;
    private final DomainManagement mDomainManagement;
    private final DialogManager mDialogManager;
    private ShopInDomainContract.Presenter mPresenter;
    private boolean mIsProgressBarListShopInDomain;
    private boolean mIsHaveData;

    ShopInDomainViewModel(ShopInDomainAdapter shopInDomainAdapter, Navigator navigator,
            DomainManagement domainManagement, DialogManager dialogManager) {
        mAdapter = shopInDomainAdapter;
        mNavigator = navigator;
        mDomainManagement = domainManagement;
        mDialogManager = dialogManager;
        shopInDomainAdapter.setShopInDomainListener(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
        mPresenter.getListShopInDomain(mDomainManagement.getId());
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(ShopInDomainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ShopInDomainAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onGetListShopInDomainSuccess(List<ShopInDomain> shops) {
        if (shops.size() == 0) {
            setHaveData(false);
            return;
        }
        setHaveData(true);
        mAdapter.updateData(shops, OWNER.equals(mDomainManagement.getRoleOfCurrentUser()));
    }

    @Override
    public void onGetListShopInDomainError(BaseException error) {
        setHaveData(false);
        Log.e(TAG, "onGetListShopInDomainError: ", error);
    }

    @Override
    public void ondeleteShopInDomainSuccess() {
        mPresenter.getListShopInDomain(mDomainManagement.getId());
    }

    @Override
    public void ondeleteShopInDomainError(BaseException error) {
        Log.e(TAG, "onGetListShopInDomainError: ", error);
    }

    @Override
    public void onClickSeeAllManager(List<OwnerShop> ownerShops) {
        mNavigator.showManagerInShopDialog(TAG, ownerShops);
    }

    @Override
    public void onClickDeleteShop(final ShopInDomain shop) {
        mDialogManager.dialogwithNoTitleTwoButton(R.string.msg_delete_domain,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteShop(mDomainManagement.getId(), shop.getId());
                    }
                });
        mDialogManager.show();
    }

    @Override
    public void onShowProgressDialog() {
        mDialogManager.showProgressDialog();
    }

    @Override
    public void onHideProgressDialog() {
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void onShowProgressBar() {
        setProgressBarListShopInDomain(true);
    }

    @Override
    public void onHideProgressBar() {
        setProgressBarListShopInDomain(false);
    }

    @Bindable
    public boolean isProgressBarListShopInDomain() {
        return mIsProgressBarListShopInDomain;
    }

    public void setProgressBarListShopInDomain(boolean progressBarListShopInDomain) {
        mIsProgressBarListShopInDomain = progressBarListShopInDomain;
        notifyPropertyChanged(BR.progressBarListShopInDomain);
    }

    public void onClickAddShopInDomain() {
        mNavigator.goNextChildFragment(R.id.layout_content,
                RequestShopInDomainFragment.newInstance(mDomainManagement), true,
                Navigator.RIGHT_LEFT, "RequestShopInDomainFragment");
    }

    public boolean isMember() {
        return MEMBER.equals(mDomainManagement.getRoleOfCurrentUser());
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

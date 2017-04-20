package com.framgia.forder.screen.listProduct;

import com.framgia.forder.data.model.Domain;
import com.framgia.forder.data.model.Product;
import com.framgia.forder.data.source.DomainRepository;
import com.framgia.forder.data.source.ProductRepository;
import com.framgia.forder.data.source.remote.api.error.BaseException;
import com.framgia.forder.data.source.remote.api.error.SafetyError;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link ListProductFragment}), retrieves the data and updates
 * the UI as required.
 */
final class ListProductPresenter implements ListProductContract.Presenter {
    private static final String TAG = ListProductPresenter.class.getName();

    private final ListProductContract.ViewModel mViewModel;
    private final CompositeSubscription mCompositeSubscription;
    private ProductRepository mProductRepository;
    private DomainRepository mDomainRepository;

    public ListProductPresenter(ListProductContract.ViewModel viewModel,
            ProductRepository productRepository, DomainRepository domainRepository) {
        mViewModel = viewModel;
        mProductRepository = productRepository;
        mDomainRepository = domainRepository;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {
        mProductRepository.openTransaction();
    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
        mProductRepository.closeTransaction();
    }

    @Override
    public void addToCart(Product product) {
        if (product == null) {
            return;
        }
        Subscription subscription =
                mProductRepository.addToCart(product).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mViewModel.onAddToCartSuccess();
                    }
                }, new SafetyError() {
                    @Override
                    public void onSafetyError(BaseException error) {
                        mViewModel.onAddToCartError(error);
                    }
                });
        mCompositeSubscription.add(subscription);
        mCompositeSubscription.clear();
    }

    @Override
    public void getListAllProduct() {
        Domain domain = mDomainRepository.getCurrentDomain();
        if (domain == null) {
            return;
        }
        Subscription subscription = mProductRepository.getListProduct(domain.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Product>>() {
                    @Override
                    public void call(List<Product> products) {
                        mViewModel.onGetListAllProductSuccess(products);
                    }
                }, new SafetyError() {
                    @Override
                    public void onSafetyError(BaseException error) {
                        mViewModel.onGetListAllProductError(error);
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
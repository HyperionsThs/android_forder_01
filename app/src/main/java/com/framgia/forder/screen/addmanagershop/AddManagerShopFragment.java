package com.framgia.forder.screen.addmanagershop;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.forder.R;
import com.framgia.forder.databinding.FragmentAddManagerShopBinding;

/**
 * Addmanagershop Screen.
 */
public class AddManagerShopFragment extends Fragment {

    private AddManagerShopContract.ViewModel mViewModel;

    public static AddManagerShopFragment newInstance() {
        return new AddManagerShopFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new AddManagerShopViewModel();

        AddManagerShopContract.Presenter presenter = new AddManagerShopPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentAddManagerShopBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_add_manager_shop, container,
                        false);
        binding.setViewModel((AddManagerShopViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
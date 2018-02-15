package com.mlsdev.mlsdevstore.presentaion.bottom_navigation;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityMainBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.account.AccountFragment;
import com.mlsdev.mlsdevstore.presentaion.cart.CartFragment;
import com.mlsdev.mlsdevstore.presentaion.store.StoreFragment;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private BottomNavigationController navigationController;
    private BottomNavigationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BottomNavigationViewModel.class);
        binding.setBottomViewModel(viewModel);
        getLifecycle().addObserver(viewModel);
        initBottomNavigation();
    }

    @Override
    protected void onDestroy() {
        getLifecycle().removeObserver(viewModel);
        super.onDestroy();
    }

    @Override
    public void setToolBarTitle(int title) {
        binding.setTitle(getString(title));
    }

    public void selectTab(@IdRes int tabId) {
        binding.bottomBar.selectTabWithId(tabId);
    }

    private void initBottomNavigation() {
        navigationController = new BottomNavigationController();
        binding.bottomBar.setOnTabSelectListener(navigationController);
        binding.bottomBar.setOnTabReselectListener(navigationController);
    }

    public class BottomNavigationController implements
            OnTabSelectListener,
            OnTabReselectListener {

        @Override
        public void onTabReSelected(int tabId) {

        }

        @Override
        public void onTabSelected(int tabId) {
            Fragment fragment;

            switch (tabId) {
                case R.id.navigation_item_cart:
                    fragment = new CartFragment();
                    break;
                case R.id.navigation_item_account:
                    fragment = new AccountFragment();
                    break;
                default:
                    fragment = new StoreFragment();
                    break;
            }

            replaceFragment(fragment);
        }

        private void replaceFragment(Fragment fragment) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content, fragment)
                    .commit();
        }

    }
}

package com.mlsdev.mlsdevstore.presentaion.account

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.FragmentAccountBinding
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment

class AccountFragment : BaseFragment() {
    private var binding: FragmentAccountBinding? = null
    private var accountViewModel: AccountViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        accountViewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)
        binding!!.viewModel = accountViewModel
        lifecycle.addObserver(accountViewModel!!)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(accountViewModel!!)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.navigation_title_account)
    }

}

package com.mlsdev.mlsdevstore.presentaion.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.FragmentAccountBinding
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment

class AccountFragment : BaseFragment() {
    lateinit var binding: FragmentAccountBinding
    lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        accountViewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)
        binding.viewModel = accountViewModel
        lifecycle.addObserver(accountViewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(binding.root)
        binding.buttonEditPersonalInfo.setOnClickListener {
            navController.navigate(R.id.edit_personal_info_activity)
        }

        binding.buttonEditShippingInfo.setOnClickListener {
            navController.navigate(R.id.edit_shipping_info_activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(accountViewModel)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.navigation_title_account)
    }

}

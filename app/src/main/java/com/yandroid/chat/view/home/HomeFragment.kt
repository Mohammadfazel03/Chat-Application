package com.yandroid.chat.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yandroid.chat.R
import com.yandroid.chat.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.settingPreferences.observe(viewLifecycleOwner) {
            if (it.token.isEmpty()) {
                findNavController().navigate(R.id.action_homeFragment_to_auth_graph)
            }
        }

        homeViewModel.connectionState.observe(viewLifecycleOwner) {
            binding.toolbar.subtitle = it
        }

    }
}

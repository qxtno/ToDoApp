package com.example.todoapp.ui.items

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.fabAdd.setOnClickListener {
            viewModel.addItemClick()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect {
                when (it) {
                    is MainFragmentViewModel.MainFragmentEvents.NavigateToAddEditScreen -> {
                        val navigateToAddEditScreenAction =
                            MainFragmentDirections.actionMainFragmentToAddEditFragment(
                                null,
                                resources.getString(R.string.add)
                            )
                        findNavController().navigate(navigateToAddEditScreenAction)
                    }
                }
            }
        }
    }
}
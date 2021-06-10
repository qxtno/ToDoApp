package com.example.todoapp.ui.items

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.database.model.Item
import com.example.todoapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ItemAdapter.OnItemClickListener {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setHasOptionsMenu(true)

        val itemAdapter = ItemAdapter(this)
        binding.recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        viewModel.items.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noItemsTextView.visibility = View.VISIBLE
            } else {
                binding.noItemsTextView.visibility = View.GONE
            }
            itemAdapter.submitList(it)
        }

        binding.fabAdd.setOnClickListener {
            viewModel.addItemClick()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect {
                when (it) {
                    is MainFragmentViewModel.MainFragmentEvents.NavigateToAddEditScreen -> {
                        var title = resources.getString(R.string.edit)
                        if (it.newItem) {
                            title = resources.getString(R.string.add)
                        }
                        val navigateToAddEditScreenAction =
                            MainFragmentDirections.actionMainFragmentToAddEditFragment(
                                it.item,
                                title
                            )
                        findNavController().navigate(navigateToAddEditScreenAction)
                    }
                    is MainFragmentViewModel.MainFragmentEvents.NavigateToDeleteAllDialog -> {
                        val navigateToDeleteAllDialog =
                            MainFragmentDirections.actionMainFragmentToDeleteAllDialog()
                        findNavController().navigate(navigateToDeleteAllDialog)
                    }
                }
            }
        }
    }

    override fun onItemClick(item: Item) {
        viewModel.onItemClick(item)
    }

    override fun onItemCheckedClick(item: Item, isChecked: Boolean) {
        viewModel.onItemChecked(item, isChecked)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_completed -> {
                lifecycleScope.launch {
                    viewModel.onDeleteAllClick()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
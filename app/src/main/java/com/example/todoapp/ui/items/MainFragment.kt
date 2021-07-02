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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.model.Item
import com.example.todoapp.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
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

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = itemAdapter.currentList[viewHolder.adapterPosition]
                viewModel.onItemSwiped(item)
            }
        }).attachToRecyclerView(binding.recyclerView)


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is MainFragmentViewModel.MainFragmentEvents.NavigateToAddEditScreen -> {
                        val navigateToAddEditScreenAction =
                            MainFragmentDirections.actionMainFragmentToAddEditFragment(
                                event.item
                            )
                        findNavController().navigate(navigateToAddEditScreenAction)
                    }
                    is MainFragmentViewModel.MainFragmentEvents.NavigateToDeleteAllDialog -> {
                        val navigateToDeleteAllDialog =
                            MainFragmentDirections.actionMainFragmentToDeleteAllDialog()
                        findNavController().navigate(navigateToDeleteAllDialog)
                    }
                    is MainFragmentViewModel.MainFragmentEvents.ShowCannotDeleteMessage -> {
                        val snackBar = Snackbar.make(
                            requireView(),
                            resources.getString(R.string.cannot_delete_message),
                            Snackbar.LENGTH_SHORT
                        )
                        snackBar.anchorView = binding.fabAdd
                        snackBar.show()
                    }
                    is MainFragmentViewModel.MainFragmentEvents.ShowUndoDeleteMessage -> {
                        val snackBar = Snackbar.make(
                            requireView(),
                            resources.getString(R.string.deleted),
                            Snackbar.LENGTH_LONG
                        )
                        snackBar.setAction(resources.getString(R.string.undo)) {
                            viewModel.onUndoDelete(event.item)
                        }
                        snackBar.anchorView = binding.fabAdd
                        snackBar.show()
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
            R.id.action_navigate_to_settings -> {
                val navigateToSettingsScreen =
                    MainFragmentDirections.actionMainFragmentToSettingsFragment()
                findNavController().navigate(navigateToSettingsScreen)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
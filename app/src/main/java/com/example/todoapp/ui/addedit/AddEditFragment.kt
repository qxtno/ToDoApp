package com.example.todoapp.ui.addedit

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddeditBinding
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.utils.models.Date
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditFragment : Fragment(R.layout.fragment_addedit) {
    private lateinit var binding: FragmentAddeditBinding
    private val viewModel: AddEditFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddeditBinding.bind(view)
        setHasOptionsMenu(true)
        val args: AddEditFragmentArgs by navArgs()
        (activity as MainActivity).supportActionBar?.title = args.title

        binding.apply {
            categorySelector.setText(viewModel.categoryString)
            categorySelector.addTextChangedListener {
                viewModel.categoryString = it.toString()
            }

            val categoriesAdapter =
                ArrayAdapter(requireActivity(), R.layout.array_adapter_item, viewModel.categories)
            categorySelector.setAdapter(categoriesAdapter)

            nameEditText.setText(viewModel.nameString)
            nameEditText.addTextChangedListener {
                viewModel.nameString = it.toString()
                viewModel.isNameCorrect()
            }

            dateSelectorEditText.setText(viewModel.dateString)
            dateSelectorEditText.addTextChangedListener {
                viewModel.dateString = it.toString()
            }

            dateSelectorEditText.setOnClickListener {
                viewModel.onDateSelectorClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect {
                when (it) {
                    is AddEditFragmentViewModel.AddEditFragmentEvents.NavigateToDatePickerDialog -> {
                        val navigateToDatePickerDialog =
                            AddEditFragmentDirections.actionAddEditFragmentToDatePickerDialog(it.date)
                        findNavController().navigate(navigateToDatePickerDialog)
                    }
                    is AddEditFragmentViewModel.AddEditFragmentEvents.ShowSavedMessage -> {
                        (activity as MainActivity).onBackPressed()
                        Snackbar.make(
                            requireView(), resources.getString(R.string.saved),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is AddEditFragmentViewModel.AddEditFragmentEvents.CancelEditing -> {
                        (activity as MainActivity).onBackPressed()
                    }
                    is AddEditFragmentViewModel.AddEditFragmentEvents.ShowNameError -> {
                        binding.addeditNameEditText.error = resources.getString(R.string.name_error)
                        if (!it.correctValue) {
                            binding.addeditNameEditText.isErrorEnabled = false
                        }
                    }
                    is AddEditFragmentViewModel.AddEditFragmentEvents.ShowInsertError -> {
                        val navigateToInsertErrorDialog =
                            AddEditFragmentDirections.actionAddEditFragmentToInsertErrorDialog(it.message)
                        findNavController().navigate(navigateToInsertErrorDialog)
                    }
                }
            }
        }

        observeDataFromDialog()
    }

    private fun observeDataFromDialog() {
        val navController = findNavController()
        val navBackStackEntry = navController.getBackStackEntry(R.id.addEditFragment)

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME
                && navBackStackEntry.savedStateHandle.contains("date")
            ) {
                val result = navBackStackEntry.savedStateHandle.get<Date>("date")
                if (result != null) {
                    val date =
                        String.format("%02d.%02d.%04d", result.day, result.month, result.year)
                    binding.dateSelectorEditText.setText(date)
                }
            }
            if (event == Lifecycle.Event.ON_RESUME
                && navBackStackEntry.savedStateHandle.contains("retry")
            ) {
                val result = navBackStackEntry.savedStateHandle.get<Boolean>("retry") ?: false
                if (result) {
                    viewModel.onSaveClick()
                }
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                lifecycleScope.launch {
                    viewModel.onSaveClick()
                }
            }
            R.id.action_cancel -> {
                lifecycleScope.launch {
                    viewModel.onCancelClick()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
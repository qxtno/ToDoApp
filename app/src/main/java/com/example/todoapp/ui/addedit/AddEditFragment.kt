package com.example.todoapp.ui.addedit

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
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
import com.example.todoapp.utils.CategoryConstants
import com.example.todoapp.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditFragment : Fragment(R.layout.fragment_addedit) {
    private lateinit var binding: FragmentAddeditBinding
    private val viewModel: AddEditFragmentViewModel by viewModels()
    private lateinit var categoryAutoCompleteTextView: AutoCompleteTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddeditBinding.bind(view)
        setHasOptionsMenu(true)
        val args: AddEditFragmentArgs by navArgs()
        val utils = DateUtils()

        setTitle(args)

        binding.apply {
            categoryAutoCompleteTextView = categorySelector

            setCategoryValue(args)
            val categories = arrayOf(
                resources.getString(R.string.work),
                resources.getString(R.string.shopping),
                resources.getString(R.string.other)
            )

            val categoriesAdapter =
                ArrayAdapter(requireActivity(), R.layout.array_adapter_item, categories)
            categorySelector.setAdapter(categoriesAdapter)

            categorySelector.setOnItemClickListener { _, _, position, _ ->
                categorySelector.setAdapter(categoriesAdapter)
                viewModel.categoryInt = position
            }

            nameEditText.setText(viewModel.nameString)
            nameEditText.addTextChangedListener {
                viewModel.nameString = it.toString()
                viewModel.isNameCorrect()
            }


            dateSelectorEditText.setText(utils.formatDateToString(viewModel.dateLong))
            dateSelectorEditText.addTextChangedListener {
                viewModel.dateLong = utils.formatDateToLong(it.toString())
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
                            AddEditFragmentDirections.actionAddEditFragmentToDatePickerDialog()
                        findNavController().navigate(navigateToDatePickerDialog)
                    }
                    is AddEditFragmentViewModel.AddEditFragmentEvents.ShowInsertError -> {
                        val navigateToInsertErrorDialog =
                            AddEditFragmentDirections.actionAddEditFragmentToInsertErrorDialog(it.message)
                        findNavController().navigate(navigateToInsertErrorDialog)
                    }
                    is AddEditFragmentViewModel.AddEditFragmentEvents.ShowNameError -> {
                        binding.addeditNameEditText.error = resources.getString(R.string.name_error)
                        binding.addeditNameEditText.isErrorEnabled = it.correctValue
                    }
                    AddEditFragmentViewModel.AddEditFragmentEvents.ShowSavedMessage -> {
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.saved),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
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
                val result = navBackStackEntry.savedStateHandle.get<String>("date")
                if (result != null) {
                    binding.dateSelectorEditText.setText(result)
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

    private fun setCategoryValue(args: AddEditFragmentArgs) {
        when (args.item?.category ?: 0) {
            CategoryConstants.work -> {
                categoryAutoCompleteTextView.setText(resources.getString(R.string.work))
            }
            CategoryConstants.shopping -> {
                categoryAutoCompleteTextView.setText(resources.getString(R.string.shopping))
            }
            else -> {
                categoryAutoCompleteTextView.setText(resources.getString(R.string.other))
            }
        }
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setTitle(args: AddEditFragmentArgs) {
        (activity as MainActivity).supportActionBar?.title = resources.getString(R.string.add)
        if (args.item != null) {
            (activity as MainActivity).supportActionBar?.title = resources.getString(R.string.edit)
        }
    }
}
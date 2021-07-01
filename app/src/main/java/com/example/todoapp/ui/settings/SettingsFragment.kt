package com.example.todoapp.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.example.todoapp.utils.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding.apply {
            viewModel.themeFlow.observe(viewLifecycleOwner) {
                when (it) {
                    Theme.DARK -> {
                        themeTextView.text = resources.getString(R.string.dark)
                    }
                    Theme.LIGHT -> {
                        themeTextView.text = resources.getString(R.string.light)
                    }
                    else -> {
                        themeTextView.text = resources.getString(R.string.auto)
                    }
                }
            }
            themeTextView.setOnClickListener {
                val popupMenu = PopupMenu(it.context, it)
                popupMenu.inflate(R.menu.popup_menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.popup_auto -> {
                            viewModel.updateTheme(Theme.AUTO)
                        }
                        R.id.popup_dark -> {
                            viewModel.updateTheme(Theme.DARK)
                        }
                        R.id.popup_light -> {
                            viewModel.updateTheme(Theme.LIGHT)
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }
}
package com.pm.developextest.main.settings

import android.app.Dialog
import android.databinding.DataBindingUtil.*
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater.*
import com.pm.developextest.R
import com.pm.developextest.databinding.FrMainSettingsBinding
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class MainSettingsDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var model: MainSettingsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dataBinding = inflate<FrMainSettingsBinding>(
            from(requireContext()), R.layout.fr_main_settings, null, false
        )

        model.init()

        dataBinding.model = model

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.settings)
            .setView(dataBinding.root)
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .create()


        dialog.setOnShowListener { _ ->
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (model.ok()) {
                    dismiss()
                }
            }

        }

        return dialog
    }
}

package com.pm.developextest.main.settings

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.*
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import com.pm.developextest.R
import com.pm.developextest.databinding.FrMainSettingsBinding
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class MainSettingsDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var model: MainSettingsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dataBinding = inflate<FrMainSettingsBinding>(
            from(requireContext()), R.layout.fr_main_settings, null, false)

        model.init()

        dataBinding.model = model

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.settings)
            .setView(dataBinding.root)
            .setNegativeButton(android.R.string.cancel)
            .setPositiveButton(android.R.string.ok)
            .create()


        dialog.setOnShowListener {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
               if (model.ok()) {
                   dismiss()
               }
            }

        }

        return dialog
    }
}

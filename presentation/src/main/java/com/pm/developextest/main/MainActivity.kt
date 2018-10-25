package com.pm.developextest.main

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.Menu
import com.pm.developextest.R
import com.pm.developextest.databinding.AcMainBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.ac_main.*
import javax.inject.Inject
import android.view.MenuItem
import com.pm.developextest.main.settings.MainSettingsDialogFragment


class MainActivity : DaggerAppCompatActivity() {

    lateinit var dataBinding: AcMainBinding

    @Inject
    lateinit var model: MainViewModel

    @Inject
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.ac_main)
        dataBinding.model = model
        rvUrls.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        rvUrls.adapter = adapter
        model.items.observe(this, Observer { adapter.submitList(it) })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.settings -> {
                MainSettingsDialogFragment().show(supportFragmentManager, "dialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

}

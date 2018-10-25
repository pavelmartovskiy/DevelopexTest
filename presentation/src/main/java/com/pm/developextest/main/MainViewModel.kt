package com.pm.developextest.main

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import com.pm.developextest.BR
import com.pm.developextest.core.AbsViewModel
import com.pm.developextest.data.UrlExplorer
import com.pm.developextest.data.UrlExplorerEngineState
import com.pm.developextest.domain.engine.UrlInfo
import com.pm.developextest.domain.engine.isFinalized

class MainViewModel(private val explorer: UrlExplorer) : AbsViewModel() {

    val items = MutableLiveData<List<Item>>()
    val engineState
        @Bindable
        get() = explorer.state.value ?: UrlExplorerEngineState.Idle

    var total: Int = 0
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.total)
            notifyPropertyChanged(BR.info)
        }

    var processed: Int = 0
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.processed)
            notifyPropertyChanged(BR.info)
        }

    val info: String
        @Bindable
        get() = "$processed / $total"


    init {
        explorer.urls.observeForever(::setTotalProcessed)
        explorer.state.observeForever(::setUrlExplorerState)
    }

    private fun setTotalProcessed( urls: List<UrlInfo>?) {
        items.postValue(urls?.mapToItems() ?: emptyList())
        total = urls?.size ?: 0
        processed = urls
            ?.asSequence()
            ?.filter { info -> info.isFinalized() }
            ?.count() ?: 0
    }

    private fun setUrlExplorerState(engineState: UrlExplorerEngineState?) {
        notifyPropertyChanged(BR.engineState)
    }


    fun start() {
        explorer.start()
    }

    fun stop() {
        explorer.stop()
    }

    fun clear() {
        explorer.clear()
    }

    override fun onCleared() {
        super.onCleared()
        explorer.urls.removeObserver(::setTotalProcessed)
        explorer.state.removeObserver(::setUrlExplorerState)
    }

}




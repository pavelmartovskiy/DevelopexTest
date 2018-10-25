package com.pm.developextest.main.settings

import android.databinding.Bindable
import android.text.TextUtils
import android.webkit.URLUtil
import com.pm.developextest.BR
import com.pm.developextest.R
import com.pm.developextest.core.AbsViewModel
import com.pm.developextest.data.UrlExplorer

class MainSettingsViewModel(private val explorer: UrlExplorer) : AbsViewModel() {

    var url: String = "https://uk.wikipedia.org"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.url)
            urlError = null
        }

    var urlError: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.urlError)
        }

    var searchText: String = "Test"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.searchText)
            searchTextError = null
        }

    var searchTextError: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.searchTextError)
        }

    var maxThreadNumber: String = "10"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxThreadNumber)
            maxThreadNumberError = null
        }

    var maxThreadNumberError: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxThreadNumberError)

        }

    var maxUrlNumber: String = "100"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxUrlNumber)
            maxUrlNumberError = null
        }

    var maxUrlNumberError: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxUrlNumberError)
        }

    fun ok() : Boolean {

        if(!(URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url))) {
            urlError = R.string.err_invalid_url
        }

        if(TextUtils.isEmpty(searchText)) {
            searchTextError = R.string.err_cant_be_empty
        }

        if(TextUtils.isEmpty(maxThreadNumber) || maxThreadNumber.toInt() == 0) {
            maxThreadNumberError = R.string.err_cant_be_zero_or_null
        }

        if(TextUtils.isEmpty(maxUrlNumber) || maxUrlNumber.toInt() == 0) {
            maxUrlNumberError = R.string.err_cant_be_zero_or_null
        }

        val noError = (urlError == null
                && maxUrlNumberError == null
                && searchTextError == null
                && maxThreadNumberError == null)


        if (noError) {
            explorer.setUp(
                url,
                searchText,
                maxThreadNumber.toLong(),
                maxUrlNumber.toLong()
            )
        }

        return noError
    }

    fun init() {
        url = explorer.getUrl()
        searchText = explorer.getSearchText()
        maxUrlNumber = explorer.getMaxUrls().toString()
        maxThreadNumber = explorer.getMaxThreads().toString()
    }
}
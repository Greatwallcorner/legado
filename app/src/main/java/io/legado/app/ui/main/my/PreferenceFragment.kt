package io.legado.app.ui.main.my

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.legado.app.App
import io.legado.app.R
import io.legado.app.lib.theme.ATH
import io.legado.app.lib.theme.ThemeStore
import io.legado.app.ui.about.AboutActivity
import io.legado.app.ui.about.DonateActivity
import io.legado.app.ui.booksource.BookSourceActivity
import io.legado.app.ui.config.ConfigActivity
import io.legado.app.ui.config.ConfigViewModel
import org.jetbrains.anko.startActivity

class PreferenceFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_main)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ATH.applyEdgeEffectColor(listView)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "isNightTheme" -> {
                App.INSTANCE.applyDayNight()
            }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        preference?.let {
            when (preference.key) {
                "bookSourceManage" -> context?.startActivity<BookSourceActivity>()
                "setting" -> context?.startActivity<ConfigActivity>(
                        Pair("configType", ConfigViewModel.TYPE_CONFIG)
                )
                "web_dav_setting" -> context?.startActivity<ConfigActivity>(
                        Pair("configType", ConfigViewModel.TYPE_WEB_DAV_CONFIG)
                )
                "theme_setting" -> context?.startActivity<ConfigActivity>(
                        Pair("configType", ConfigViewModel.TYPE_THEME_CONFIG)
                )
                "donate" -> context?.startActivity<DonateActivity>()
                "about" -> context?.startActivity<AboutActivity>()
                else -> null
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

}
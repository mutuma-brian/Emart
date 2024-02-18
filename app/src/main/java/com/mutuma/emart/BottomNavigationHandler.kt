package com.mutuma.emart


import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavigationHandler {

    private const val SHARED_PREF_NAME = "bottom_nav_prefs"
    private const val SELECTED_ITEM_ID_KEY = "selected_item_id"

    fun setupBottomNavigation(context: Context, bottomNavigationView: BottomNavigationView) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val selectedItemId = sharedPreferences.getInt(SELECTED_ITEM_ID_KEY, R.id.nav_home)

        bottomNavigationView.selectedItemId = selectedItemId

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home, R.id.nav_favorites, R.id.nav_cart, R.id.nav_account -> {
                    sharedPreferences.edit().putInt(SELECTED_ITEM_ID_KEY, item.itemId).apply()
                    handleNavigation(context, item.itemId)

                    true
                }
                else -> false
            }
        }
    }

    private fun handleNavigation(context: Context, itemId: Int) {
        when (itemId) {
            R.id.nav_home -> openActivity(context, HomeActivity::class.java)
            R.id.nav_favorites -> openActivity(context, FavouritesActivity::class.java)
            R.id.nav_cart -> openActivity(context, CartActivity::class.java)
            R.id.nav_account -> openActivity(context, AccountActivity::class.java)
        }
    }

    private fun openActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }
}

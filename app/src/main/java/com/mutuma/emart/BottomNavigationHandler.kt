package com.mutuma.emart

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavigationHandler {

    fun setupBottomNavigation(context: Context, bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (context !is HomeActivity) {
                        openActivity(context, HomeActivity::class.java)
                    }
                    true
                }
                R.id.nav_favorites -> {
                    if (context !is FavouritesActivity) {
                        openActivity(context, FavouritesActivity::class.java)
                    }
                    true
                }
                R.id.nav_cart -> {
                    if (context !is CartActivity) {
                        openActivity(context, CartActivity::class.java)
                    }
                    true
                }
                R.id.nav_account -> {
                    if (context !is AccountActivity) {
                        openActivity(context, AccountActivity::class.java)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun openActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }
}

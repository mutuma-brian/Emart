package com.mutuma.emart

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavigationHandler {

    fun setupBottomNavigation(context: Context, bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    openActivity(context, HomeActivity::class.java)
                    true
                }
                R.id.nav_favorites -> {
                    openActivity(context, FavouritesActivity::class.java)
                    true
                }
                R.id.nav_cart -> {
                    openActivity(context, CartActivity::class.java)
                    true
                }
                R.id.nav_account -> {
                    openActivity(context, AccountActivity::class.java)
                    true
                }
                else -> false
            }
        }
    }

    private fun openActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

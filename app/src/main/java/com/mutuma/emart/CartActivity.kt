package com.mutuma.emart

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView


class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var productCartList: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHandler.setupBottomNavigation(this@CartActivity, bottomNavigationView)

        productCartList = mutableListOf()
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        cartRecyclerView.adapter = CartRecyclerViewAdapter( getSelectedProductsFromSharedPreferences())
        cartRecyclerView.layoutManager = LinearLayoutManager(this)


    }
    private fun getSelectedProductsFromSharedPreferences(): MutableList<Product> {
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

            val selectedProductName = sharedPreferences.getString("productName", null)
            val selectedProductDescription = sharedPreferences.getString("productDescription", null)
            val selectedProductPrice = sharedPreferences.getString("productPrice", null)
            val selectedProductImageUrl = sharedPreferences.getString("productImageUrl", null)

            val selectedProduct = Product(selectedProductImageUrl, selectedProductName, selectedProductPrice, selectedProductDescription)

            if (selectedProduct != null) {
                productCartList.add(selectedProduct)
            }

            return productCartList
        }
}




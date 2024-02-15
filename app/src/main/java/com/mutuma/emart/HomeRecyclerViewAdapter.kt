package com.mutuma.emart

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HomeRecyclerViewAdapter(var productList: MutableList<Product>, private val context: Context) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ProductViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productNameTextView: TextView = itemView.findViewById(R.id.productName)
        var productDescriptionTextView: TextView = itemView.findViewById(R.id.productDescription)
        var productPriceTextView: TextView = itemView.findViewById(R.id.productPrice)
        var productImageView: ImageView = itemView.findViewById(R.id.productImage)
        var buttonAddToCart: Button = itemView.findViewById(R.id.buttonAddToCart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_recyclerview, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productNameTextView.text = productList[position].productName
        holder.productDescriptionTextView.text = productList[position].productDescription
        holder.productPriceTextView.text = "KSH ${productList[position].productPrice}"
        Glide.with(holder.productImageView)
            .load(productList[position].productImageUrl)
            .into(holder.productImageView)
        holder.buttonAddToCart.setOnClickListener {
            saveSelectedProduct(productList[position])
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    private fun saveSelectedProduct(product: Product) {
        val editor = sharedPreferences.edit()
        editor.putString("productName", product.productName)
        editor.putString("productDescription", product.productDescription)
        editor.putString("productPrice", product.productPrice)
        editor.putString("productImageUrl", product.productImageUrl)
        editor.apply()
    }
}

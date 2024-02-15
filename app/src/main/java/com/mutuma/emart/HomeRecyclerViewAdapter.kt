package com.mutuma.emart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HomeRecyclerViewAdapter(var productList: MutableList<Product>) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productNameTextView: TextView = itemView.findViewById(R.id.productName)
        var productDescriptionTextView: TextView = itemView.findViewById(R.id.productDescription)
        var productPriceTextView: TextView = itemView.findViewById(R.id.productPrice)
        var productImageView: ImageView = itemView.findViewById(R.id.productImage)
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
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}

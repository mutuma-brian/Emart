package com.mutuma.emart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartRecyclerViewAdapter(var productList: MutableList<Product>) : RecyclerView.Adapter<CartRecyclerViewAdapter.ProductViewHolder>() {


    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productNameTextView: TextView = itemView.findViewById(R.id.productCartName)
        var productDescriptionTextView: TextView = itemView.findViewById(R.id.productCartDescription)
        var productPriceTextView: TextView = itemView.findViewById(R.id.productCartPrice)
        var productImageView: ImageView = itemView.findViewById(R.id.productCartImage)
        var productCartAdd: Button = itemView.findViewById(R.id.buttonAdd)
        var productCartSubtract: Button = itemView.findViewById(R.id.buttonSubtract)
        var productAmountTextView: TextView = itemView.findViewById(R.id.productAmountTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_recyclerview, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productNameTextView.text = productList[position].productName
        holder.productDescriptionTextView.text = productList[position].productDescription
        holder.productPriceTextView.text = "KSH ${productList[position].productPrice}"
        Glide.with(holder.productImageView)
            .load(productList[position].productImageUrl)
            .into(holder.productImageView)
        holder.productCartAdd.setOnClickListener {
            var amount = holder.productAmountTextView.text.toString().toInt()
            amount += 1
            holder.productAmountTextView.text = amount.toString()
        }
        holder.productCartSubtract.setOnClickListener {
            var amount = holder.productAmountTextView.text.toString().toInt()
            if(amount>0) {
                amount -= 1
            }
            holder.productAmountTextView.text = amount.toString()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}

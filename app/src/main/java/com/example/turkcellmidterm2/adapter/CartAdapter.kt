package com.example.turkcellmidterm2.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.turkcellmidterm2.R
import com.example.turkcellmidterm2.models.Product
import com.example.turkcellmidterm2.models.ProductCart

class CartAdapter(private val context : Activity, private val list: MutableList<ProductCart>) : ArrayAdapter<ProductCart>(context,
    R.layout.product_list_item,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rootView = context.layoutInflater.inflate(R.layout.product_list_item,null,true)

        val productTitleText = rootView.findViewById<TextView>(R.id.productTitleList)
        val productPriceText = rootView.findViewById<TextView>(R.id.productPriceList)
        val productImageView = rootView.findViewById<ImageView>(R.id.productImageList)

        val product = list.get(position)
        productTitleText.text = product.title
        productPriceText.text = product.price.toString() + "$"
        Glide.with(rootView).load("https://fomantic-ui.com/images/wireframe/image.png").into(productImageView)
        return rootView
    }
}

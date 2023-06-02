package com.example.turkcellmidterm2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.turkcellmidterm2.adapter.CartAdapter
import com.example.turkcellmidterm2.adapter.ProductAdapter
import com.example.turkcellmidterm2.models.Cart
import com.example.turkcellmidterm2.models.ProductCart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    var cartList = mutableListOf<ProductCart>()
    lateinit var cartListView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartListView = findViewById(R.id.cartList)
        getCart()
    }

    fun getCart(){
        MainActivity.dummyService.getCarts().enqueue(object : Callback<Cart>{
            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                val data = response.body()
                if (data != null) {
                    cartList.addAll(data.products)
                    val adapter = CartAdapter(this@CartActivity, cartList)
                    cartListView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
            }
        })
    }
}

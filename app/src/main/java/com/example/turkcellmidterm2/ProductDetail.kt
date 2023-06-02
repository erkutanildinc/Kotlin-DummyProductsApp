package com.example.turkcellmidterm2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.turkcellmidterm2.models.Cart
import com.example.turkcellmidterm2.models.OrderRequest
import com.example.turkcellmidterm2.models.ProductRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetail : AppCompatActivity() {

    lateinit var productTitleText : TextView
    lateinit var productRatingText : TextView
    lateinit var productDescriptionText : TextView
    lateinit var productPriceText : TextView
    lateinit var productImgView : ImageView
    lateinit var addToCartBtn : Button
    var productId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        var productTitle = intent.getStringExtra("title")
        var productPrice = intent.getStringExtra("price")
        var productRating = intent.getStringExtra("rating")
        var productDescription = intent.getStringExtra("description")
        var productImg = intent.getStringExtra("img")
        productId = intent.getLongExtra("id",0)

        productTitleText = findViewById(R.id.productTitleText)
        productRatingText = findViewById(R.id.productRatingText)
        productDescriptionText = findViewById(R.id.productDescriptionText)
        productPriceText = findViewById(R.id.productPriceText)
        productImgView = findViewById(R.id.productImageView)
        addToCartBtn = findViewById(R.id.addToCartBtn)

        productTitleText.text = productTitle
        productRatingText.text = productRating
        productPriceText.text = productPrice + "$"
        productDescriptionText.text = productDescription
        Glide.with(this).load(productImg).into(productImgView)
        addToCartBtn.setOnClickListener(cartBtnClick)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cartMenu ->{
                var cartIntent = Intent(applicationContext,CartActivity::class.java)
                startActivity(cartIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    val cartBtnClick = View.OnClickListener {
        var orderList = mutableListOf<ProductRequest>()
        var product = ProductRequest(productId,1)
        orderList.add(product)
        var order = OrderRequest(1,orderList)
        MainActivity.dummyService.order(order).enqueue(object : Callback<Cart>{
            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
               var data = response.body()
                if(data!=null){
                    Toast.makeText(applicationContext,"Order Succesfull",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Toast.makeText(applicationContext,"Order Failed",Toast.LENGTH_LONG).show()
            }
        })
    }
}
package com.example.turkcellmidterm2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import com.example.turkcellmidterm2.adapter.ProductAdapter
import com.example.turkcellmidterm2.configs.APIClient
import com.example.turkcellmidterm2.models.DummyProduct
import com.example.turkcellmidterm2.models.Product
import com.example.turkcellmidterm2.service.DummyProductsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var dummyService: DummyProductsService
    }
    lateinit var productListView: ListView
    var productsList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productListView = findViewById(R.id.productsListView)
        dummyService = APIClient.getClient().create(DummyProductsService::class.java)
        getProducts()
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

    fun getProducts(){
        dummyService.getProducts().enqueue(object : Callback<DummyProduct> {
            override fun onResponse(call: Call<DummyProduct>, response: Response<DummyProduct>) {
                val data = response.body()
                if (data != null) {
                    productsList.addAll(data.products)
                    val adapter = ProductAdapter(this@MainActivity, productsList)
                    productListView.adapter = adapter
                    productListView.setOnItemClickListener { adapterView, view, position, id ->
                        intent = Intent(this@MainActivity, ProductDetail::class.java)
                        intent.putExtra("id",productsList.get(position).id)
                        intent.putExtra("title", productsList.get(position).title)
                        intent.putExtra("price", productsList.get(position).price.toString())
                        intent.putExtra("img", productsList.get(position).images.get(0))
                        intent.putExtra("description", productsList.get(position).description)
                        intent.putExtra("rating", productsList.get(position).rating.toString())
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<DummyProduct>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Internet or Server Fail", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}
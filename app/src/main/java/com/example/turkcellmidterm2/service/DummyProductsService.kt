package com.example.turkcellmidterm2.service

import com.example.turkcellmidterm2.models.Cart
import com.example.turkcellmidterm2.models.DummyProduct
import com.example.turkcellmidterm2.models.OrderRequest
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface DummyProductsService {

    @GET("/products?limit=12")
    fun getProducts() : Call<DummyProduct>

    @Headers("Content-Type: application/json")
    @POST("/carts/add")
    fun order(@Body orderRequest : OrderRequest) : Call<Cart>

    @GET("/carts/1")
    fun getCarts() : Call<Cart>
}
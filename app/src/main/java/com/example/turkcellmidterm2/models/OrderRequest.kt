package com.example.turkcellmidterm2.models

data class OrderRequest(
    val userId : Int,
    val products : List<ProductRequest>
)

package com.waga.waga01

class Product(val id: Int, var name: String, val mass: Int){
    override fun toString(): String{
        return name
    }
}
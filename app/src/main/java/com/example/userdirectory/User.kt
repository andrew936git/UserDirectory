package com.example.userdirectory

data class User(val name: String, val age: Int){

    override fun toString(): String = "Имя: $name, возраст: $age"


}

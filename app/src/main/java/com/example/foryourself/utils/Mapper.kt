package com.example.foryourself.utils

abstract class Mapper<From, To> {
    abstract fun map(from: From): To
}
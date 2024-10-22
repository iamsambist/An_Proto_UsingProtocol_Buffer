package com.sunaa.protoaddme.mainfeature.domain

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InputVarifiaction @Inject constructor(){

    fun isValid(input: String): Boolean {
        return input.isNotEmpty()
    }

}
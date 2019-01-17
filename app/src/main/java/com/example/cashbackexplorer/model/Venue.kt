package com.example.cashbackexplorer.model

import com.google.android.gms.common.util.NumberUtils

class Venue constructor(
    val id: Long,
    val uuid: String,
    val name: String,
    val city: String,
    val lat: Float,
    val long: Float,
    val cashback: Float,
    val user_id: Long?,
    val created_at: String,
    val updated_at: String ) {

    fun getCashbackStr(): String {
        return "$cashback%"
    }

    fun getCreator(): String {
        return if (user_id == null) "-" else "$user_id"
    }


}
package com.example.cashbackexplorer.api

import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

object ResponseValidator {

    val VALID_RESPONSE = null

    enum class ErrorType {
        INVALID_NAME,
        INVALID_EMAIL,
        AUTHORIZATION_ERROR,
        UNKNOWN_ERROR
    }

    fun identifyErrorType(response: Response<ResponseBody>): ErrorType? {
        if (response.errorBody() == null)
            return ErrorType.UNKNOWN_ERROR
        try {
            val raw = response.errorBody()!!.string()
            val mainObj = JSONObject(raw)
            val validity = mainObj.getBoolean("valid")
            if (!validity) {
                val errorObj = mainObj.getJSONObject("errors")
                val itr = errorObj.keys()
                while (itr.hasNext()) {
                    val errorKey = itr.next()
                    return when {
                        errorKey.equals("name") -> ErrorType.INVALID_NAME
                        errorKey.equals("email") -> ErrorType.INVALID_EMAIL
                        else -> ErrorType.UNKNOWN_ERROR
                    }
                }
            }
            return VALID_RESPONSE
        } catch (e: JSONException) {
            e.printStackTrace()
            return ErrorType.UNKNOWN_ERROR
        }
    }


}
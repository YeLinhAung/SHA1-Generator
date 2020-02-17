package com.example.sha1generator.data

data class PaymentCreateRequest(
    val amount: Int,
    val callbackInfo: String,
    val callbackUrl: String,
    val channelCode: String,
    val currencyCode: Int,
    val expired: Int,
    val language: String,
    val merchantCode: String,
    val merchantTradeNo: String,
    val method: String,
    val nonceStr: String,
    val otcMerchantCode: String,
    val remark: String,
    val signType: String,
    val version: String
)
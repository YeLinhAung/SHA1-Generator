package com.example.sha1generator

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class MainActivity : AppCompatActivity() {

    companion object {
        const val staticSecretKey = "0xfbhb5Knea7DRaTz7gWdDgGCgg="
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpUI()
        catchEvent()

    }

    private fun setUpUI(){
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun catchEvent(){
        btnGenerate.setOnClickListener { generateSign() }
    }

    private fun generateSign(){

        val jsonStr = getJsonFromObject(PaymentChannelRequest())
        val queryStr = getQueryString(jsonStr)

        val mac = Mac.getInstance("HmacSHA1")
        val secret = SecretKeySpec(staticSecretKey.toByteArray(), mac.algorithm)
        mac.init(secret)
        val bytes = mac.doFinal(queryStr.toByteArray())
        val sign = bytesToHex(bytes)

        showSign(sign.toString())

    }

    private fun showSign(sign: String) {
        tvSign.text = sign
    }

    private fun bytesToHex(hashInBytes: ByteArray): String? {
        val sb = StringBuilder()
        for (b in hashInBytes) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }

    private class PaymentChannelRequest {
        val language: String = "en"
        val merchantCode: String = "T05280001"
        val method: String = "get.payment.channel"
        val nonceStr: String = "11AB"
        val signType: String = "HmacSHA1"
        val version: String = "1.0"
    }

    private fun getJsonFromObject(`object`: Any): String {
        val gSon = GsonBuilder().serializeNulls().create()
        return gSon.toJson(`object`)
    }

    private fun getQueryString(unParsedString: String): String{
        val sb = StringBuilder()
        val json = JSONObject(unParsedString)
        val keys: Iterator<String> = json.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            sb.append(key)
            sb.append("=")
            sb.append(json.get(key))
            if (keys.hasNext()) sb.append("&")
        }
        return sb.toString()
    }

}

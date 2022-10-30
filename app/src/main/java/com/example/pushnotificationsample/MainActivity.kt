package com.example.pushnotificationsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pushnotificationsample.databinding.ActivityMainBinding
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    val TAG = "Ser-MPL"
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

        Log.e(TAG, "token " + FirebaseInstanceId.getInstance().getToken())
    }

    fun initListener() {
        binding.txtPush.setOnClickListener {
            //    pushNotificationLocal()
        }
    }
}

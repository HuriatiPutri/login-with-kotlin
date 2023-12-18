package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.login.response.BalaceResponse
import com.example.login.response.BaseResponse
import com.example.login.response.LoginResponse
import com.example.login.viewModel.BalanceViewModel

class LogoutActivity : AppCompatActivity() {

    private lateinit var btnLogout : Button
    private val viewModelBalance by viewModels<BalanceViewModel>()

    private lateinit var txtBalance: TextView
    private lateinit var txtWelcome: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        btnLogout = findViewById(R.id.btn_logout)
        txtBalance = findViewById(R.id.txt_balance)
        progressBar = findViewById(R.id.prgbar)
        txtWelcome = findViewById(R.id.txt_welcome)

        btnLogout.setOnClickListener{
            SessionManager.clearData(this)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        viewModelBalance.balanceResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    getBalances(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                    stopLoading()
                }
                else -> {
                    stopLoading()
                }
            }
        }

        val id = SessionManager.getData(this)
        if (id != null) {
            viewModelBalance.getBalance(id)
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        progressBar.visibility = View.GONE
    }

    fun getBalances(data: BalaceResponse?) {
        showToast("Success:" + data?.message)
        txtBalance.setText("Rp" + data?.data?.balance)
        txtWelcome.setText("Welcome " + data?.data?.name)
    }

    fun processError(msg: String?) {
        showToast("Error:" + msg)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
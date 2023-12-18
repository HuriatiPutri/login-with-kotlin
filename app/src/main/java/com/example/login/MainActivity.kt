package com.example.login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import com.example.login.response.BaseResponse
import com.example.login.response.LoginResponse
import com.example.login.viewModel.BalanceViewModel
import com.example.login.viewModel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var btnLogin : Button
    private lateinit var textInputEmail : TextInputEditText
    private lateinit var textInputPsw : TextInputEditText
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            navigateToHome()
        }

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processLogin(it.data)
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


        btnLogin = findViewById<Button>(R.id.btn_login)
        textInputEmail = findViewById(R.id.txtInput_email)
        textInputPsw = findViewById(R.id.txt_pass)
        progressBar = findViewById(R.id.prgbar)

        btnLogin.setOnClickListener{
            doLogin()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, LogoutActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    fun doLogin() {
        val email = textInputEmail.text.toString()
        val pwd = textInputPsw.text.toString()
        viewModel.loginUser(email = email, pwd = pwd)
    }

    fun doSignup() {

    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        progressBar.visibility = View.GONE
    }

    fun processLogin(data: LoginResponse?) {
        showToast("Success:" + data?.message)
        if (!data?.data?.token.isNullOrEmpty()) {
            data?.data?.token?.let {
                SessionManager.saveAuthToken(this, it)
            }
            data?.data?.id?.let {
                SessionManager.saveId(this, it)
            }
            navigateToHome()
        }
    }

    fun processError(msg: String?) {
        showToast("Error:" + msg)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
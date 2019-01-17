package com.example.cashbackexplorer.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cashbackexplorer.R
import com.example.cashbackexplorer.databinding.ActivityLoginBinding
import com.example.cashbackexplorer.model.Venue
import com.example.cashbackexplorer.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        binding.viewmodel = loginViewModel
        
        setupObservers()
    }
    
    
    private fun setupObservers() {
        // Make a Toast message for any network error
        loginViewModel.toastText.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        // Name field error
        loginViewModel.nameError.observe(this, Observer {
            login_name_edit.error = it
        })

        // Email field error
        loginViewModel.emailError.observe(this, Observer {
            login_email_edit.error = it
        })

        loginViewModel.toMainActivity.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })
    }


}

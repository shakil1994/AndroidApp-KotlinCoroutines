package com.blackbirds.nfcposapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.blackbirds.nfcposapp.Common.DataStatus
import com.blackbirds.nfcposapp.Common.NetWorkChecking
import com.blackbirds.nfcposapp.Model.LoginRequest
import com.blackbirds.nfcposapp.ViewModel.LoginViewModel
import com.blackbirds.nfcposapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private var userEmail: String? = null
    private  var userPassword: String? = null

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding.apply {
            this!!.btnSignIn.setOnClickListener {
                login()
            }
        }
    }

    private fun login() {
        binding.apply {
            userEmail = this!!.edtUserEmail.text.toString().trim { it <= ' ' }
            userPassword = this!!.edtPassword.text.toString().trim { it <= ' ' }

            val error_text = "Required"

            if (userEmail!!.isEmpty()){
                edtUserEmail.requestFocus()
                edtUserEmail.error = error_text
            } else if (userPassword!!.isEmpty()) {
                edtPassword.requestFocus()
                edtPassword.error = error_text
            }
            else{
                if (!NetWorkChecking.isNetworkAvailable(this@MainActivity)){
                    val loginRequest = LoginRequest(userEmail!!, userPassword!!)
                    viewModel.login(loginRequest)
                    viewModel.loginResponse.observe(this@MainActivity){ response ->
                        response.getContentIfNotHandled()?.let {
                            when(it.status){
                                DataStatus.Status.LOADING -> {
                                    Toast.makeText(baseContext, "Loading", Toast.LENGTH_SHORT).show()
                                }
                                DataStatus.Status.SUCCESS -> {
                                    Toast.makeText(baseContext, "Login Success" + it.data!!.message, Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this@MainActivity,MainActivity2::class.java))
                                }
                                DataStatus.Status.ERROR -> {
                                    Toast.makeText(baseContext, "Phone number does not exist.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(baseContext, "No Internet", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isEmail(text: EditText): Boolean {
        val email: CharSequence = text.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
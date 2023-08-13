package com.blackbirds.nfcposapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.blackbirds.nfcposapp.databinding.ActivityLoginBinding
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgFinger.setOnClickListener {
            checkDeviceHasBiometric()
        }

        executor  =ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@LoginActivity, "Authentication error: $errorCode", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@LoginActivity, "Authentication succeeded", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@LoginActivity,MainActivity2::class.java))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_LONG).show()
            }
        })

        promptInfo = PromptInfo.Builder()
            .setTitle("Sample Title")
            .setSubtitle("Sample Subtitle")
            .setNegativeButtonText("Sample NegativeButtonText")
            .build()

        binding.btnLogin.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun checkDeviceHasBiometric(){
        val biometricManager = BiometricManager.from(this)
        when(biometricManager
            .canAuthenticate(BIOMETRIC_STRONG
                    or DEVICE_CREDENTIAL)){
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MY_APP_TAG", "App can authenticate using biometric.")
                binding.tvMsg.text = "App can authenticate using biometric."
                binding.btnLogin.isEnabled = true
                binding.btnLogin.setTextColor(getColor(R.color.white))
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d("MY_APP_TAG", "Biometric features are currently unavailable.")
                binding.tvMsg.text = "Biometric features are currently unavailable."
                binding.btnLogin.isEnabled = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }
                binding.btnLogin.isEnabled = false

                startActivityForResult(enrollIntent, 100)
            }
        }
    }
}
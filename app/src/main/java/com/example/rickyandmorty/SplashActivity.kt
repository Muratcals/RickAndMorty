package com.example.rickyandmorty

import android.app.backup.SharedPreferencesBackupHelper
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.edit
import com.example.rickyandmorty.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding :ActivitySplashBinding
    private lateinit var sharedefPreferences:SharedPreferences
    private lateinit var animationLogo:Animation
    private lateinit var animationText:Animation
    private lateinit var sayac :CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        animationLogo =AnimationUtils.loadAnimation(applicationContext,R.anim.splas_logo_animation)
        animationText =AnimationUtils.loadAnimation(applicationContext,R.anim.splash_text_animation)
        sharedefPreferences=getSharedPreferences("Giris",Context.MODE_PRIVATE)
        val girisKontrol =sharedefPreferences.getInt("girisKontrol",0)
        if (girisKontrol==0){
            sharedefPreferences.edit {
                binding.splashText.text="Welcome!"
                this.putInt("girisKontrol",1).commit()
            }
        }else{
            binding.splashText.text="Hello!"
        }
        countDownTimer()
        sayac.start()
    }

    fun countDownTimer(){
        binding.splashText.startAnimation(animationText)
        binding.splashLogo.startAnimation(animationLogo)
        sayac =object :CountDownTimer(4500,1000){
            override fun onTick(millisUntilFinished: Long) {
                println(millisUntilFinished)
            }
            override fun onFinish() {
                val intent =Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
                this@SplashActivity.finish()
            }
        }
    }
}
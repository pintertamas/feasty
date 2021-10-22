package hu.bme.aut.feasty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import hu.bme.aut.feasty.databinding.SplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        val splashAnimationEnding = AnimationUtils.loadAnimation(this, R.anim.splash_animation_ending)

        binding.feastyView.startAnimation(splashAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.feastyView.startAnimation(splashAnimationEnding)
        }, 1000)

        val homeIntent = Intent(this@SplashScreen, MainActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(homeIntent)
            finish()
        }, 1800)
    }
}
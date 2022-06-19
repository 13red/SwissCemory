package ro.serj.swisscemory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat

class IntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startMain = Intent(this, MainActivity::class.java)

        try {
            val bundle = ActivityOptionsCompat
                .makeCustomAnimation(this, R.anim.fadein, R.anim.fadeout)
                .toBundle()
            ActivityCompat.startActivity(this, startMain, bundle)
            ActivityCompat.finishAfterTransition(this)
        } catch (e: Exception) {
            startActivity(startMain)
        }
    }

}
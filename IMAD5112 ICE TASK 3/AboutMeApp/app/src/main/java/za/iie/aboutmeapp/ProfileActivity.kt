package za.iie.aboutmeapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val greetingText = findViewById<TextView>(R.id.greetingText)
        val birthYearText = findViewById<TextView>(R.id.birthYearText)

        val name = intent.getStringExtra(EXTRA_NAME).orEmpty()
        val age = intent.getIntExtra(EXTRA_AGE, -1)

        if (name.isBlank() || age <= 0) {
            greetingText.text = getString(R.string.error_profile_data_missing)
            birthYearText.text = ""
            return
        }

        val welcomeMessage = "Hello, $name! You are $age years old."
        val birthYear = 2026 - age

        greetingText.text = welcomeMessage
        birthYearText.text = getString(R.string.birth_year_message, birthYear)
        Log.v(TAG, "Calculated birth year: $birthYear")
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_AGE = "extra_age"
        private const val TAG = "ProfileActivity"
    }
}

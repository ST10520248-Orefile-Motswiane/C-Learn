package za.iie.aboutmeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val showProfileButton = findViewById<Button>(R.id.showProfileButton)

        showProfileButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val ageText = ageInput.text.toString().trim()

            var hasError = false

            if (name.isBlank()) {
                nameInput.error = getString(R.string.error_name_required)
                hasError = true
            }

            val age = ageText.toIntOrNull()
            if (age == null || age <= 0) {
                ageInput.error = getString(R.string.error_valid_age_required)
                hasError = true
            }

            if (hasError || age == null) {
                return@setOnClickListener
            }

            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtra(ProfileActivity.EXTRA_NAME, name)
                putExtra(ProfileActivity.EXTRA_AGE, age)
            }
            startActivity(intent)
        }
    }
}

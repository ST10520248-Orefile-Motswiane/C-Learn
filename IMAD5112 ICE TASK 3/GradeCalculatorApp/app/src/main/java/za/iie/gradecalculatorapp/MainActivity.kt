package za.iie.gradecalculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val markInput = findViewById<EditText>(R.id.markInput)
        val languageSwitch = findViewById<SwitchCompat>(R.id.languageSwitch)
        val checkGradeButton = findViewById<Button>(R.id.checkGradeButton)
        val gradeText = findViewById<TextView>(R.id.gradeText)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)

        checkGradeButton.setOnClickListener {
            val mark = markInput.text.toString().trim().toIntOrNull()
            if (mark == null || mark !in 0..100) {
                markInput.error = getString(R.string.error_valid_mark_required)
                gradeText.text = ""
                feedbackText.text = ""
                return@setOnClickListener
            }

            val useAfrikaans = languageSwitch.isChecked
            val grade: String
            val feedback: String

            if (mark >= 75) {
                grade = "Distinction"
                if (mark == 100) {
                    feedback = if (useAfrikaans) "Perfekte punt!" else "Perfect score!"
                } else {
                    feedback = if (useAfrikaans) "Uitstaande werk." else "Outstanding work."
                }
            } else if (mark >= 60) {
                grade = "Merit"
                feedback = if (useAfrikaans) "Mooi so." else "Well done."
            } else if (mark >= 50) {
                grade = "Pass"
                feedback = if (useAfrikaans) "Jy het dit gemaak." else "You made it."
            } else {
                grade = "Fail"
                feedback = if (useAfrikaans) "Hou aan oefen!" else "Keep practising!"
            }

            gradeText.text = getString(R.string.grade_label, grade)
            feedbackText.text = getString(R.string.feedback_label, feedback)
        }
    }
}

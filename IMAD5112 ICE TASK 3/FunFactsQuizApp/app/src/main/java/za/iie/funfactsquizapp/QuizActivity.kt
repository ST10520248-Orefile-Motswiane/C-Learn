package za.iie.funfactsquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var factText: TextView
    private lateinit var progressText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    private val facts = arrayOf(
        "An octopus has three hearts.",
        "The Great Wall of China is visible from space.",
        "Bananas are berries."
    )

    private val answers = arrayOf(true, false, true)

    private val explanations = arrayOf(
        "True! Two pump blood to the gills, one to the body.",
        "False! It is not visible to the naked eye from space.",
        "True! Botanically, bananas qualify as berries."
    )

    private var currentIndex = 0
    private var score = 0
    private var answerSubmitted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        factText = findViewById(R.id.factText)
        progressText = findViewById(R.id.progressText)
        feedbackText = findViewById(R.id.feedbackText)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)

        loadQuestion()

        trueButton.setOnClickListener { submitAnswer(true) }
        falseButton.setOnClickListener { submitAnswer(false) }

        nextButton.setOnClickListener {
            if (!answerSubmitted) {
                Toast.makeText(this, R.string.answer_before_next, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            currentIndex++
            if (currentIndex < facts.count()) {
                loadQuestion()
            } else {
                var reviewText = ""
                var counter = 0

                while (counter < facts.count()) {
                    reviewText += "Q${counter + 1}: ${facts[counter]}\n"
                    reviewText += "${explanations[counter]}\n\n"
                    counter++
                }

                val intent = Intent(this, ResultsActivity::class.java).apply {
                    putExtra(ResultsActivity.EXTRA_SCORE, score)
                    putExtra(ResultsActivity.EXTRA_TOTAL, facts.count())
                    putExtra(ResultsActivity.EXTRA_REVIEW_TEXT, reviewText.trim())
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadQuestion() {
        factText.text = facts[currentIndex]
        progressText.text = getString(
            R.string.question_progress,
            currentIndex + 1,
            facts.count()
        )
        feedbackText.text = ""
        answerSubmitted = false
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    private fun submitAnswer(userAnswer: Boolean) {
        if (answerSubmitted) {
            return
        }

        val correctAnswer = answers[currentIndex]
        val explanation = explanations[currentIndex]

        if (userAnswer == correctAnswer) {
            score++
            feedbackText.text = getString(R.string.correct_feedback, explanation)
        } else {
            feedbackText.text = getString(R.string.wrong_feedback, explanation)
        }

        answerSubmitted = true
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }
}

package za.iie.funfactsquizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val resultFeedbackText = findViewById<TextView>(R.id.resultFeedbackText)
        val reviewText = findViewById<TextView>(R.id.reviewText)
        val reviewButton = findViewById<Button>(R.id.reviewButton)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)

        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        val total = intent.getIntExtra(EXTRA_TOTAL, 0)
        val reviewContent = intent.getStringExtra(EXTRA_REVIEW_TEXT).orEmpty()

        scoreText.text = getString(R.string.score_message, score, total)
        resultFeedbackText.text = if (score == total) {
            "Amazing! You know your facts!"
        } else if (score == 2) {
            "Not bad! Almost there."
        } else {
            "Keep learning! Try again."
        }
        reviewText.text = reviewContent

        reviewButton.setOnClickListener {
            val showReview = reviewText.visibility != View.VISIBLE
            reviewText.visibility = if (showReview) View.VISIBLE else View.GONE
            reviewButton.text = if (showReview) {
                getString(R.string.hide_review)
            } else {
                getString(R.string.review)
            }
        }

        playAgainButton.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finishAffinity()
        }
    }

    companion object {
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_TOTAL = "extra_total"
        const val EXTRA_REVIEW_TEXT = "extra_review_text"
    }
}

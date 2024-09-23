package net.spicycombo.jiamingwang.loggingandcycling3

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.widget.*
import com.google.android.material.floatingactionbutton.*

class MainActivity : AppCompatActivity() {
    private lateinit var reset: Button
    private lateinit var startStop : FloatingActionButton
    private lateinit var timer: Chronometer

    private val SAVED_RUNNING: String = "running"
    private val SAVED_NEWSTATE: String = "newState"
    private val SAVED_DISPLAYTIME: String = "displayTime"

    private var running: Boolean = false
    private var newState : Boolean = true
    private var displayTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        hookViews()

        if (savedInstanceState != null) {
            running = savedInstanceState.getBoolean(SAVED_RUNNING)
            displayTime = savedInstanceState.getLong(SAVED_DISPLAYTIME)
            newState = savedInstanceState.getBoolean(SAVED_NEWSTATE)

            if (!newState) timer.base = SystemClock.elapsedRealtime() + displayTime
            if (running) startTimer()
        }


        startStop.setOnClickListener() {
            if (!running) startTimer()
            else stopTimer()
            running = !running
        }

        reset.setOnClickListener() {
            timer.stop()
            timer.base = SystemClock.elapsedRealtime()
            startStop.setImageResource(android.R.drawable.ic_media_play)
            newState = true;
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // calculate the display time if needed and store the values and store the other info
        if(running) {
            stopTimer()
        }
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_RUNNING, running)
        outState.putLong(SAVED_DISPLAYTIME, displayTime)
        outState.putBoolean(SAVED_NEWSTATE, newState)
    }

    fun stopTimer() {
        startStop.setImageResource(android.R.drawable.ic_media_play)
        timer.stop()
        displayTime = timer.base - SystemClock.elapsedRealtime()
    }

    fun startTimer() {
        startStop.setImageResource(android.R.drawable.ic_media_pause)
        if (newState) {
            newState = false
            timer.base = SystemClock.elapsedRealtime()
        }
        else timer.base = SystemClock.elapsedRealtime() + displayTime
        timer.start()
    }

    fun hookViews() {
        reset = findViewById(R.id.button_main_reset)
        startStop = findViewById(R.id.floatingActionButton)
        timer = findViewById(R.id.chronometer_main_timer)
    }
}
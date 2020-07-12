package dev.valvassori.debounce.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.valvassori.debouncer.Debouncer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    private val debouncer = Debouncer(lifecycleScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { showToast() }
    }

    private fun showToast() = debouncer.launch(delayDuration = 1000, coroutineContext = Dispatchers.IO) {
        Toast.makeText(this@MainActivity, "Hello world", Toast.LENGTH_SHORT).show()
    }
}

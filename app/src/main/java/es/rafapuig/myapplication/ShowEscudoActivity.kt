package es.rafapuig.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import es.rafapuig.myapplication.databinding.ActivityShowEscudoBinding

class ShowEscudoActivity : AppCompatActivity() {

    companion object {
        const val NOMBRE = "es.rafapuig.myapplication.ShowEscudoActivity.NOMBRE"
        const val PUNTOS = "es.rafapuig.myapplication.ShowEscudoActivity.PUNTOS"
    }

    private lateinit var binding: ActivityShowEscudoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShowEscudoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        initListeners()

    }

    private fun initViews() {

        val nombre = intent.getStringExtra(NOMBRE)

        val escudoUrl =
            "https://github.com/rafapuig/PMDM7N_2024/blob/master" +
                    "/escudos/$nombre.png?raw=true"

        Glide.with(this)
            .load(escudoUrl)
            .into(binding.escudo)
    }

    private fun initListeners() {
        binding.puntuarBtn.setOnClickListener { onPuntuar() }
    }

    private fun onPuntuar() {
        val puntos = binding.puntosSeek.progress
        val intent = Intent()
        intent.putExtra(PUNTOS, puntos)
        setResult(RESULT_OK, intent)
        finish()
    }
}
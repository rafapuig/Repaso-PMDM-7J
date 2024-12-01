package es.rafapuig.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.rafapuig.myapplication.databinding.ActivitySelectEquipoBinding

class SelectEquipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectEquipoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySelectEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initSpinner()
        initListeners()
    }

    private fun initListeners() {
        binding.verEscudoButton.setOnClickListener { onVerEscudo() }
    }

    private fun initSpinner() {
        val equipos = arrayOf("valencia", "barcelona", "real_madrid")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, equipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.equiposSpinner.adapter = adapter
    }

    val activityResultLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { processResult(it) }

    private fun onVerEscudo() {
        val intent = Intent(this, ShowEscudoActivity::class.java)
        val nombre = binding.equiposSpinner.selectedItem as String
        intent.putExtra(ShowEscudoActivity.NOMBRE, nombre)
        activityResultLauncher.launch(intent)
        //startActivity(intent)
    }

    private fun processResult(result: ActivityResult) {
        val puntos = result.data?.getIntExtra(ShowEscudoActivity.PUNTOS,0) ?: 0
        val text = "El usuario ha puntuado el escudo con $puntos puntos"
        Toast.makeText(this,text,Toast.LENGTH_LONG).show()
    }

}
package es.rafapuig.myapplication

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import es.rafapuig.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val nombres = listOf(
        "levante-ud-logo-escudo-1",
        "real_madrid",
        "valencia",
        "barcelona",
        "osasuna", "sevilla", "mallorca", "villarreal"
    )

    //private lateinit var escudo : ImageView
    //private lateinit var nombre : TextView

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        initViews()
        initListeners()
    }

    private fun initListeners() {
        //val escudo = findViewById<ImageView>(R.id.escudo)

        val listener = object : View.OnClickListener {

            override fun onClick(v: View?) {
                Toast.makeText(v?.context, "ES EL MEJOR!!!", LENGTH_LONG)
                    .show()
            }

        }

        val listener2 = View.OnClickListener { v ->
            Toast.makeText(v?.context, "ES EL MEJOR!!!", LENGTH_LONG)
                .show()
        }

        val listener3 = object : View.OnClickListener {

            override fun onClick(v: View?) {
                onEscudoClick(v)
            }

        }

        val listener4 = View.OnClickListener { v -> onEscudoClick(v) }

        val listener5 = View.OnClickListener { onEscudoClick(it) }

        //escudo.setOnClickListener(listener5)

        //escudo.setOnClickListener({ onEscudoClick(it) })

        //escudo.setOnClickListener() { onEscudoClick(it) }

        binding.escudo.setOnClickListener { onEscudoClick(it) }
        binding.permissionButton.setOnClickListener { onStartRecording() }

        binding.selectEquipo.setOnClickListener { onSelectEquipo() }
    }

    private fun onSelectEquipo() {
        val intent = Intent(this, SelectEquipoActivity::class.java)
        startActivity(intent)
    }


    private fun onStartRecording() {
        if (!isRecodingPermissionGranted()) {
            Toast
                .makeText(this, "No mmm tienes permiso de grabar!!!", LENGTH_LONG)
                .show()

            if (shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
                showRationale()
            } else {
                requestRecordPermission()
            }

        } else {
            startRecording()
        }
    }

    private fun isRecodingPermissionGranted() = checkSelfPermission(
        android.Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    private fun showRationale() {
        val builder = AlertDialog.Builder(this)
        builder
            .setMessage(
                "El permiso para acceder al microfono" +
                        " es necesario para  que la app pueda grabar audio"
            )
            .setTitle("Permiso requerido")
            .setPositiveButton("Aceptar") { _, _ -> requestRecordPermission() }
            .create()
            .show()
    }

    val requestPermissionsLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            { map -> onRequestPermissionsResult(map) }
        )

    private fun onRequestPermissionsResult(map: Map<String, Boolean>) {
        if (map.values.all { it }) {
            startRecording()
        }
    }


    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { processRecordPermissionResult(it) }

    private fun requestRecordPermission() {
        //requestPermissions(arrayOf(RECORD_AUDIO), 100)
        requestPermissionLauncher.launch(RECORD_AUDIO)
        requestPermissionsLauncher.launch(arrayOf(RECORD_AUDIO, ACCESS_FINE_LOCATION))
    }

    private fun processRecordPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            startRecording()
            Log.i("TAG", "El permiso ha sido concedido por el usuario")
        } else {
            Log.i("TAG", "El permiso ha sido denegado por el usuario")
        }
    }


    private fun startRecording() {
        Toast
            .makeText(this, "Iniciando grabacion...", LENGTH_LONG)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("RAFA", "Procesando onRequestPermissionsResult")
        if (grantResults[0] == PERMISSION_GRANTED) {
            Log.i("RAFA", "Permiso concedido")
            startRecording()
        }
    }


    fun onEscudoClick(view: View?) {
        //Toast.makeText(view?.context, "ES EL MEJOR!!!", LENGTH_LONG)
        //    .show()

        val nombre = nombres[nombres.indices.random()]

        setEscudoImage(nombre)
        binding.nombreEquipo.text = nombre
    }

    private fun initViews() {
        //escudo = findViewById<ImageView>(R.id.escudo)   //ImageView(this)
        //iv.setImageResource(android.R.drawable.ic_dialog_email)

        //iv.setImageURI(Uri.parse(
        //    "https://github.com/rafapuig/PMDM7N_2024/blob/master/escudos/levante-ud-logo-escudo-1.png?raw=true"))

        setEscudoImage(nombres[0])

        //nombre = findViewById<TextView>(R.id.nombre_equipo)

        binding.nombreEquipo.text = "LEVANTE U.D."
    }

    private fun setEscudoImage(nombre: String) {

        val escudoUrl =
            "https://github.com/rafapuig/PMDM7N_2024/blob/master/escudos/$nombre.png?raw=true"

        Glide.with(this)
            .load(escudoUrl)
            .into(binding.escudo)
    }
}
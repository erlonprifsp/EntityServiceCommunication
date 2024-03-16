package br.edu.ifsp.scl.sdm.entityservicecommunication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.entityservicecommunication.databinding.ActivityMainBinding

class MainActivity :
    AppCompatActivity() { // MainActivity herda da classe AppCompatActivity (AppCompatActivity é uma Activity)
    private val amb: ActivityMainBinding by lazy { // amb é uma instância da classe ActivityMainBinding
        ActivityMainBinding.inflate(layoutInflater) // infla o layout e armazena em amb
    }

    private lateinit var incrementServiceIntent: Intent // incrementServiceIntent é uma instância da classe Intent
    private var counter =
        0 // variável de controle que armazena o valor do contador (inicializa com 0)
    private val incrementBroadcastReceiver = object :
        BroadcastReceiver() { // incrementBroadcastReceiver é uma instância da classe BroadcastReceiver
        // sobrescreve o método onReceive
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getIntExtra("VALUE", -1)?.also { value ->
                counter = value
                Toast.makeText(this@MainActivity, "You clicked $counter times", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) { // sobrescreve o método onCreate da classe AppCompatActivity
        super.onCreate(savedInstanceState) // chama o método onCreate da classe AppCompatActivity
        setContentView(amb.root) // setContentView carrega o layout inflado em amb.root

        incrementServiceIntent = Intent(
            this,
            IncrementService::class.java
        ) // incrementServiceIntent recebe o Intent que inicia o serviço

        with(amb) { // with recebe o objeto amb
            mainTb.apply { // mainTb é um toolbar definido no arquivo de layout activity_main
                getString(R.string.app_name).also { title = it } // title recebe o nome da aplicação
                setSupportActionBar(this) // coloca o toolbar como action bar da aplicação
            }
            incrementBt.setOnClickListener { // incrementBt é um botão definido no arquivo de layout activity_main
                startService(incrementServiceIntent.apply {
                    putExtra("VALUE", counter)
                })
            }
        }

        /*
        InterEntityComunication.valueLiveData.observe(this) {value ->
            counter = value
            Toast.makeText(this, "You clicked $counter times", Toast.LENGTH_SHORT).show()
        }
        */

    }

    override fun onStart() { // sobrescreve o método onStart da classe AppCompatActivity
        super.onStart() // chama o método onStart da classe AppCompatActivity
        registerReceiver(incrementBroadcastReceiver, IntentFilter("INCREMENT_VALUE_ACTION"),
            RECEIVER_EXPORTED) // registra o BroadcastReceiver para receber o ACTION INCREMENT_VALUE_ACTION e o RECEIVER_EXPORTED para que seja exportado
    }

    override fun onStop() { // sobrescreve o método onStop da classe AppCompatActivity
        super.onStop() // chama o método onStop da classe AppCompatActivity
        unregisterReceiver(incrementBroadcastReceiver) // desregistra o BroadcastReceiver
    }

    // sobrescreve o método onDestroy da classe AppCompatActivity
    override fun onDestroy() { // metodo que destrói a activity MainActivity
        super.onDestroy() // chama o método onDestroy da classe AppCompatActivity
        stopService(incrementServiceIntent) // para o serviço
    }

}
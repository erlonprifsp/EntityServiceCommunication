package br.edu.ifsp.scl.sdm.entityservicecommunication

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message

class IncrementService : Service() { // IncrementService herda da classe Service


    private inner class IncrementHandler(looper: Looper) :
        Handler(looper) { // IncrementHandler herda da classe Handler e recebe um parâmetro do tipo Looper para o HandlerThread

        // implementa o método handleMessage
        override fun handleMessage(msg: Message) { // sobrescreve o método handleMessage da classe Handler
            super.handleMessage(msg) // chama o método handleMessage da classe Handler
            msg.data.getInt("VALUE")
                .also { // pega o valor do Bundle (msg.data) e o atribui a variável it
                    /*
                    InterEntityComunication.valueLiveData.postValue(it + 1) // incrementa o valor do MutableLiveData
                    */
                    Intent("INCREMENT_VALUE_ACTION").putExtra("VALUE", it + 1).apply { // cria o Intent carregando uma action INCREMENT_VALUE_ACTION e o VALUE com o valor incrementado
                        sendBroadcast(this) // envia o Intent para o BroadcastReceiver
                    }
                }
            stopSelf() // para o serviço
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        intent.getIntExtra("VALUE", -1)
            .also { value -> // pega o valor do Intent (intent) e o atribui a variável value
                // InterEntityComunication.valueLiveData.postValue(value + 1) // incrementa o valor do MutableLiveData que funcionará como uma memória compartilhada
                HandlerThread("IncrementThread").apply { // cria um HandlerThread para o Handler
                    start() // inicia o HandlerThread
                    IncrementHandler(looper).apply { // cria o Handler e o atribui a variável it
                        obtainMessage().apply { // obtem uma mensagem do Handler
                            data.putInt("VALUE", value) // adiciona o valor a mensagem
                            sendMessage(this) // envia a mensagem
                        }
                    }
                }
            }
        return START_NOT_STICKY // START_NOT_STICKY indica que o serviço não deve ser reiniciado
    }

    // no serviço iniciado, o método onBind é obrigatório, mas não é necessário
    override fun onBind(intent: Intent): IBinder? = null // retorna null
}
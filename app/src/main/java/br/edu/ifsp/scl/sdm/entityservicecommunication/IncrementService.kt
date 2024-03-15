package br.edu.ifsp.scl.sdm.entityservicecommunication

import android.app.Service
import android.content.Intent
import android.os.IBinder

class IncrementService : Service() { // IncrementService herda da classe Service
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        // incrementa o valor do MutableLiveData que funcionará como memória compartilhada
        intent?.getIntExtra("VALUE", -1)?.also {
            InterEntityComunication.valueLiveData.postValue(it + 1)
        }
        return START_NOT_STICKY // START_NOT_STICKY indica que o serviço não deve ser reiniciado
    }

    // no serviço iniciado, o método onBind é obrigatório, mas não é necessário
    override fun onBind(intent: Intent): IBinder? = null // retorna null
}
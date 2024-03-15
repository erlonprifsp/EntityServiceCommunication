package br.edu.ifsp.scl.sdm.entityservicecommunication

import androidx.lifecycle.MutableLiveData

object InterEntityComunication {
    val valueLiveData: MutableLiveData<Int> = MutableLiveData() // MutableLiveData utilizada como memória compartilhada

}
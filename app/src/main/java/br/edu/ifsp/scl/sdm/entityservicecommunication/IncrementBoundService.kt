package br.edu.ifsp.scl.sdm.entityservicecommunication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class IncrementBoundService : Service() {

    /*
    private inner class IncrementBoundServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            msg.replyTo?.also {
                clientMessenger = it
            }



            msg.data.getInt("VALUE", -1).also {
                if (it != -1) {
                Log.v(this.javaClass.simpleName, "Returning incremented value.")
                clientMessenger.send(Message.obtain().apply { data.putInt("VALUE", it + 1) })
                }
            }
        }
    }
    */

    /*
    private lateinit var ibsMessenger: Messenger
    private lateinit var ibsHandler: IncrementBoundServiceHandler
    private lateinit var clientMessenger: Messenger
    */

    private val ibsBinder: IncrementBoundServiceInterface.Stub = object : IncrementBoundServiceInterface.Stub() {
        override fun increment(value: Int): Int = value + 1
    }

    override fun onBind(intent: Intent): IBinder {
        Log.v(this.javaClass.simpleName, "Entity bound to the service.")
        // ibsMessenger = Messenger(ibsHandler)
        // return ibsMessenger.binder
        return ibsBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(this.javaClass.simpleName, "Entity unbound to the service.")
        return super.onUnbind(intent)
    }

    /*
    override fun onCreate() {
        super.onCreate()
        HandlerThread(this.javaClass.simpleName).apply {
            start()
            ibsHandler = IncrementBoundServiceHandler(looper)
        }
    }
    */

}
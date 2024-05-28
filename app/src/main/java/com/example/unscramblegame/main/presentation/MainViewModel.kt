package com.example.unscramblegame.main.presentation

import androidx.lifecycle.ViewModel
import com.example.unscramblegame.main.data.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val mainRepository: MainRepository
) : MyViewModel {
    fun init(firstRun: Boolean): Screen {
        return if (firstRun)
            mainRepository.lastSavedScreen()
        else
            Screen.Empty
    }
}

interface MyViewModel {

    abstract class Abstract(
        private val runAsync: RunAsync
    ) : ViewModel(), MyViewModel {

        private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

//        override fun <T : Any> runAsync(background: () -> T, ui: (T) -> Unit) {
//            runAsync.runAsync(background, ui)
//        }

        protected fun <T : Any> runAsync(background: suspend () -> T, ui: (T) -> Unit) {
            runAsync.runAsync(viewModelScope, background, ui)
        }

        protected fun cancelLastJob() {
            runAsync.cancelLastJob()
        }
    }
}

interface RunAsync {

    //    fun <T : Any> runAsync(background: () -> T, ui: (T) -> Unit)
    fun <T : Any> runAsync(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    )

    fun cancelLastJob()

    class Base : RunAsync {

//        override fun <T : Any> runAsync(background: () -> T, ui: (T) -> Unit) {
//            Thread {
//                val result: T = background.invoke()
//                Handler(Looper.getMainLooper()).post {
//                    ui.invoke(result)
//                }
//            }.start()
//        }

        protected var job: Job? = null

        override fun <T : Any> runAsync(
            coroutineScope: CoroutineScope,
            background: suspend () -> T,
            ui: (T) -> Unit
        ) {
            job = coroutineScope.launch(Dispatchers.IO) {
                val result: T = background.invoke()
                withContext(Dispatchers.Main) {
                    ui.invoke(result)
                }
            }
        }

        override fun cancelLastJob() {
            job?.cancel()
        }
    }
}
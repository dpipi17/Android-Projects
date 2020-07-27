package com.example.calculator

import android.content.Context
import com.android.mathjswrapper.MathJsWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainScenePresenterImpl(var view: MainSceneContract.View, var context: Context) : MainSceneContract.Presenter, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    private var mathJsWrapper = MathJsWrapper(context)

    override fun evaluate(expression: String) {
        launch {
            val res = mathJsWrapper.eval(expression)
            view?.updateResult(res ?: "Invalid Expression")
        }
    }

}
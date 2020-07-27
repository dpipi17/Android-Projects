package com.example.calculator

interface MainSceneContract {

    interface View {
        fun updateResult(result: String)
    }

    interface Presenter {
        fun evaluate(expression: String)
    }
}
package com.example.calculator.customwidgets

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.example.calculator.R


@RequiresApi(Build.VERSION_CODES.M)
class CustomButton(context: Context?, attrs: AttributeSet) : AppCompatButton(context, attrs) {

    private var customButtonHandler: CustomButtonHandler? = null
    private lateinit var type: Type

    init {
        setBackgroundResource(R.drawable.rounded_rect)

        context?.theme?.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0)?.apply {
            val numbersColor = getColor(R.styleable.CustomButton_customButtonNumbersColor, 0)
            val operandColor = getInteger(R.styleable.CustomButton_operandColorType, 0)

            when (operandColor) {
                0 -> {
                    setTextColor((context.getColor(R.color.buttonOperandsYellow)))
                    textSize = BIG_TEXT_SIZE_PORTRAIT
                }
                1 -> {
                    setTextColor((context.getColor(R.color.buttonOperandGreen)))
                    textSize = BIG_TEXT_SIZE_PORTRAIT
                }
                2 -> {
                    setTextColor(numbersColor)
                    textSize = SMALL_TEXT_SIZE_PORTRAIT
                }
            }

            type = getType(text.toString())
            setOnClickListener {
                customButtonHandler?.onCustomButtonClick(type, text.toString())
            }

        }
    }

    fun setCustomButtonHandler(handler: CustomButtonHandler) {
        customButtonHandler = handler
    }

    companion object {
        const val BIG_TEXT_SIZE_PORTRAIT = 22.0F
        const val SMALL_TEXT_SIZE_PORTRAIT = 18.0F

        fun getType(text: String) : Type {
            return if (text.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                Type.NUMBER
            } else if (text == "=") {
                Type.EQUAL
            } else if (text == "AC") {
                Type.DELETE
            } else {
                Type.OPERAND
            }
        }
    }

    enum class Type {
        DELETE,
        EQUAL,
        NUMBER,
        OPERAND
    }

    interface CustomButtonHandler {
        fun onCustomButtonClick(buttonType: Type, value: String);
    }
}
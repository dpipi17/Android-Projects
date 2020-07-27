package com.example.calculator

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.customwidgets.CustomButton


class MainActivity : AppCompatActivity(), MainSceneContract.View, CustomButton.CustomButtonHandler {

    companion object {
         const val themeKey = "isDarkModeOn"
    }

    private lateinit var customButtonZero: CustomButton
    private lateinit var customButtonOne: CustomButton
    private lateinit var customButtonTwo: CustomButton
    private lateinit var customButtonThree: CustomButton
    private lateinit var customButtonFour: CustomButton
    private lateinit var customButtonFive: CustomButton
    private lateinit var customButtonSix: CustomButton
    private lateinit var customButtonSeven: CustomButton
    private lateinit var customButtonEight: CustomButton
    private lateinit var customButtonNine: CustomButton

    private lateinit var customButtonPoint: CustomButton
    private lateinit var customButtonDelete: CustomButton
    private lateinit var customButtonEqual: CustomButton
    private lateinit var customButtonPlus: CustomButton
    private lateinit var customButtonMinus: CustomButton
    private lateinit var customButtonDivide: CustomButton
    private lateinit var customButtonPercent: CustomButton
    private lateinit var customButtonMultiply: CustomButton
    private lateinit var customButtonPlusMinus: CustomButton
    private var customButtonList: MutableList<CustomButton> = ArrayList()

    private lateinit var presenter: MainSceneContract.Presenter
    private lateinit var switchTheme: TextView
    private lateinit var expressionEditText: EditText
    private lateinit var resValueTextView: TextView
    private var darkMode: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        darkMode = getBoolPreference(themeKey)
        updateTheme(false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initFields() {
        presenter = MainScenePresenterImpl(this, this)

        switchTheme = findViewById(R.id.switchTheme)
        switchTheme.text = "Switch To ${if (darkMode) "Light" else "Dark"} Theme"
        switchTheme.setOnClickListener {
            saveBoolPreference(themeKey, !darkMode)
            darkMode = !darkMode
            updateTheme(true)
        }

        expressionEditText = findViewById(R.id.expression_edit_text)
        expressionEditText.isFocusable = false
        expressionEditText.setOnTouchListener {v, event ->
            true
        }

        resValueTextView = findViewById(R.id.res_value_text_field)
        initCustomButtons()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initCustomButtons() {
        findButtonsByIds();
        addHandlers();
    }

    private fun findButtonsByIds() {
        customButtonZero = findViewById(R.id.custom_button_zero)
        customButtonOne = findViewById(R.id.custom_button_one)
        customButtonTwo = findViewById(R.id.custom_button_two)
        customButtonThree = findViewById(R.id.custom_button_three)
        customButtonFour = findViewById(R.id.custom_button_four)
        customButtonFive = findViewById(R.id.custom_button_five)
        customButtonSix = findViewById(R.id.custom_button_six)
        customButtonSeven = findViewById(R.id.custom_button_seven)
        customButtonEight = findViewById(R.id.custom_button_eight)
        customButtonNine = findViewById(R.id.custom_button_nine)

        customButtonPoint = findViewById(R.id.custom_button_point)
        customButtonDelete = findViewById(R.id.custom_button_delete)
        customButtonEqual = findViewById(R.id.custom_button_equal)
        customButtonPlus = findViewById(R.id.custom_button_plus)
        customButtonMinus = findViewById(R.id.custom_button_minus)
        customButtonDivide = findViewById(R.id.custom_button_divide)
        customButtonPercent = findViewById(R.id.custom_button_percent)
        customButtonMultiply = findViewById(R.id.custom_button_multiply)
        customButtonPlusMinus = findViewById(R.id.custom_button_plus_minus)

        customButtonList.addAll(
            listOf(
                customButtonZero, customButtonOne, customButtonTwo, customButtonThree,
                customButtonFour, customButtonFive, customButtonSix, customButtonSeven,
                customButtonEight, customButtonNine, customButtonPoint, customButtonDelete,
                customButtonEqual, customButtonPlus, customButtonMinus, customButtonDivide,
                customButtonPercent, customButtonMultiply, customButtonPlusMinus
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addHandlers() {
        customButtonList.forEach {
            if (it != customButtonPlusMinus) {
                it.setCustomButtonHandler(this)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val toSave: String = resValueTextView.text.toString()
        outState.putString("textViewValue", toSave);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        resValueTextView.text = savedInstanceState.getString("textViewValue")
    }

    private fun updateTheme(onThemeChange: Boolean) {
        if (darkMode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme)
        }

        if (onThemeChange) {
            recreate();
        }
    }

    private fun saveBoolPreference(key: String, value: Boolean){
        var sharedPreference = getPreferences(Context.MODE_PRIVATE)

        with(sharedPreference.edit()){
            putBoolean(key, value)
            commit()
        }
    }

    private fun getBoolPreference(key: String, defaultValue: Boolean = false): Boolean {
        var sharedPreference = getPreferences(Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(key,defaultValue)
    }

    override fun updateResult(result: String) {
        runOnUiThread {
            resValueTextView.text = result
        }
    }

    override fun onCustomButtonClick(buttonType: CustomButton.Type, value: String) {
        var currText = expressionEditText.text.toString()

        when (buttonType) {
            CustomButton.Type.NUMBER, CustomButton.Type.OPERAND -> {
                expressionEditText.setText(currText + value)
            }

            CustomButton.Type.EQUAL -> {
                presenter.evaluate(currText)
            }

            CustomButton.Type.DELETE -> {
                expressionEditText.text.clear()
                resValueTextView.text = ""
            }
        }
    }
}
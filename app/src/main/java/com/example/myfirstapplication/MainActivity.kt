package com.example.myfirstapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.ui.theme.MyFirstApplicationTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    val inputValue = remember { mutableStateOf("") }
    val outputValue = remember { mutableStateOf("") }
    val inputUnit = remember { mutableStateOf("INR") }
    val outputUnit = remember { mutableStateOf("INR") }
    val iExpand = remember { mutableStateOf(false) }
    val oExpand = remember { mutableStateOf(false) }
    
    val conversionRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.93,
        "GBP" to 0.77,
        "JPY" to 148.0,
        "AUD" to 1.50,
        "CAD" to 1.36,
        "CHF" to 0.90,
        "CNY" to 7.27,
        "INR" to 83.0,
        "MXN" to 18.0,
        "AED" to 3.67,
        "KWD" to 0.31
    )
    
    fun convert(input:String, fromUnit:String, toUnit:String):String{
        val amount = input.toDoubleOrNull() ?: return "Invalid input"
        val fromRate = conversionRates[fromUnit] ?: return "Currency not Found!"
        val toRate = conversionRates[toUnit] ?: return "Currency not Found!"
        val result = ((amount * toRate)/fromRate).toString()
        return result
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Currency Xchange",
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Cyan, Color.Magenta)
                ),
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold
            )

        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            label = { Text(text = "Xchange Amount:") }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row {
            Box {
                Button(onClick = { iExpand.value = true }) {
                    Text(text = inputUnit.value)
                    Icon(Icons.Default.ArrowDropDown, "")
                }
                DropdownMenu(expanded = iExpand.value, onDismissRequest = { iExpand.value = false }) {
                    conversionRates.keys.forEach{ currency ->
                        DropdownMenuItem(
                            text = { Text(text = currency) },
                            onClick = {
                                iExpand.value = false
                                inputUnit.value = currency

                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { oExpand.value = true }) {
                    Text(text = outputUnit.value)
                    Icon(Icons.Default.ArrowDropDown, "")
                }
                DropdownMenu(expanded = oExpand.value, onDismissRequest = { oExpand.value = false }) {
                    conversionRates.keys.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(text = currency) },
                            onClick = {
                                outputUnit.value = currency
                                oExpand.value = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        ElevatedButton(onClick = {
            outputValue.value = convert(
                inputValue.value,
                inputUnit.value,
                outputUnit.value
            )
        },
            shape = CutCornerShape(4.dp)
        ) {
            Text(text = "Xchange")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val resultText = when {
            outputValue.value == "Invalid input" || outputValue.value == "Currency not Found!" -> outputValue.value
            else -> {
                val res = outputValue.value.toDoubleOrNull()
                val roundedResult = res?.let { (it * 100).roundToInt() / 100.0 } ?: 0.0
                "$roundedResult ${outputUnit.value}"
            }
        }
        Text(
            text = "Result: $resultText",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )




        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    MyFirstApplicationTheme {
        UnitConverter()
    }
}
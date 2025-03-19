package com.example.greetingcard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                    Display()
                }
            }
        }
    }
}

@Composable
fun Display(){
    val showText = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Greeting("Damien", "Who's Next?")
        CustomButton(
            name = "Test",
            onClick = {
                showText.value = !showText.value
            }
        )
        NewText("Blablabla test d'un nouveau texte", showText = showText.value)
        ButtonPrompt()
    }
}

@Composable
fun Greeting(name: String, nameApp: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color.Red,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Salut $name! \nBienvenue dans $nameApp",
                color = Color.White,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

@Composable
fun CustomButton(name: String, modifier: Modifier = Modifier, onClick: () -> Unit) {

    val buttonColor: MutableState<Color> = remember { mutableStateOf(Color.Gray) }

    Button(
        onClick = {
            buttonColor.value = if (buttonColor.value == Color.Gray) Color.Green else Color.Gray
            onClick()
                  },
        modifier = Modifier.padding(top = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor.value)
    ){
        Text(text = name)
    }
}

@Composable
fun NewText(text: String, modifier: Modifier = Modifier, showText: Boolean){
    if (showText) {
        Text(
            text = text,
            modifier = modifier
        )
    }
}

@Composable
fun ButtonPrompt(){

    val userInput = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        EditTextInput(value = userInput.value, onValueChange = { userInput.value = it })
    }
    Button(
        onClick = {
            Toast.makeText(
                LocalContext.current,
                "Test prompt: ${userInput.value}",
                Toast.LENGTH_SHORT
            ).show()
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(text = "Soumettre")
    }
}

@Composable
fun EditTextInput(value: String, onValueChange: (String) -> Unit){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Entrez quelque chose...") },
        modifier = Modifier.padding(16.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingCardTheme {
        Greeting("Damien", "Who's Next?")
    }
}

@Preview(showBackground = true)
@Composable
fun UserInputPreview() {
    GreetingCardTheme {
        ButtonPrompt()
    }
}
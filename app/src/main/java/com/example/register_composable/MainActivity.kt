package com.example.register_composable

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.register_composable.ui.theme.Register_composableTheme
import com.example.register_composable.ui.theme.Register_composableTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Register_composableTheme {

                // A surface container using the 'background' color from the them
                App()
                SimpleForm()

            }
        }
    }
}


@Composable
fun App() {
   Register_composableTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // A Barra de navegação ficará visível na parte superior através da função SimpleBottomAppBar
            SimpleBottomAppBar()
            // Botão de confirmação que ficará visível na parte inferior da interface

            SimpleForm()
        }
    }
}



@Composable
fun SimpleBottomAppBar() {
    BottomAppBar {
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
        }
        Column (
            modifier = Modifier
                .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "AppFirestore - Cadastro",
                style = MaterialTheme.typography.titleLarge,
                //modifier = Modifier.padding(bottom = 16.dp)
            )
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleForm() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Adiciona um espaçamento externo
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Formulário simples
        TextField(
            value = "",
            onValueChange = { /* Implemente a lógica de mudança de valor aqui */ },
            label = { Text("Nome") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = "",
            onValueChange = { /* Implemente a lógica de mudança de valor aqui */ },
            label = { Text("Telefone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
            EditableExposedDropdownMenuSample()

            CalendarSample()

        TextField(
            value = "",
            onValueChange = { /* Implemente a lógica de mudança de valor aqui */ },
            label = { Text("Observação") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )



        Button(
            onClick = {
                Log.d("Botão Validar", "O botão Validar foi clicado") // Adicione um log
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                Icons.Filled.Done,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Text("Validar")
        }

        Button(
            onClick = {
                // Implemente a lógica do botão aqui
                Log.d("Botão Cancelar", "O botão Cancelar foi clicado") // Adicione um log
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Text("Cancelar")
        }
      }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableExposedDropdownMenuSample() {
    val options = listOf("Whatsapp", "Facebook", "X", "Instagram", "Telefone")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedOptionText,
            onValueChange = { selectedOptionText = it },
            label = { Text("Select...") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        // filter options based on text field value
        val filteringOptions = options.filter { it.contains(selectedOptionText, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSample(){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDateText by remember{ mutableStateOf("") }
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMoth = calendar[Calendar.DAY_OF_MONTH]
    val datePicker = DatePickerDialog(
        context,
        {
                _:DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMoth
    )
    datePicker.datePicker.minDate = calendar.timeInMillis

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                datePicker.show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = if (selectedDateText.isNotEmpty()) {
                    "Data Selecionada $selectedDateText"
                } else {
                    "Data de Contato"
                }
            )
        }
    }
    val text = rememberSaveable { (mutableStateOf("")) }
}

@Preview(showBackground = true)
@Composable
fun SimpleBottomPreview() {
    Register_composableTheme {
        SimpleBottomAppBar()

    }
}

@Preview(showBackground = true)
@Composable
fun SimpleFormPreview() {
    Register_composableTheme {
        SimpleForm()
    }
}

@Preview(showBackground = true)
@Composable
fun EditableExposedDropdownMenuSamplePreview() {
    Register_composableTheme {
        EditableExposedDropdownMenuSample()
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarSamplePreview() {
    Register_composableTheme {
        CalendarSample()
    }
}
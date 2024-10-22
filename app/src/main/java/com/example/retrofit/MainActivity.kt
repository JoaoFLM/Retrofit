package com.example.retrofit

import ApiInterface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrofit.Response.CotacaoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var ticker by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cotação de Ações") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = ticker,
                    onValueChange = { ticker = it },
                    label = { Text("Insira o ticker") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { fetchCotacao(ticker, onResult = { result = it }) }) {
                    Text("Buscar Cotação")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(result, modifier = Modifier.fillMaxWidth())
            }
        }
    )
}

private fun fetchCotacao(ticker: String, onResult: (String) -> Unit) {
    if (ticker.isBlank()) {
        onResult("Por favor, insira um ticker.")
        return
    }

    val apiService = RetrofitInstance.getInstance().create(ApiInterface::class.java)

    val call = apiService.getCotacao("Bearer seu_token_aqui")
    call.enqueue(object : Callback<CotacaoResponse> {
        override fun onResponse(call: Call<CotacaoResponse>, response: Response<CotacaoResponse>) {
            if (response.isSuccessful) {
                response.body()?.let { cotacao ->
                    val result = """
                        Ticker: $ticker
                        Abertura: ${cotacao.open}
                        Fechamento: ${cotacao.close}
                        Máximo: ${cotacao.max}
                        Mínimo: ${cotacao.min}
                        Volume: ${cotacao.volume}
                    """.trimIndent()
                    onResult(result)
                } ?: onResult("Resposta vazia da API.")
            } else {
                onResult("Erro: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<CotacaoResponse>, t: Throwable) {
            onResult("Erro: ${t.message}")
        }
    })
}

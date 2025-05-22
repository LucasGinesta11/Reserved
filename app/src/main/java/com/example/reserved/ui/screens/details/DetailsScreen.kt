package com.example.reserved.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reserved.data.model.Establishment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    establishment: Establishment,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = establishment.nombre) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(establishment.imagenUrl),
                contentDescription = "Imagen del establecimiento",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text("Tipo: ${establishment.tipo}", fontSize = 18.sp)
            Text("Dirección: ${establishment.direccion}", fontSize = 18.sp)
            Text("Teléfono: ${establishment.telefono}", fontSize = 18.sp)
            Text("Descripción:", fontWeight = FontWeight.Bold)
            Text(establishment.descripcion, fontSize = 16.sp)
            Text("Valoración: ${establishment.valoracion}/5", fontSize = 18.sp)

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    // Aquí irá la lógica de reservar
                }) {
                    Text("Reservar")
                }

                OutlinedButton(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Volver")
                }
            }
        }
    }
}

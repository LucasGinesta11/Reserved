package com.example.reserved.ui.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reserved.data.model.Establishment
import androidx.core.net.toUri

@Composable
fun DetailsScreen(
    establishment: Establishment,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var showRatingDialog by remember { mutableStateOf(false) }

    var ratingText by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(establishment.image),
                    contentDescription = "Imagen del establecimiento",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = establishment.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Tipo: ${establishment.type}", fontSize = 16.sp)
            Text("Dirección: ${establishment.address}", fontSize = 16.sp)
            Text("Teléfono: ${establishment.phone}", fontSize = 16.sp)
            Text("Valoración: ${establishment.rating}/5", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = establishment.description,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Reservar")
                }

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Volver")
                }
            }
        }

        // FAB para valorar
        FloatingActionButton(
            onClick = { showRatingDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text("+", fontSize = 24.sp, color = MaterialTheme.colorScheme.onPrimary)
        }
    }

    // Diálogo de reserva
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Cómo quieres reservar?") },
            text = { Text("Elige una opción para contactar con el establecimiento.") },
            confirmButton = {
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${establishment.phone}".toUri()
                    }
                    context.startActivity(intent)
                    showDialog = false
                }) {
                    Text("Llamar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    val gmmIntentUri = "geo:0,0?q=${Uri.encode(establishment.address)}".toUri()
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                        setPackage("com.google.android.apps.maps")
                    }
                    context.startActivity(mapIntent)
                    showDialog = false
                }) {
                    Text("Google Maps")
                }
            }
        )
    }

    // Diálogo de valoración
    if (showRatingDialog) {
        AlertDialog(
            onDismissRequest = { showRatingDialog = false },
            title = { Text("Añadir valoración") },
            text = {
                Column {
                    OutlinedTextField(
                        value = ratingText,
                        onValueChange = { ratingText = it },
                        label = { Text("Puntuación (1.0 - 5.0)") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        label = { Text("Comentario") },
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    // TODO: Enviar la valoración al servidor con Retrofit
                    println("Valor enviado: puntuación = $ratingText, comentario = $commentText")
                    showRatingDialog = false
                    ratingText = ""
                    commentText = ""
                }) {
                    Text("Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRatingDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

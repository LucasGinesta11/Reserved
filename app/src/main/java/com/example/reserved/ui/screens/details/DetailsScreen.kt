package com.example.reserved.ui.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reserved.data.model.Establishment
import com.example.reserved.ui.viewModel.establishment.EstablishmentViewModel

@Composable
fun DetailsScreen(
    establishment: Establishment,
    viewModel: EstablishmentViewModel,
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
            Text("Valoración: ${establishment.rating}", fontSize = 16.sp)

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
                val buttonModifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)

                val shape = RoundedCornerShape(12.dp)
                val fontSize = 14.sp

                Button(
                    onClick = {
                        showRatingDialog = true
                        viewModel.loadRatings(establishment.id)
                    },
                    modifier = buttonModifier,
                    shape = shape
                ) {
                    Text("Valorar", fontSize = fontSize)
                }

                Button(
                    onClick = { showDialog = true },
                    modifier = buttonModifier,
                    shape = shape
                ) {
                    Text("Reservar", fontSize = fontSize)
                }

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = buttonModifier,
                    shape = shape
                ) {
                    Text("Volver", fontSize = fontSize)
                }
            }

        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Cómo quieres reservar?") },
            text = { Text("Elige una opción para contactar con el establecimiento.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.createReserve(establishment.id)
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

                    viewModel.createReserve(establishment.id)

                    showDialog = false
                }) {
                    Text("Google Maps")
                }
            }
        )
    }

    val ratings by viewModel.ratings.collectAsState()

    if (showRatingDialog) {
        AlertDialog(
            onDismissRequest = { showRatingDialog = false },
            title = { Text("Valoraciones") },
            text = {
                Column {
                    if (ratings.isEmpty()) {
                        Text("Este establecimiento aún no tiene valoraciones.")
                    } else {
                        ratings.forEach {
                            Text("${it.rating} - ${it.comment}", fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    HorizontalDivider()

                    Text("Añadir una valoración", fontWeight = FontWeight.SemiBold)
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
                    val ratingDouble = ratingText.toDoubleOrNull()
                    if (ratingDouble != null && ratingDouble in 1.0..5.0) {
                        viewModel.submitRating(establishment.id, ratingDouble, commentText)
                        showRatingDialog = false
                        ratingText = ""
                        commentText = ""
                    }
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

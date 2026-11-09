package cl.duoc.consumiendoapi.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.consumiendoapi.ui.theme.*

@Composable
fun TarjetaEstadistica(
    titulo: String,
    valor: String,
    colorBorde: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, colorBorde),
        colors = CardDefaults.cardColors(
            containerColor = Superficie
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Text(
                text = titulo,
                style = MaterialTheme.typography.bodySmall,
                color = AzulSecundario
            )
        }
    }
}

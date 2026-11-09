package cl.duoc.consumiendoapi.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.consumiendoapi.domain.model.Cliente
import cl.duoc.consumiendoapi.ui.theme.*

@Composable
fun ClienteCard(cliente: Cliente) {
    val esVip = cliente.tipo.equals("vip", ignoreCase = true)
    val colorFondo = if (esVip) Oro else Plata
    val colorBorde = if (esVip) BordeOro else BordePlata

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondo,
            contentColor = TextoPrincipal
        ),
        border = BorderStroke(
            1.dp,
            colorBorde
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${cliente.nombre} ${cliente.apellido}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = cliente.tipo.uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = if (esVip) Color(0xFF755000)
                    else Color.DarkGray
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = colorBorde
            )

            Text("ID: ${cliente.id}")
            Text("Nombre: ${cliente.nombre}")
            Text("Apellido: ${cliente.apellido}")
            Text("Edad: ${cliente.edad} años")
            Text("Tipo: ${cliente.tipo}")
        }
    }
}

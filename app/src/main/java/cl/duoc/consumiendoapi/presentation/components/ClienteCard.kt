package cl.duoc.consumiendoapi.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.consumiendoapi.domain.model.Cliente
import cl.duoc.consumiendoapi.ui.theme.*

@Composable
fun ClienteCard(cliente: Cliente) {
    val esVip = cliente.tipo.equals("vip", ignoreCase = true)
    val colorFondo = if (esVip) Oro else Superficie
    val colorBorde = if (esVip) BordeOro else BordePlata
    val iniciales = "${cliente.nombre.take(1)}${cliente.apellido.take(1)}".uppercase()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondo,
            contentColor = TextoPrincipal
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.5.dp, colorBorde)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Avatar Badge con iniciales
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            if (esVip) BordeOro.copy(alpha = 0.3f)
                            else AzulPrincipal.copy(alpha = 0.15f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = iniciales.ifBlank { "CL" },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (esVip) Color(0xFF755000) else AzulPrincipal
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${cliente.nombre} ${cliente.apellido}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ID: #${cliente.id}",
                            style = MaterialTheme.typography.bodySmall,
                            color = AzulSecundario
                        )
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodySmall,
                            color = AzulSecundario
                        )
                        Text(
                            text = "${cliente.edad} años",
                            style = MaterialTheme.typography.bodySmall,
                            color = AzulSecundario
                        )
                    }
                }
            }

            // Badge de estado VIP / Regular
            Surface(
                color = if (esVip) BordeOro else Plata,
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = cliente.tipo.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (esVip) Color(0xFF755000) else TextoPrincipal,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

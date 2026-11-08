package cl.duoc.consumiendoapi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ClientesColorScheme = lightColorScheme(
    primary = AzulPrincipal,
    secondary = AzulSecundario,
    background = FondoApp,
    surface = Superficie,
    onPrimary = Superficie,
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal
)

@Composable
fun ClientesAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ClientesColorScheme,
        content = content
    )
}
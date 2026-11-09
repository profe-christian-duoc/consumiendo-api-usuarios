
package cl.duoc.consumiendoapi.ui

import androidx.lifecycle.ViewModel
import cl.duoc.consumiendoapi.data.ClientesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClienteFormState(
    val nombre: String = "",
    val apellido: String = "",
    val edad: String = "",
    val tipo: String = "regular",
    
    val cargando: Boolean = false,
    val guardando: Boolean = false,
    val guardado: Boolean = false,
    val error: String? = null
)

class ClienteFormViewModel(
    private val repository: ClientesRepository = ClientesRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(ClienteFormState())
    val estado = _estado.asStateFlow()

    fun cambiarNombre(valor: String) {
        _estado.value = _estado.value.copy(
            nombre = valor,
            error = null
        )
    }

    fun cambiarApellido(valor: String) {
        _estado.value = _estado.value.copy(
            apellido = valor,
            error = null
        )
    }

    fun cambiarEdad(valor: String) {
        if (valor.all { it.isDigit() }) {
            _estado.value = _estado.value.copy(
                edad = valor,
                error = null
            )
        }
    }

    fun cambiarTipo(valor: String) {
        _estado.value = _estado.value.copy(
            tipo = valor,
            error = null
        )
    }
}


package cl.duoc.consumiendoapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.consumiendoapi.data.ClienteRequest
import cl.duoc.consumiendoapi.data.ClientesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    private var clienteCargadoId: Int? = null
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

    fun cargarCliente(id: Int) {
        if (clienteCargadoId == id) return

        if (_estado.value.cargando ||
            _estado.value.guardando ||
            _estado.value.guardado) return

        viewModelScope.launch {
            _estado.value = _estado.value.copy(
                cargando = true,
                error = null
            )

            try {
                val cliente = repository.obtenerCliente(id)

                _estado.value = ClienteFormState(
                    nombre = cliente.nombre,
                    apellido = cliente.apellido,
                    edad = cliente.edad.toString(),
                    tipo = cliente.tipo.lowercase()
                )
                clienteCargadoId = id
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(
                    error = "No se pudo cargar el cliente."
                )
            } finally {
                _estado.value = _estado.value.copy(
                    cargando = false
                )
            }
        }
    }

    fun guardar(id: Int?) {
        val actual = _estado.value

        if (actual.guardando || actual.cargando ||
            actual.guardado) return

        val nombre = actual.nombre.trim()
        val apellido = actual.apellido.trim()
        val edad = actual.edad.toIntOrNull()

        val errorValidacion = when {
            nombre.isBlank() -> "Ingresa un nombre."
            apellido.isBlank() -> "Ingresa un apellido."
            edad == null || edad !in 1..120 ->
                "La edad debe estar entre 1 y 120."
            actual.tipo !in listOf("regular", "vip") ->
                "Selecciona un tipo válido."
            else -> null
        }

        if (errorValidacion != null) {
            _estado.value = actual.copy(error = errorValidacion)
            return
        }

        val solicitud = ClienteRequest(
            nombre = nombre,
            apellido = apellido,
            edad = edad!!,
            tipo = actual.tipo
        )

        viewModelScope.launch {
            _estado.value = _estado.value.copy(
                guardando = true,
                error = null
            )

            try {
                if (id == null) {
                    repository.crearCliente(solicitud)
                } else {
                    repository.actualizarCliente(id, solicitud)
                }

                _estado.value = _estado.value.copy(
                    guardado = true
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(
                    error = "No se pudo guardar el cliente: ${e.message}"
                )
            } finally {
                _estado.value = _estado.value.copy(
                    guardando = false
                )
            }
        }
    }
}

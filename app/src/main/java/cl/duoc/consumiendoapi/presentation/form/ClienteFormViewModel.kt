package cl.duoc.consumiendoapi.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.consumiendoapi.data.model.ClienteRequest
import cl.duoc.consumiendoapi.domain.usecase.GuardarClienteUseCase
import cl.duoc.consumiendoapi.domain.usecase.ObtenerClienteUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClienteFormViewModel(
    private val obtenerClienteUseCase: ObtenerClienteUseCase = ObtenerClienteUseCase(),
    private val guardarClienteUseCase: GuardarClienteUseCase = GuardarClienteUseCase()
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
                val cliente = obtenerClienteUseCase(id)

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

        val edadInt = actual.edad.toIntOrNull()
        val errorValidacion = ClienteValidator.validar(
            nombre = actual.nombre,
            apellido = actual.apellido,
            edad = edadInt,
            tipo = actual.tipo
        )

        if (errorValidacion != null) {
            _estado.value = actual.copy(error = errorValidacion)
            return
        }

        val solicitud = ClienteRequest(
            nombre = actual.nombre.trim(),
            apellido = actual.apellido.trim(),
            edad = edadInt!!,
            tipo = actual.tipo
        )

        viewModelScope.launch {
            _estado.value = _estado.value.copy(
                guardando = true,
                error = null
            )

            try {
                if (id == null) {
                    guardarClienteUseCase.crear(solicitud)
                } else {
                    guardarClienteUseCase.actualizar(id, solicitud)
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

package cl.duoc.consumiendoapi.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.consumiendoapi.data.model.ClienteRequest
import cl.duoc.consumiendoapi.data.usecase.ClientesUseCases
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClienteFormViewModel(
    private val clientesUseCases: ClientesUseCases = ClientesUseCases()
) : ViewModel() {

    private var clienteCargadoId: Int? = null
    private val _estado = MutableStateFlow(ClienteFormState())
    val estado = _estado.asStateFlow()

    fun cambiarNombre(valor: String) {
        _estado.value = _estado.value.copy(
            nombre = valor,
            nombreError = ClienteValidator.validarNombre(valor),
            errorGeneral = null
        )
    }

    fun cambiarApellido(valor: String) {
        _estado.value = _estado.value.copy(
            apellido = valor,
            apellidoError = ClienteValidator.validarApellido(valor),
            errorGeneral = null
        )
    }

    fun cambiarEdad(valor: String) {
        if (valor.all { it.isDigit() }) {
            _estado.value = _estado.value.copy(
                edad = valor,
                edadError = ClienteValidator.validarEdad(valor),
                errorGeneral = null
            )
        }
    }

    fun cambiarTipo(valor: String) {
        _estado.value = _estado.value.copy(
            tipo = valor,
            errorGeneral = null
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
                errorGeneral = null
            )

            try {
                val cliente = clientesUseCases.obtenerCliente(id)

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
                    errorGeneral = e.message ?: "No se pudo cargar el cliente."
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

        val nombreErr = ClienteValidator.validarNombre(actual.nombre)
        val apellidoErr = ClienteValidator.validarApellido(actual.apellido)
        val edadErr = ClienteValidator.validarEdad(actual.edad)
        val tipoErr = ClienteValidator.validarTipo(actual.tipo)

        if (nombreErr != null || apellidoErr != null || edadErr != null || tipoErr != null) {
            _estado.value = actual.copy(
                nombreError = nombreErr,
                apellidoError = apellidoErr,
                edadError = edadErr,
                errorGeneral = tipoErr
            )
            return
        }

        val edadInt = actual.edad.toIntOrNull()!!
        val solicitud = ClienteRequest(
            nombre = actual.nombre.trim(),
            apellido = actual.apellido.trim(),
            edad = edadInt,
            tipo = actual.tipo
        )

        viewModelScope.launch {
            _estado.value = _estado.value.copy(
                guardando = true,
                errorGeneral = null
            )

            try {
                if (id == null) {
                    clientesUseCases.crearCliente(solicitud)
                } else {
                    clientesUseCases.actualizarCliente(id, solicitud)
                }

                _estado.value = _estado.value.copy(
                    guardado = true
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(
                    errorGeneral = "No se pudo guardar el cliente: ${e.message}"
                )
            } finally {
                _estado.value = _estado.value.copy(
                    guardando = false
                )
            }
        }
    }
}


package cl.duoc.consumiendoapi.data.repository

import cl.duoc.consumiendoapi.data.model.ClienteRequest
import cl.duoc.consumiendoapi.data.remote.ClientesApi
import cl.duoc.consumiendoapi.data.remote.RetrofitClient
import cl.duoc.consumiendoapi.domain.model.Cliente
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import retrofit2.Response

class ClientesRepository(
    private val api: ClientesApi = RetrofitClient.api
) {

    suspend fun obtenerClientes(): List<Cliente> = ejecutar {
        api.obtenerClientes()
    }

    suspend fun obtenerCliente(id: Int): Cliente = ejecutar {
        api.obtenerCliente(id)
    }

    suspend fun crearCliente(cliente: ClienteRequest) = ejecutar {
        comprobarRespuesta(api.crearCliente(cliente))
    }

    suspend fun actualizarCliente(
        id: Int,
        cliente: ClienteRequest
    ) = ejecutar {
        comprobarRespuesta(
            api.actualizarCliente(id, cliente)
        )
    }

    suspend fun eliminarCliente(id: Int) = ejecutar {
        comprobarRespuesta(api.eliminarCliente(id))
    }

    private fun comprobarRespuesta(respuesta: Response<Void>) {
        if (!respuesta.isSuccessful) {
            throw HttpException(respuesta)
        }
    }

    private suspend fun <T> ejecutar(
        operacion: suspend () -> T
    ): T {
        return try {
            operacion()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw traducirError(e)
        }
    }
}

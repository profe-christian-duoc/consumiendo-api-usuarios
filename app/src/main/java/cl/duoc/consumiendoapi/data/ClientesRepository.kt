package cl.duoc.consumiendoapi.data

import retrofit2.HttpException
import retrofit2.Response

class ClientesRepository(
    private val api: ClientesApi = RetrofitClient.api
) {

    suspend fun obtenerClientes(): List<Cliente> {
        return api.obtenerClientes()
    }

    suspend fun obtenerCliente(id: Int): Cliente {
        return api.obtenerCliente(id)
    }

    suspend fun crearCliente(cliente: ClienteRequest) {
        comprobarRespuesta(api.crearCliente(cliente))
    }

    suspend fun actualizarCliente(
        id: Int,
        cliente: ClienteRequest
    ) {
        comprobarRespuesta(
            api.actualizarCliente(id, cliente)
        )
    }

    private fun comprobarRespuesta(respuesta: Response<Void>) {
        if (!respuesta.isSuccessful) {
            throw HttpException(respuesta)
        }
    }
}
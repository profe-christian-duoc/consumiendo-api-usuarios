package cl.duoc.consumiendoapi.data

import retrofit2.HttpException
import retrofit2.Response

class ClientesRepository(private val api: ClientesApi = RetrofitClient.api) {
    suspend fun obtenerClientes(): List<Cliente> {
        return api.obtenerClientes()
    }
}
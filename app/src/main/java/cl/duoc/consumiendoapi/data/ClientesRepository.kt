package cl.duoc.consumiendoapi.data

class ClientesRepository(
    private val api: ClientesApi = RetrofitClient.api
) {
    suspend fun obtenerClientes(): List<Cliente> {
        return api.obtenerClientes()
    }
}
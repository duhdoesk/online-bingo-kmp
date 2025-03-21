package domain.util.apiResponse

sealed class ApiResponse {
    data class Success(val data: Any?) : ApiResponse()
    data object Error : ApiResponse()
    data object Loading : ApiResponse()
}

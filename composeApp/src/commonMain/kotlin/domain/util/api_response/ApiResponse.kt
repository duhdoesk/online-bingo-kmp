package domain.util.api_response

sealed class ApiResponse {
    data class Success(val data: Any?): ApiResponse()
    data object Error: ApiResponse()
    data object Loading: ApiResponse()
}
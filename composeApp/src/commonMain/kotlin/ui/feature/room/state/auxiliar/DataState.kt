package ui.feature.room.state.auxiliar

enum class DataState {
    /**
     * Data is still being fetched from the backend
     */
    LOADING,

    /**
     * There was an error and it was not possible to fetch the data from the backend
     */
    ERROR,

    /**
     * Data was successfully fetched from the backend
     */
    SUCCESS
}

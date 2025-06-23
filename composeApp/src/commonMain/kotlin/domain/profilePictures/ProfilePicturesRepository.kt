package domain.profilePictures

import domain.profilePictures.model.ProfilePictures
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ProfilePicturesRepository {

    /** Returns the available profile pictures */
    fun getProfilePictures(): Flow<Resource<ProfilePictures>>
}

package house.spring.vote.domain.service

interface ObjectManager {
    fun generateDownloadUrl(objectKey: String): String
    suspend fun generateUploadUrl(objectKey: String): String
    suspend fun generateSignedDownloadUrl(objectKey: String): String
    suspend fun copyObject(sourceKey: String, destinationKey: String)
    suspend fun deleteObject(objectKey: String)

}
package house.spring.vote.post.application.port

interface ObjectManager {
    fun generateDownloadUrl(objectKey: String): String
    suspend fun generateUploadUrl(objectKey: String): String
    suspend fun generateSignedDownloadUrl(objectKey: String): String
    suspend fun copyObject(sourceKey: String, destinationKey: String)
    suspend fun deleteObject(objectKey: String)

}
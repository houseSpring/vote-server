package house.spring.vote.util

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.*
import aws.sdk.kotlin.services.s3.presigners.presignGetObject
import aws.sdk.kotlin.services.s3.presigners.presignPutObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.minutes

@Component
class S3ImageUtil(
        @Value("\${aws.s3.bucket}")
        private val bucket: String,
        @Value("\${aws.s3.region}")
        private val region: String,
        @Value("\${aws.s3.accessKeyId}")
        private val accessKeyId: String,
        @Value("\${aws.s3.secretAccessKey}")
        private val secretAccessKey: String
) {

    private val s3Client: S3Client = S3Client {
        region = this@S3ImageUtil.region
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = this@S3ImageUtil.accessKeyId
            secretAccessKey = this@S3ImageUtil.secretAccessKey
        }
    }

    private val PRESIGNED_URL_EXPIRATION = 10.minutes

    fun generateDownloadUrl(objectKey: String): String {
        return "https://${bucket}.s3.${region}.amazonaws.com/$objectKey"
    }

    suspend fun generateUploadUrl(objectKey: String): String {
        val putObjectRequest = PutObjectRequest {
            bucket = this@S3ImageUtil.bucket
            key = objectKey
        }
        val presignedRequest = s3Client.presignPutObject(putObjectRequest, PRESIGNED_URL_EXPIRATION)
        return presignedRequest.url.toString()
    }

    suspend fun generateSignedDownloadUrl(objectKey: String): String {
        val getObjectRequest = GetObjectRequest {
            bucket = this@S3ImageUtil.bucket
            key = objectKey
        }
        val presignedRequest = s3Client.presignGetObject(getObjectRequest, PRESIGNED_URL_EXPIRATION)
        return presignedRequest.url.toString()
    }

    suspend fun copyObject(sourceKey: String, destinationKey: String): CopyObjectResponse {
        val copObjectRequest = CopyObjectRequest {
            copySource = "${this@S3ImageUtil.bucket}/$sourceKey"
            bucket = this@S3ImageUtil.bucket
            key = destinationKey
        }
        return s3Client.copyObject(copObjectRequest)
    }

    private suspend fun deleteObject(sourceKey: String, destinationKey: String): DeleteObjectResponse {
        val deleteObjectRequest = DeleteObjectRequest {
            bucket = this@S3ImageUtil.bucket
            key = sourceKey
        }
        return s3Client.deleteObject(deleteObjectRequest)
    }
}
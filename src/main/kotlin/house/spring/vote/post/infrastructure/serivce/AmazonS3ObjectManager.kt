package house.spring.vote.post.infrastructure.serivce

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.CopyObjectRequest
import aws.sdk.kotlin.services.s3.model.DeleteObjectRequest
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.sdk.kotlin.services.s3.presigners.presignGetObject
import aws.sdk.kotlin.services.s3.presigners.presignPutObject
import house.spring.vote.post.application.port.ObjectManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.minutes

@Component
class AmazonS3ObjectManager(
    @Value("\${aws.s3.bucket}")
    private val bucket: String,
    @Value("\${aws.s3.region}")
    private val region: String,
    @Value("\${aws.s3.accessKeyId}")
    private val accessKeyId: String,
    @Value("\${aws.s3.secretAccessKey}")
    private val secretAccessKey: String,
) : ObjectManager {

    private val s3Client: S3Client = S3Client {
        region = this@AmazonS3ObjectManager.region
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = this@AmazonS3ObjectManager.accessKeyId
            secretAccessKey = this@AmazonS3ObjectManager.secretAccessKey
        }
    }

    override fun generateDownloadUrl(objectKey: String): String {
        return "https://${bucket}.s3.${region}.amazonaws.com/$objectKey"
    }

    override suspend fun generateUploadUrl(objectKey: String): String {
        val putObjectRequest = PutObjectRequest {
            bucket = this@AmazonS3ObjectManager.bucket
            key = objectKey
        }
        val presignedRequest = s3Client.presignPutObject(putObjectRequest, PRESIGNED_URL_EXPIRATION)
        return presignedRequest.url.toString()
    }

    override suspend fun generateSignedDownloadUrl(objectKey: String): String {
        val getObjectRequest = GetObjectRequest {
            bucket = this@AmazonS3ObjectManager.bucket
            key = objectKey
        }
        val presignedRequest = s3Client.presignGetObject(getObjectRequest, PRESIGNED_URL_EXPIRATION)
        return presignedRequest.url.toString()
    }

    override suspend fun copyObject(sourceKey: String, destinationKey: String) {
        val copObjectRequest = CopyObjectRequest {
            copySource = "${this@AmazonS3ObjectManager.bucket}/$sourceKey"
            bucket = this@AmazonS3ObjectManager.bucket
            key = destinationKey
        }
        s3Client.copyObject(copObjectRequest)
    }

    override suspend fun deleteObject(objectKey: String) {
        val deleteObjectRequest = DeleteObjectRequest {
            bucket = this@AmazonS3ObjectManager.bucket
            key = objectKey
        }
        s3Client.deleteObject(deleteObjectRequest)
    }

    companion object {
        private val PRESIGNED_URL_EXPIRATION = 10.minutes
    }

}
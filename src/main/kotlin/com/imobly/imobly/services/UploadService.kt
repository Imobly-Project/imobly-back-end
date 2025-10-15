package com.imobly.imobly.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.imobly.imobly.exceptions.InternalErrorException
import com.imobly.imobly.exceptions.UnsupportedMediaTypeException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*
import java.util.Locale.getDefault

@Service
class UploadService(val amazonS3Client: AmazonS3) {

    fun uploadObject(objectFile: MultipartFile): String {
        val extension: String = getExtension(objectFile)

        if ((extension != ".jpg") && (extension != ".png") && (extension != ".webp")) {
            throw UnsupportedMediaTypeException(RuntimeErrorEnum.ERR0004)
        }

        val fileName: String = getName(extension)
        val bucketName = "my-personal-bucket-jvm"
        try {
            amazonS3Client.putObject(bucketName, fileName, objectFile.inputStream, getMetadata(objectFile))
        } catch (e: IOException) {
            throw InternalErrorException(RuntimeErrorEnum.ERR0003)
        }
        return "https://$bucketName.s3.amazonaws.com/$fileName"
    }

    private fun getMetadata(objectFile: MultipartFile): ObjectMetadata {
        val metadata = ObjectMetadata()
        metadata.contentLength = objectFile.size
        metadata.contentType = objectFile.contentType
        return metadata
    }

    private fun getName(extension: String): String {
        return UUID.randomUUID().toString() + extension
    }

    private fun getExtension(objectFile: MultipartFile): String {
        var extension = ""
        if (objectFile.originalFilename != null) {
            extension = objectFile.originalFilename!!.substring(objectFile.originalFilename!!.lastIndexOf("."))
                .lowercase(getDefault());
        }
        return extension
    }
}
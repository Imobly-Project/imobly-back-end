package com.imobly.imobly.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.imobly.imobly.exceptions.InternalErrorException
import com.imobly.imobly.exceptions.InvalidArgumentsException
import com.imobly.imobly.exceptions.UnsupportedMediaTypeException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*
import java.util.Locale.getDefault

@Service
class UploadService(private val amazonS3Client: AmazonS3) {

    val supportedMediaTypes = listOf(".jpg", ".jpeg", ".png", ".webp")
    val bucketName = "my-personal-bucket-jvm"

    fun uploadImage(objectFile: MultipartFile): String {
        val extension: String = getExtension(objectFile)
        if (!supportedMediaTypes.contains(extension))
            throw UnsupportedMediaTypeException(RuntimeErrorEnum.ERR0004)
        val fileName: String = getName(extension)
        try {
            amazonS3Client.putObject(
                bucketName,
                fileName,
                objectFile.inputStream,
                getMetadata(objectFile)
            )
        } catch (e: IOException) {
            throw InternalErrorException(RuntimeErrorEnum.ERR0003)
        }
        return "https://$bucketName.s3.amazonaws.com/$fileName"
    }

    fun checkIfMultipartFileListIsNull(files: List<MultipartFile>?) {
        if (files == null)
            throw InvalidArgumentsException(RuntimeErrorEnum.ERR0009)
    }

    fun checkIfMultipartFileIsNull(files: MultipartFile?) {
        if (files == null)
            throw InvalidArgumentsException(RuntimeErrorEnum.ERR0009)
    }

    fun checkIfMultipartFilesListIsInTheInterval(files: List<MultipartFile>) {
        if (files.size !in 3..15)
            throw InvalidArgumentsException(RuntimeErrorEnum.ERR0010)
    }

    private fun getMetadata(objectFile: MultipartFile): ObjectMetadata {
        val metadata = ObjectMetadata()
        metadata.contentLength = objectFile.size
        metadata.contentType = objectFile.contentType
        return metadata
    }

    private fun getName(extension: String): String = UUID.randomUUID().toString() + extension

    private fun getExtension(objectFile: MultipartFile): String =
        if (objectFile.originalFilename != null && objectFile.originalFilename!!.contains("."))
            objectFile.originalFilename!!
                .substring(objectFile.originalFilename!!.lastIndexOf("."))
                .lowercase(getDefault())

        else throw UnsupportedMediaTypeException(RuntimeErrorEnum.ERR0008)
}
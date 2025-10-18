package com.imobly.imobly.services

import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.exceptions.InvalidArgumentsException
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PropertyService(
    val repository: PropertyRepository, val uploadService: UploadService, val mapper: PropertyPersistenceMapper
) {
    fun findAll(): List<PropertyDomain> = mapper.toDomains(repository.findAll())

    fun findById(id: String): PropertyDomain =
        mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
        }))


    fun insert(property: PropertyDomain, files: List<MultipartFile>?): PropertyDomain {
        uploadService.checkIfMultipartFileListIsNull(files)
        uploadService.checkIfMultipartFilesListIsInTheInterval(files!!)
        property.pathImages = files.map { uploadService.uploadObject(it) }
        val propertySaved = repository.save(mapper.toEntity(property))
        return mapper.toDomain(propertySaved)
    }

    fun update(id: String, property: PropertyDomain, files: List<MultipartFile>?): PropertyDomain {
        property.pathImages = repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
        }).pathImages
        property.id = id
        if (files != null) {
            uploadService.checkIfMultipartFilesListIsInTheInterval(files)
            property.pathImages = files.map { uploadService.uploadObject(it) }
        }
        val propertyUpdated = repository.save(mapper.toEntity(property))
        return mapper.toDomain(propertyUpdated)
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
        })
        repository.deleteById(id)
    }
}

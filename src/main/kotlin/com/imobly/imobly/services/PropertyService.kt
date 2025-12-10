package com.imobly.imobly.services

import com.imobly.imobly.controllers.property.mappers.PropertyWebMapper
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.exceptions.InvalidArgumentsException
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.persistences.appointment.repositories.AppointmentRepository
import com.imobly.imobly.persistences.category.mappers.CategoryPersistenceMapper
import com.imobly.imobly.persistences.category.repositories.CategoryRepository
import com.imobly.imobly.persistences.issuereport.repositories.ReportRepository
import com.imobly.imobly.persistences.lease.repositories.LeaseRepository
import com.imobly.imobly.persistences.property.mappers.AddressPersistenceMapper
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.Collections

@Service
class PropertyService(
    private val propertyRepository: PropertyRepository,
    private val categoryRepository: CategoryRepository,
    private val leaseRepository: LeaseRepository,
    private val appointmentRepository: AppointmentRepository,
    private val reportRepository: ReportRepository,
    private val uploadService: UploadService,
    private val propertyMapper: PropertyPersistenceMapper,
    private val categoryMapper: CategoryPersistenceMapper
) {
    fun findAllByTitle(title: String): List<PropertyDomain> {
        val list = propertyMapper.toDomains(propertyRepository.findByTitleContainingAllIgnoreCase(title))
        Collections.sort(list)
        return list
    }

    fun findByTenantIdAndTitle(title: String, id: String): List<PropertyDomain> {
        val list = propertyMapper.toDomains(
            leaseRepository.findByTenant_IdAndProperty_TitleContainingIgnoreCase(id, title)
                .map { it.property }
        )

        Collections.sort(list)
        return list
    }

    fun findById(id: String): PropertyDomain =
        propertyMapper.toDomain(
            propertyRepository.findById(id).orElseThrow {
                throw ResourceNotFoundException(RuntimeErrorEnum.ERR0011)
            },
            CategoryPersistenceMapper()
        )


    fun insert(property: PropertyDomain, files: List<MultipartFile>?): PropertyDomain {
        uploadService.checkIfMultipartFileListIsNull(files)
        uploadService.checkIfMultipartFilesListIsInTheInterval(files!!)
        if (!categoryRepository.existsById(property.category.id ?: "")) {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        }
        property.pathImages = files.map { uploadService.uploadImage(it) }
        val propertySaved = propertyRepository.save(propertyMapper.toEntity(property, CategoryPersistenceMapper()))
        return propertyMapper.toDomain(propertySaved, CategoryPersistenceMapper())
    }

    fun update(id: String, property: PropertyDomain, files: List<MultipartFile>?): PropertyDomain {
        property.pathImages = propertyRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0011)
        }).pathImages
        if (!categoryRepository.existsById(property.category.id ?: ""))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)

        property.id = id
        if (files != null) {
            uploadService.checkIfMultipartFilesListIsInTheInterval(files)
            property.pathImages = files.map { uploadService.uploadImage(it) }
        }
        val propertyUpdated = propertyRepository.save(propertyMapper.toEntity(property, CategoryPersistenceMapper()))
        return propertyMapper.toDomain(propertyUpdated, CategoryPersistenceMapper())
    }

    fun delete(id: String) {
        if (propertyRepository.existsById(id))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0011)

        if (leaseRepository.existsByProperty_Id(id))
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0029)

        appointmentRepository.deleteAllByProperty_Id(id)
        reportRepository.deleteAllByProperty_Id(id)

        categoryRepository.findAll().forEach { it ->
            val category = categoryMapper.toDomain(it, PropertyPersistenceMapper(AddressPersistenceMapper()))
            category.properties = propertyMapper.toDomains(it.properties.filter { it.id != id })
            categoryRepository.save(
                categoryMapper.toEntity(category, PropertyPersistenceMapper(AddressPersistenceMapper()))
            )
        }

        propertyRepository.deleteById(id)
    }
}

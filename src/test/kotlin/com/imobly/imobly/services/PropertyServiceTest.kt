package com.imobly.imobly.services

import com.imobly.imobly.domains.AddressDomain
import com.imobly.imobly.domains.CategoryDomain
import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.persistences.appointment.repositories.AppointmentRepository
import com.imobly.imobly.persistences.category.entities.CategoryEntity
import com.imobly.imobly.persistences.category.mappers.CategoryPersistenceMapper
import com.imobly.imobly.persistences.category.repositories.CategoryRepository
import com.imobly.imobly.persistences.issuereport.repositories.ReportRepository
import com.imobly.imobly.persistences.lease.repositories.LeaseRepository
import com.imobly.imobly.persistences.property.entities.AddressEntity
import com.imobly.imobly.persistences.property.entities.PropertyEntity
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.web.multipart.MultipartFile

@ExtendWith(MockitoExtension::class)
class PropertyServiceTest {

    @Mock
    lateinit var uploadService: UploadService

    @Mock
    lateinit var categoryRepository: CategoryRepository

    @Mock
    lateinit var propertyRepository: PropertyRepository

    @Mock
    private lateinit var leaseRepository: LeaseRepository

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository

    @Mock
    private lateinit var reportRepository: ReportRepository

    @Mock
    lateinit var categoryMapper: CategoryPersistenceMapper

    @Mock
    lateinit var propertyMapper: PropertyPersistenceMapper

    @InjectMocks
    lateinit var propertyService: PropertyService

    private val property = FixturesProperty.propertyDomain()
    private val propertyEntity = FixturesProperty.propertyEntity()
    private val propertySavedEntity = FixturesProperty.propertyEntity(id = "PROP123")
    private val propertySavedDomain = FixturesProperty.propertyDomain(id = "PROP123")

    private val file1 = mock<MultipartFile>()
    private val file2 = mock<MultipartFile>()
    private val files = listOf(file1, file2)

    @Test
    fun `insert deve salvar propriedade e retornar dominio`() {
        whenever(categoryRepository.existsById(any())).thenReturn(true)
        whenever(uploadService.uploadImage(any())).thenReturn("image1.jpg", "image2.jpg")
        whenever(propertyMapper.toEntity(any(), any())).thenReturn(propertyEntity)
        whenever(propertyRepository.save(any())).thenReturn(propertySavedEntity)
        whenever(propertyMapper.toDomain(any(), any())).thenReturn(propertySavedDomain)

        val result = propertyService.insert(property, files)

        verify(uploadService).checkIfMultipartFileListIsNull(files)
        verify(uploadService).checkIfMultipartFilesListIsInTheInterval(files)
        verify(uploadService, times(2)).uploadImage(any())
        verify(propertyRepository).save(propertyEntity)
        verify(propertyMapper).toDomain(propertySavedEntity, categoryMapper)

        assertEquals("PROP123", result.id)
        assertEquals(propertySavedDomain, result)
    }

    @Test
    fun `insert deve lançar ResourceNotFoundException quando categoria nao existe`() {
        whenever(categoryRepository.existsById(any())).thenReturn(false)

        assertThrows<ResourceNotFoundException> {
            propertyService.insert(property, files)
        }

        verify(propertyRepository, never()).save(any())
    }

    @Test
    fun `insert deve chamar checkIfMultipartFileListIsNull quando files for null`() {
        propertyService.insert(property, null)

        verify(uploadService).checkIfMultipartFileListIsNull(null)
        verify(uploadService, never()).uploadImage(any())
    }

    @Test
    fun `insert deve chamar checkIfMultipartFilesListIsInTheInterval`() {
        whenever(categoryRepository.existsById(any())).thenReturn(true)
        whenever(uploadService.uploadImage(any())).thenReturn("image.jpg")
        whenever(propertyMapper.toEntity(any(), any())).thenReturn(propertyEntity)
        whenever(propertyRepository.save(any())).thenReturn(propertySavedEntity)
        whenever(propertyMapper.toDomain(any(), any())).thenReturn(propertySavedDomain)

        propertyService.insert(property, files)

        verify(uploadService).checkIfMultipartFilesListIsInTheInterval(files)
    }
}

object FixturesProperty {

    fun propertyDomain(
        id: String? = null,
        title: String = "Apartamento moderno",
        pathImages: List<String> = listOf("img1.jpg", "img2.jpg"),
        description: String = "Apartamento espaçoso com vista para o mar",
        monthlyRent: Double = 3500.0,
        area: Float = 85.5f,
        bedrooms: Int = 2,
        bathrooms: Int = 2,
        garageSpaces: Int = 1,
        address: AddressDomain = addressDomain(),
        category: CategoryDomain = categoryDomain()
    ) = PropertyDomain(
        id = id,
        title = title,
        pathImages = pathImages,
        description = description,
        monthlyRent = monthlyRent,
        area = area,
        bedrooms = bedrooms,
        bathrooms = bathrooms,
        garageSpaces = garageSpaces,
        address = address,
        category = category
    )

    fun categoryDomain(
        id: String? = "CAT1",
        name: String = "Apartamento"
    ) = CategoryDomain(
        id = id,
        title = name
    )

    fun addressDomain(
        street: String = "Av. Paulista",
        number: String = "1000",
        neighborhood: String = "Bela Vista",
        city: String = "São Paulo",
        state: String = "SP",
        cep: String = "01310-000"
    ) = AddressDomain(
        street = street,
        number = number,
        neighborhood = neighborhood,
        city = city,
        state = state,
        cep = cep
    )

    fun propertyEntity(
        id: String? = null,
        title: String = "Apartamento moderno",
        pathImages: List<String> = listOf("img1.jpg", "img2.jpg"),
        description: String = "Apartamento espaçoso com vista para o mar",
        monthlyRent: Double = 3500.0,
        area: Float = 85.5f,
        bedrooms: Int = 2,
        bathrooms: Int = 2,
        garageSpaces: Int = 1,
        address: AddressEntity = addressEntity(),
        category: CategoryEntity = categoryEntity()
    ) = PropertyEntity(
        id = id,
        title = title,
        pathImages = pathImages,
        description = description,
        monthlyRent = monthlyRent,
        area = area,
        bedrooms = bedrooms,
        bathrooms = bathrooms,
        garageSpaces = garageSpaces,
        address = address,
        category = category
    )

    fun categoryEntity(
        id: String? = "CAT1",
        name: String = "Apartamento"
    ) = CategoryEntity(
        id = id,
        title = name,
        properties = emptyList()
    )

    fun addressEntity(
        street: String = "Av. Paulista",
        number: String = "1000",
        neighborhood: String = "Bela Vista",
        city: String = "São Paulo",
        state: String = "SP",
        cep: String = "01310-000"
    ) = AddressEntity(
        street = street,
        number = number,
        neighborhood = neighborhood,
        city = city,
        state = state,
        cep = cep,
        complement = ""
    )
}

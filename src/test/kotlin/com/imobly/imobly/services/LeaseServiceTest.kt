package com.imobly.imobly.services

import com.imobly.imobly.domains.LeaseDomain
import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.domains.enums.MaritalStatusEnum
import com.imobly.imobly.domains.enums.PaymentStatusEnum
import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.payments.MonthlyInstallmentDomain
import com.imobly.imobly.domains.payments.PaymentDomain
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.exceptions.InvalidArgumentsException
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.category.entities.CategoryEntity
import com.imobly.imobly.persistences.lease.entities.LeaseEntity
import com.imobly.imobly.persistences.lease.mappers.LeasePersistenceMapper
import com.imobly.imobly.persistences.lease.repositories.LeaseRepository
import com.imobly.imobly.persistences.payment.repositories.PaymentRepository
import com.imobly.imobly.persistences.property.entities.AddressEntity
import com.imobly.imobly.persistences.property.entities.PropertyEntity
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

@ExtendWith(MockitoExtension::class)
class LeaseServiceTest{

    @Mock
    lateinit var propertyRepository: PropertyRepository

    @Mock
    lateinit var tenantRepository: TenantRepository

    @Mock
    lateinit var leaseRepository: LeaseRepository

    @Mock
    lateinit var paymentRepository: PaymentRepository

    @Mock
    lateinit var paymentService: PaymentService

    @InjectMocks
    lateinit var leaseService: LeaseService

    @Mock
    lateinit var mapper: LeasePersistenceMapper

    @Test
    fun `insert deve lançar ERR0011 quando o property não existe`() {
        val lease = Fixtures.leaseDomain()

        whenever(propertyRepository.existsById(lease.property.id!!))
            .thenReturn(false)

        val exception = assertThrows<ResourceNotFoundException> {
            leaseService.insert(lease)
        }

        assertEquals(RuntimeErrorEnum.ERR0011, exception.errorEnum)
    }

    @Test
    fun `insert deve lançar ERR0012 quando o tenant não existe`() {
        val lease = Fixtures.leaseDomain()

        whenever(propertyRepository.existsById(any()))
            .thenReturn(true)
        whenever(tenantRepository.existsById(lease.tenant.id!!))
            .thenReturn(false)

        val exception = assertThrows<ResourceNotFoundException> {
            leaseService.insert(lease)
        }

        assertEquals(RuntimeErrorEnum.ERR0012, exception.errorEnum)
    }

    @Test
    fun `insert deve lançar ERR0031 quando já existir lease para o property`() {
        val lease = Fixtures.leaseDomain()

        whenever(propertyRepository.existsById(any())).thenReturn(true)
        whenever(tenantRepository.existsById(any())).thenReturn(true)
        whenever(leaseRepository.existsByProperty_Id(lease.property.id!!)).thenReturn(true)

        val exception = assertThrows<OperationNotAllowedException> {
            leaseService.insert(lease)
        }

        assertEquals(RuntimeErrorEnum.ERR0031, exception.errorEnum)
    }

    @Test
    fun `insert deve lançar ERR0002 quando endDate é menor que startDate`() {
        val lease = Fixtures.leaseDomain(
            startDate = LocalDate.of(2025, 1, 10),
            endDate = LocalDate.of(2025, 1, 5)
        )

        whenever(propertyRepository.existsById(any()))
            .thenReturn(true)
        whenever(tenantRepository.existsById(any()))
            .thenReturn(true)

        val exception = assertThrows<InvalidArgumentsException> {
            leaseService.insert(lease)
        }

        assertEquals(RuntimeErrorEnum.ERR0002, exception.errorEnum)
    }

    @Test
    fun `insert deve salvar lease e chamar paymentService`() {
        val lease = Fixtures.leaseDomain()
        val leaseEntity = Fixtures.leaseEntity()
        val leaseSavedEntity = Fixtures.leaseEntity(id = "LEASE123")

        whenever(propertyRepository.existsById(any())).thenReturn(true)
        whenever(tenantRepository.existsById(any())).thenReturn(true)
        whenever(leaseRepository.existsByProperty_Id(any())).thenReturn(false)
        whenever(mapper.toEntity(any())).thenReturn(leaseEntity)
        whenever(leaseRepository.save(any())).thenReturn(leaseSavedEntity)
        whenever(mapper.toDomain(any())).thenReturn(Fixtures.leaseDomain(id = "LEASE123"))

        leaseService.insert(lease)

        val captor = argumentCaptor<LeaseDomain>()

        verify(paymentService).insert(captor.capture())
        assertEquals("LEASE123", captor.firstValue.id)
    }

}

object Fixtures {

    fun leaseDomain(
        id: String? = null,
        startDate: LocalDate = LocalDate.of(2025, 1, 10),
        endDate: LocalDate = LocalDate.of(2025, 12, 10),
        propertyId: String = "PROP1",
        tenantId: String = "TEN1",
        paymentDueDay: Int = 10
    ) = LeaseDomain(
        id = id,
        startDate = startDate,
        endDate = endDate,
        monthlyRent = 1000.0,
        securityDeposit = 2000.0,
        paymentDueDay = paymentDueDay,
        property = PropertyDomain(id = propertyId),
        tenant = TenantDomain(id = tenantId)
    )

    fun leaseEntity(
        id: String? = null,
        startDate: LocalDate = LocalDate.of(2025, 1, 10),
        endDate: LocalDate = LocalDate.of(2025, 12, 10),
        propertyId: String = "PROP1",
        tenantId: String = "TEN1",
        paymentDueDay: Int = 10
    ) = LeaseEntity(
        id = id,
        startDate = startDate,
        endDate = endDate,
        monthlyRent = 1000.0,
        securityDeposit = 2000.0,
        paymentDueDay = paymentDueDay,
        property = PropertyEntity(
            id = propertyId,
            title = "Apartamento Central",
            pathImages = listOf("img1.jpg", "img2.jpg"),
            description = "Apartamento moderno com 2 quartos e 1 vaga de garagem.",
            monthlyRent = 1000.0,
            area = 75.0f,
            bedrooms = 2,
            bathrooms = 1,
            garageSpaces = 1,
            address = AddressEntity(
                street = "Rua das Flores",
                number = "123",
                complement = "Apto 45B",
                neighborhood = "Centro",
                city = "São Paulo",
                state = "SP",
                cep = "01000-000"
            ),
            category = CategoryEntity(id = "CAT1", "Categoria", emptyList())
        ),
        tenant = TenantEntity(
            id = tenantId,
            firstName = "João",
            lastName = "Silva",
            email = "joao.silva@example.com",
            password = "hashed_password",
            rg = "12.345.678-9",
            cpf = "123.456.789-00",
            birthDate = LocalDate.of(1998, 5, 14),
            nationality = "Brasileiro",
            maritalStatus = MaritalStatusEnum.SINGLE,
            telephones = listOf(

            ),
            pathImage = "tenant1.jpg",
            job = "Engenheiro de Software",
            role = UserRoleEnum.TENANT,
            address = AddressEntity(
                street = "Av. Paulista",
                number = "1000",
                complement = "Apto 12",
                neighborhood = "Bela Vista",
                city = "São Paulo",
                state = "SP",
                cep = "01310-000"
            )
        ),
        createdAt = LocalDateTime.now(),
        lastUpdatedAt = LocalDateTime.now(),
        isEnabled = true
    )

    fun paymentDomain(lease: LeaseDomain = leaseDomain()): PaymentDomain {
        val lease = lease

        val installments = (1..12).map { monthNumber ->
            MonthlyInstallmentDomain(
                id = "INST$monthNumber",
                monthlyRent = lease.monthlyRent,
                status = PaymentStatusEnum.PENDING,
                dueDate = LocalDate.of(2025, monthNumber, lease.paymentDueDay),
                month = Month.of(monthNumber)
            )
        }.toMutableList()

        return PaymentDomain(
            id = "PAY1",
            lease = lease,
            installments = installments
        )
    }
}


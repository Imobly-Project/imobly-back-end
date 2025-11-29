package com.imobly.imobly.services

 
@Service
class CategoryService(
    private val repository: CategoryRepository, private val mapper: CategoryPersistenceMapper
) {
    fun findAllByTitle(title: String): List<CategoryDomain> {
        val list = mapper.toDomains(repository.findByTitleContainingAllIgnoreCase(title))
        Collections.sort(list)
        return list
    }

    fun findById(id: String): CategoryDomain =
        mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        }), PropertyPersistenceMapper(AddressPersistenceMapper()))

    fun insert(category: CategoryDomain): CategoryDomain {
        val categorySaved = repository.save(
            mapper.toEntity(category, PropertyPersistenceMapper(AddressPersistenceMapper()))
        )
        return mapper.toDomain(categorySaved, PropertyPersistenceMapper(AddressPersistenceMapper()))
    }

    fun update(id: String, category: CategoryDomain): CategoryDomain {
        val categoryFound = mapper.toDomain(
            repository.findById(id).orElseThrow {
                throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
            }, PropertyPersistenceMapper(AddressPersistenceMapper())
        )
        categoryFound.title = category.title
        val categoryUpdated = repository.save(
            mapper.toEntity(categoryFound, PropertyPersistenceMapper(AddressPersistenceMapper()))
        )
        return mapper.toDomain(categoryUpdated, PropertyPersistenceMapper(AddressPersistenceMapper()))
    }

    fun delete(id: String) {
        if (!repository.existsById(id))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        repository.deleteById(id)
    }
}
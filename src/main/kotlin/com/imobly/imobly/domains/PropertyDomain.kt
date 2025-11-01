package com.imobly.imobly.domains

class PropertyDomain(
    var id: String? = null,
    var title: String = "",
    var pathImages: List<String> = emptyList(),
    var description: String = "",
    var monthlyRent: Double = 0.0,
    var area: Float = 0.0f,
    var bedrooms: Int = 0,
    var bathrooms: Int = 0,
    var garageSpaces: Int = 0,
    var address: AddressDomain = AddressDomain(),
    var category: CategoryDomain = CategoryDomain()
)
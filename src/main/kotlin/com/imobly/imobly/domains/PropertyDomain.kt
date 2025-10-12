package com.imobly.imobly.domains

class PropertyDomain(
    var id: String? = null,
    var title: String,
    var pathImages: List<String>,
    var description: String,
    var rentalValue: Double,
    var area: Float,
    var bedrooms: Int,
    var bathrooms: Int,
    var garageSpaces: Int,
    var address: AddressDomain
)
package com.infoshell.domain.entity


class ProductFilter(
    val id: String? = null,
    val available: Int? = null,
    val price: Double? = null,
    val sectionId: String? = null,
    val publishingHouseId: String? = null,
    val seriesId: String? = null,
    val authorId: String? = null,
    val new: Int? = null,
    val bestseller: Int? = null,
    val isbn: Int? = null,
    val nomcode: String? = null

) {

    override fun toString(): String {
        val params = ArrayList<String>()
        if (id != null) params.add("id=$id")
        if (available != null) params.add("available=$available")
        if (price != null) params.add("price=$price")
        if (sectionId != null) params.add("section_id=$sectionId")
        if (publishingHouseId != null) params.add("publishing_house_id=$publishingHouseId")
        if (seriesId != null) params.add("series_id=$seriesId")
        if (authorId != null) params.add("author_id=$authorId")
        if (new != null) params.add("new=$new")
        if (bestseller != null) params.add("bestseller=$bestseller")
        if (isbn != null) params.add("isbn=$isbn")
        if (nomcode != null) params.add("id=$id")
        return params.joinToString(";")
    }
}
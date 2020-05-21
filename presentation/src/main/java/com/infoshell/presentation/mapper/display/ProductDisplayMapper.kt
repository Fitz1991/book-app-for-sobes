package com.infoshell.presentation.mapper.display

import com.infoshell.domain.entity.Product
import com.infoshell.presentation.enity.DisplayProduct

class ProductDisplayMapper : DomainToDisplayMapper<Product, DisplayProduct> {

    override fun mapToDisplay(domainModel: Product): DisplayProduct {
        return DisplayProduct(
            id = domainModel.id,
            isbn = domainModel.isbn,
            author = domainModel.author,
            name = domainModel.name,
            image = domainModel.image,
            price = domainModel.price,
            fullPrice = domainModel.fullPrice,
            weight = domainModel.weight,
            totalQuantity = domainModel.totalQuantity,
            promo = domainModel.promo,
            images = domainModel.images,
            isPromoActive = domainModel.isPromoActive,
            isAvailabile = domainModel.isAvailabile,
            detailText = domainModel.detailText,
            prodText = domainModel.prodText,
            rating = domainModel.rating,
            countReviews = domainModel.countReviews,
            reviews = domainModel.reviews,
            numberOfPurchases = domainModel.numberOfPurchases
        )
    }
}
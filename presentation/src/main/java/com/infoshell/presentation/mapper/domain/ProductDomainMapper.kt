package com.infoshell.presentation.mapper.domain

import com.infoshell.domain.entity.Product
import com.infoshell.presentation.enity.DisplayProduct

class ProductDomainMapper : DisplayToDomainMapper<DisplayProduct, Product> {

    override fun mapToApi(displayModel: DisplayProduct): Product {
        return Product(
            id = displayModel.id,
            isbn = displayModel.isbn,
            author = displayModel.author,
            name = displayModel.name,
            image = displayModel.image,
            price = displayModel.price,
            fullPrice = displayModel.fullPrice,
            promo = displayModel.promo,
            images = displayModel.images,
            isPromoActive = displayModel.isPromoActive,
            isAvailabile = displayModel.isAvailabile,
            detailText = displayModel.detailText,
            prodText = displayModel.prodText,
            numberOfPurchases = displayModel.numberOfPurchases,
            rating = displayModel.rating,
            countReviews = displayModel.countReviews,
            reviews = displayModel.reviews
        )
    }
}
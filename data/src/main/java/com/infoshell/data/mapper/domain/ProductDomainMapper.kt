package com.infoshell.data.mapper.domain

import com.infoshell.data.entity.ApiPicture
import com.infoshell.data.entity.ApiProduct
import com.infoshell.domain.entity.Product
import com.infoshell.domain.entity.ProductPicture

class ProductDomainMapper : ApiToDomainMapper<ApiProduct, Product> {

    override fun mapToDomain(domainModel: ApiProduct): Product {
        return Product(
            id = domainModel.id,
            author = if (domainModel.relations.isNotEmpty()) domainModel.relations[0].data.name else "",
            name = domainModel.name,
            image = if (domainModel.images.isNotEmpty()) domainModel.images[0].src else "",
            price = domainModel.price,
            fullPrice = domainModel.oldPrice,
            weight = domainModel.weight,
            totalQuantity = domainModel.quantity,
            promo = domainModel.discountPercent.toDouble(),
            isPromoActive = !domainModel.isPromoDisabled(),
            images = mapToProductPicture(domainModel.images),
            isbn = domainModel.isbn,
            isAvailabile = domainModel.isAvailabile(),
            detailText = domainModel.detailText,
            prodText = domainModel.prodText,
            numberOfPurchases = domainModel.numberOfPurchases
        )
    }

    fun mapToProductPicture(apiPictures: List<ApiPicture> ) : List<ProductPicture> {
        return apiPictures.map {
            ProductPicture(
                src = it.src
            )
        }
    }

}
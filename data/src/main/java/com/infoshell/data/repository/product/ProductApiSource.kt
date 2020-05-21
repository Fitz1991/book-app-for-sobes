package com.infoshell.data.repository.product

import com.infoshell.data.entity.ApiProduct
import com.infoshell.data.entity.ApiRating
import com.infoshell.data.entity.ApiReviews
import com.infoshell.data.mapper.domain.ApiToDomainMapper
import com.infoshell.data.network.BookApiService
import com.infoshell.data.network.SingleErrorTransformer
import com.infoshell.data.response.BaseResponse
import com.infoshell.data.response.BaseResponseRating
import com.infoshell.data.response.BaseResponseReviews
import com.infoshell.data.response.data.BaseDataWithMeta
import com.infoshell.data.response.data.MetaData
import com.infoshell.domain.entity.Product
import com.infoshell.domain.entity.Review
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import retrofit2.Response
import com.infoshell.domain.entity.BaseDataWithMeta as ProductDataWithMeta

class ProductApiSource(
    private val apiService: BookApiService,
    private val productDomainMapper: ApiToDomainMapper<ApiProduct, Product>,
    private val metaDomainMapper: ApiToDomainMapper<MetaData, com.infoshell.domain.entity.MetaData>
) : ProductRemoteSource {

    override fun getProducts(
        page: Int,
        pageSize: Int,
        filter: String
    ): Single<ProductDataWithMeta<Product>> {
        return apiService.getProducts(page, pageSize, filter, "author")
            .compose(SingleErrorTransformer<BaseDataWithMeta<ApiProduct>, Response<BaseResponse<BaseDataWithMeta<ApiProduct>>>>())
            .retry(3)
            .map {
                val items = it.body()?.data?.items?.map {
                    productDomainMapper.mapToDomain(it)
                }

                val meta = metaDomainMapper.mapToDomain(it.body()?.data?.meta!!)
                ProductDataWithMeta(meta, items!!)
            }
    }

    override fun getAllInfoProduct(product: Product): Single<Product> {
        return Single.zip(
            apiService.getReviews(product.isbn),
            apiService.getRating(product.isbn),
            BiFunction<Response<BaseResponseReviews<ApiReviews>>, Response<BaseResponseRating<ApiRating>>, Product> { reviews, rating ->
                // here we get both the results at a time.
                product.countReviews = rating.body()?.data?.countReviews ?: 0
//                product.rating = rating.body()?.data?.countPlus ?: 0
                product.reviews = reviews.body()?.data?.map {
                    Review(
                        id = it.id,
                        authorName = it.authorName,
                        date = it.date,
                        review = it.review,
                        reviewBrief = it.reviewBrief,
                        reviewRating = it.reviewRating
                    )
                } ?: listOf()
                product
            })
            .flatMap {
            Single.just(it)
        }
    }
}
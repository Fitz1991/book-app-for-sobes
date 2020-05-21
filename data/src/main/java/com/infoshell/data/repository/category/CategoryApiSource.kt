package com.infoshell.data.repository.category

import com.infoshell.data.entity.ApiCategory
import com.infoshell.data.exceptions.ApiException
import com.infoshell.data.mapper.domain.ApiToDomainMapper
import com.infoshell.data.network.BookApiService
import com.infoshell.data.network.NetworkProvider
import com.infoshell.data.network.SingleErrorTransformer
import com.infoshell.data.response.BaseResponse
import com.infoshell.data.response.data.BaseData
import com.infoshell.domain.entity.Category
import io.reactivex.Single
import retrofit2.Response

class CategoryApiSource(
    private val apiService: BookApiService,
    private val categoryDomainMapper: ApiToDomainMapper<ApiCategory, Category>
) : CategoryRemoteSource {

    override fun getAllCategories(): Single<List<Category>> {

        return apiService.getCategories()
            .flatMap {
                val body = it.body()
                if (body != null && body.isError()) {
                    return@flatMap Single.error<Response<BaseResponse<BaseData<ApiCategory>>>>(ApiException(body.getFirstError()))
                }
                else{
                    return@flatMap Single.just(it)
                }
            }
            .map {
                it.body()?.data?.items?.map {
                    categoryDomainMapper.mapToDomain(it)
                }
            }
    }

    private fun mapApiExceptionFactory(response: Response<*>): ApiException {
        return try {
            val serverMessage = response.errorBody()
                ?.string()
                ?.let { NetworkProvider.provideGson().fromJson(it, BaseResponse::class.java) }

            ApiException(serverMessage?.getFirstError())
        } catch (error: Exception) {
            ApiException("")
        }
    }


}
package com.infoshell.data.network

import com.infoshell.data.HEADER_AUTH
import com.infoshell.data.HEADER_USER_AGENT
import com.infoshell.data.HEADER_X_TOKEN
import com.infoshell.data.entity.*
import com.infoshell.data.response.BaseResponse
import com.infoshell.data.response.BaseResponseRating
import com.infoshell.data.response.BaseResponseReviews
import com.infoshell.data.response.Empty
import com.infoshell.data.response.data.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

private const val API_VERSION_PATH = "api/v1"
private const val USER_SESSION = "$API_VERSION_PATH/user-session/"
private const val USER = "$API_VERSION_PATH/user/"
private const val CHECK_EMAIL = "$API_VERSION_PATH/user/check-email-availability/"
private const val AUTHORS = "$API_VERSION_PATH/catalog/lists/authors/"
private const val CATEGORIES = "$API_VERSION_PATH/catalog/lists/categories/"
private const val PRODUCTS = "$API_VERSION_PATH/catalog/lists/products/"
private const val BASKET = "$API_VERSION_PATH/sale/order/basket/"
private const val PRODUCT = "$API_VERSION_PATH/catalog/lists/products/{id}"
private const val RATING = "$API_VERSION_PATH/catalog/product/rating/livelib/"
private const val REVIEWS = "$API_VERSION_PATH/catalog/product/reviews/livelib/"

private const val PAGE_PARAM = "PAGE"
private const val PAGE_SIZE_PARAM = "PAGE_SIZE"
private const val FILTER_PARAM = "FILTER"
private const val INCLUDE = "INCLUDE"
private const val ID = "id"
private const val ISBN = "isbn"

interface BookApiService {

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(USER_SESSION)
    fun createSession(): Single<Response<BaseResponse<UserData>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @POST(USER_SESSION)
    @FormUrlEncoded
    fun login(@FieldMap params: Map<String, String>): Single<Response<BaseResponse<UserData>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @DELETE(USER_SESSION)
    @FormUrlEncoded
    fun deleteSession(@FieldMap params: Map<String, String>): Single<Response<BaseResponse<Empty>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @POST(USER)
    @FormUrlEncoded
    fun register(@FieldMap params: Map<String, String>): Single<Response<BaseResponse<RegisteredUserData>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @POST(CHECK_EMAIL)
    @FormUrlEncoded
    fun checkEmail(@FieldMap params: Map<String, String>): Single<Response<BaseResponse<Empty>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(AUTHORS)
    fun getAuthors(@Query(PAGE_PARAM) page: Int): Single<Response<BaseResponse<BaseDataWithMeta<ApiAuthor>>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(CATEGORIES)
    fun getCategories(): Single<Response<BaseResponse<BaseData<ApiCategory>>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(CATEGORIES)
    fun getCategoriesPage(@Query(PAGE_PARAM) page: Int, @Query(PAGE_SIZE_PARAM) pageSize: Int): Single<Response<BaseResponse<*>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(PRODUCTS)
    fun getProducts(
        @Query(PAGE_PARAM) page: Int,
        @Query(PAGE_SIZE_PARAM) pageSize: Int,
        @Query(FILTER_PARAM) filter: String,
        @Query(INCLUDE) include: String
    ): Single<Response<BaseResponse<BaseDataWithMeta<ApiProduct>>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(BASKET)
    fun getBasket(): Single<Response<BaseResponse<BasketData>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @POST(BASKET)
    @FormUrlEncoded
    fun putItemToBasket(@FieldMap params: Map<String, String>): Single<Response<BaseResponse<BasketData>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @DELETE(BASKET)
    @FormUrlEncoded
    fun deleteItemFromBasket(@FieldMap params: Map<String, String>): Single<Response<BaseResponse<BasketData>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(PRODUCT)
    fun getProduct(
        @Path(ID) id: String?
    ): Single<Response<BaseResponse<ApiProduct>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(RATING)
    fun getRating(
        @Query(ISBN) isbn: String
    ): Single<Response<BaseResponseRating<ApiRating>>>

    @Headers(HEADER_AUTH, HEADER_X_TOKEN, HEADER_USER_AGENT)
    @GET(REVIEWS)
    fun getReviews(
        @Query(ISBN) isbn: String
    ): Single<Response<BaseResponseReviews<ApiReviews>>>
}
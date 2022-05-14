package com.example.foryourself.repository

import com.example.foryourself.data.retrofitResponse.*
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Resource
import com.example.kapriz.api.ApiService
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>
) {

    suspend fun getOrders(): Response<TestResponse> = apiService.getOrders()

    suspend fun postOrders(result: Result_2):
            Response<PostResponseAnswer> =
        apiService.createPost(post = result)


    fun fetchOrders() = flow {

        emit(Resource.loading())

        if (dao.getProductsFromDATABASE().isEmpty()) {
            val response = apiService.getOrders()
            if (response.isSuccessful) {
                val resultList = response.body()?.results
                resultList?.forEach { product ->
                    dao.addProductsFromRepository(resultToCascheMapper.map(product))
                }

                if (resultList!!.isEmpty())emit(Resource.empty())
                else emit(Resource.success(data = resultList))

            } else emit(Resource.error(response.message()))

        } else {

            val resultList = dao.getProductsFromDATABASE().map {
                cascheToResultMapper.map(it)
            }

            if (resultList.isEmpty()) emit(Resource.empty())
            else emit(Resource.success(data = resultList))

        }

    }


}
package com.example.onlinestore.feature_online_store.data.repository

import com.example.onlinestore.core.util.Resource
import com.example.onlinestore.feature_online_store.data.local.dao.ProductDao
import com.example.onlinestore.feature_online_store.data.remote.ProductsApi
import com.example.onlinestore.feature_online_store.domain.model.Product
import com.example.onlinestore.feature_online_store.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class ProductsRepositoryImpl(
    private val api: ProductsApi,
    private val dao: ProductDao,
) : ProductsRepository {
    override fun getProductsList(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())

        val products = dao.getAllProducts().map { it.toProduct() }

        emit(Resource.Loading(data = products))

        try {
            val remoteProductsList = api.getProducts()

            dao.deleteProducts(products = remoteProductsList.map { it.title })

            dao.insertProduct(products = remoteProductsList.map { it.toProductEntity() })

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = products
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = products
                )
            )
        }

        val newProducts = dao.getAllProducts().map { it.toProduct() }

        emit(Resource.Success(data = newProducts))
    }

    override fun getProductsPerCategory(category: String): Flow<Resource<List<Product>>> = flow {
        val productsPerCategory =
            dao.getProductsPerCategory(category = category).map { it.toProduct() }

        emit(Resource.Success(data = productsPerCategory))
    }

    override suspend fun getProductDetails(id: Int): Product? {
        return dao.getProductById(id = id).toProduct()
    }

}
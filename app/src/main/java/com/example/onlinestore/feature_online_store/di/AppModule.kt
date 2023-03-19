package com.example.onlinestore.feature_online_store.di

import android.app.Application
import androidx.room.Room
import com.example.onlinestore.feature_online_store.data.local.Converter
import com.example.onlinestore.feature_online_store.data.local.db.CartDatabase
import com.example.onlinestore.feature_online_store.data.local.db.ProductDatabase
import com.example.onlinestore.feature_online_store.data.remote.ProductsApi
import com.example.onlinestore.feature_online_store.data.repository.CartRepositoryImpl
import com.example.onlinestore.feature_online_store.data.repository.ProductsRepositoryImpl
import com.example.onlinestore.feature_online_store.data.util.GsonParser
import com.example.onlinestore.feature_online_store.domain.repository.CartRepository
import com.example.onlinestore.feature_online_store.domain.repository.ProductsRepository
import com.example.onlinestore.feature_online_store.domain.use_case.cart.*
import com.example.onlinestore.feature_online_store.domain.use_case.products.GetProductDetails
import com.example.onlinestore.feature_online_store.domain.use_case.products.ProductsUseCase
import com.example.onlinestore.feature_online_store.domain.use_case.products.GetProducts
import com.example.onlinestore.feature_online_store.domain.use_case.products.GetProductsPerCategory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductsUseCase(repository: ProductsRepository): ProductsUseCase {
        return ProductsUseCase(
            getProducts = GetProducts(repository),
            getProductsPerCategory = GetProductsPerCategory(repository),
            getProductDetails = GetProductDetails(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCartUseCase(repository: CartRepository): CartUseCase {
        return CartUseCase(
            getCartItems = GetCartItems(repository),
            removeCartItem = RemoveCartItem(repository),
            updateCartQuantity = UpdateCartQuantity(repository),
            isItemExists = IsItemExists(repository),
            addItemToCart = AddItemToCart(repository)
        )
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        api: ProductsApi,
        db: ProductDatabase
    ): ProductsRepository {
        return ProductsRepositoryImpl(
            api = api,
            dao = db.dao
        )
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        db: CartDatabase
    ): CartRepository {
        return CartRepositoryImpl(
            dao = db.dao
        )
    }

    @Provides
    @Singleton
    fun provideProductsDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            name = "products_db"
        ).addTypeConverter(Converter(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideCartDatabase(app: Application): CartDatabase {
        return Room.databaseBuilder(
            app,
            CartDatabase::class.java,
            name = "cart_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductsApi(): ProductsApi {
        val loggingInter = HttpLoggingInterceptor()
        loggingInter.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInter)
            .build()

        return Retrofit.Builder()
            .baseUrl(ProductsApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ProductsApi::class.java)
    }
}
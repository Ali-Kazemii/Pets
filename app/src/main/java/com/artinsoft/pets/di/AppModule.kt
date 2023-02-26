package com.artinsoft.pets.di

import android.content.Context
import androidx.room.Room
import com.artinsoft.pets.data.CategoryRepositoryImpl
import com.artinsoft.pets.data.local.CategoryDao
import com.artinsoft.pets.data.local.PetDatabase
import com.artinsoft.pets.data.local.SubCategoryDao
import com.artinsoft.pets.data.remote.CategoryEndPoint
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.domain.usecase.*
import com.artinsoft.pets.utils.Constants
import com.artinsoft.pets.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@OptIn(ExperimentalCoroutinesApi::class)
object AppModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideCategoryEndPoint(retrofit: Retrofit): CategoryEndPoint =
        retrofit.create(CategoryEndPoint::class.java)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        endPoint: CategoryEndPoint,
        categoryDao: CategoryDao,
        subCategoryDao: SubCategoryDao
    ): CategoryRepository = CategoryRepositoryImpl(endPoint, categoryDao, subCategoryDao)

    @Singleton
    @Provides
    fun providePetDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PetDatabase::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCategoryDao(
        database: PetDatabase
    ) = database.categoryDao()

    @Singleton
    @Provides
    fun provideSubCategoryDao(
        database: PetDatabase
    ) = database.subCategoryDao()

    @Singleton
    @Provides
    fun provideUseCases(
        getCategoryFromApiUseCase: GetCategoryFromApiUseCase,
        getSubCategoryFromApiUseCase: GetSubCategoryFromApiUseCase,
        getCategoryUseCase: GetCategoryUseCase,
        getSubCategoryUseCase: GetSubCategoryUseCase,
        insertCategoryItemUseCase: InsertCategoryItemUseCase,
        insertSubCategoryItemUseCase: InsertSubCategoryItemUseCase,
        deleteCategoryUseCase: DeleteCategoryUseCase,
        deleteSubCategoryUseCase: DeleteSubCategoryUseCase
    ) = CategoryUseCases(
        getCategoryFromApiUseCase,
        getSubCategoryFromApiUseCase,
        getCategoryUseCase,
        getSubCategoryUseCase,
        insertCategoryItemUseCase,
        insertSubCategoryItemUseCase,
        deleteCategoryUseCase,
        deleteSubCategoryUseCase
    )
}
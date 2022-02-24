package com.hazarbelge.themoviedb.network.dependency_injection

import com.hazarbelge.themoviedb.network.interceptor.HeaderInterceptor
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.network.repository.MovieDBRepository
import com.hazarbelge.themoviedb.network.utils.UrlUtils
import com.hazarbelge.themoviedb.network.MovieDBService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<IMovieDBRepository> { MovieDBRepository(get()) }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient().newBuilder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .readTimeout(15000, TimeUnit.MILLISECONDS)
            .build()
    }
    single {
        val ut = UrlUtils()
        val baseURL = ut.baseUrl

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .client(get())
            .build()
        retrofit.create(MovieDBService::class.java)
    }
}
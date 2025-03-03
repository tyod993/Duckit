package com.company.duckit.injection

import com.company.duckit.controllers.UserTokenProvider
import com.company.duckit.data.posts.PostsService
import com.company.duckit.data.user.UserService
import com.company.duckit.interactors.PostsInteractor
import com.company.duckit.interactors.UserInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUserTokenProvider(): UserTokenProvider {
        return UserTokenProvider()
    }

    @Provides
    fun providesHttpClient(): HttpClient {
        return HttpClient(CIO){
            defaultRequest {
                url("https://nametag-duckit-2.uc.r.appspot.com")
            }
            install(ContentNegotiation){
                json()
            }
        }
    }

    @Provides
    fun providesPostInteractor(client: HttpClient): PostsInteractor {
        return PostsInteractor(PostsService(client))
    }

    @Provides
    fun providesUserInteractor(client: HttpClient): UserInteractor {
        return UserInteractor(UserService(client))
    }



}


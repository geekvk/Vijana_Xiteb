package com.example.vijana_xiteb.di

import android.content.Context
import com.example.vijana_xiteb.data.remote.service.FacebookSignInService
import com.example.vijana_xiteb.data.remote.service.GoogleSignInService
import com.example.vijana_xiteb.data.repository.UserRepositoryImpl
import com.example.vijana_xiteb.domain.repository.UserRepository
import com.example.vijana_xiteb.domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context  = context

    @Provides
    @Singleton
    fun providesGoogleSignInService(
         context : Context
    ) : GoogleSignInService {
        return GoogleSignInService(context)
    }

    @Provides
    @Singleton
    fun provideFacebookSignInService() : FacebookSignInService {
        return FacebookSignInService()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        googleSignInService: GoogleSignInService,
        facebookSignInService: FacebookSignInService
    ): UserRepository {
        return UserRepositoryImpl(googleSignInService, facebookSignInService)
    }

    @Provides
    @Singleton
    fun provideUserUseCase(
        userRepository: UserRepository
    ) : UserUseCase {
        return UserUseCase(userRepository)
    }


}
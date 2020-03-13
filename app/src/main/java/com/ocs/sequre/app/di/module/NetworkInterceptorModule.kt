package com.ocs.sequre.app.di.module

import android.content.Context
import android.content.Intent
import com.compact.di.qualifier.ApplicationContext
import com.ocs.sequre.presentation.preference.AuthPreference
import com.ocs.sequre.presentation.ui.activity.MainActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.*
import java.util.*
import javax.inject.Singleton

@Module
class NetworkInterceptorModule {
    @Provides
    @Singleton
    fun providesAuthenticator(@ApplicationContext context: Context): Authenticator {
        return object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP and
                            Intent.FLAG_ACTIVITY_CLEAR_TASK and
                            Intent.FLAG_ACTIVITY_NEW_TASK and
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                context.startActivity(intent)
                return response.request
            }

        }
    }

//    @Provides
//    @IntoSet
//    fun providesBodyInterceptors(): Interceptor {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
//        return interceptor
//    }

    @Provides
    @IntoSet
    fun providesAuthenticationInterceptor(auth: AuthPreference): Interceptor {
        return Interceptor.invoke {
            when {
                it.request().url.toString().toLowerCase(Locale.ROOT).contains("/auth/") or
                        it.request().url.toString().toLowerCase(Locale.ROOT).contains("/location/") or
                        it.request().url.toString().toLowerCase(Locale.ROOT).contains("addresses.overcoffees.com/api/") -> {
                    val response = it.proceed(it.request())
//                    response.run {
//                        if (code == 200) {
//                            body?.run {
//                                Log.d("OkHttp", "Response ${string()}")
//                                val token =
//                                    Gson().fromJson<ResponseSuccess<AuthModel>>(
//                                        string(),
//                                        object : TypeToken<ResponseSuccess<AuthModel>>() {}.type
//                                    ).data.token
//                                Log.d("OkHttp", "Authorization $token")
//                                auth.set(token)
//                            }
//                        }
//                    }
//                    val token = response.header("Authorization", "")
                    response
                }
                else -> {
                    val newRequest: Request = it.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer ${auth.get()}"
                        ).build()
                    it.proceed(newRequest)
                }
            }
        }
    }

    @Provides
    @IntoSet
    fun providesCountriesInterceptor(): Interceptor {
        return Interceptor.invoke {
            when {
                it.request().url.toString().toLowerCase(Locale.ROOT).endsWith("/location/countries/codes") -> {
                    it.proceed(
                        it.request().newBuilder()
                            .url("https://addresses.overcoffees.com/api/country/codes")
                            .addHeader(
                                "authorization",
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyVHlwZSI6ImRldmVsb3BlciIsImlhdCI6MTU3OTUxMzAzNCwiZXhwIjoxMDIxOTUxMzAzNH0.W4Ky9zm0UWL91jn7XSg8pfoewaiq1U3GM0ZmGjG0s78"
                            )
                            .build()
                    )
                }
                else -> it.proceed(it.request())
            }
        }
    }
}
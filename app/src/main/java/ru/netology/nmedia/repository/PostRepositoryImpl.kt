package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit

class PostRepositoryImpl : PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }


    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll()
            .enqueue(object : Callback<List<Post>> {
                override fun onResponse(
                    call: Call<List<Post>>,
                    response: retrofit2.Response<List<Post>>
                ) {
                    try {
                        if (!response.isSuccessful) {
                            callback.onError(java.lang.RuntimeException(response.message()))
                            return
                        }
                        callback.onSuccess(
                            response.body() ?: throw RuntimeException("body is null")
                        )
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }

    override fun likeByIdAsync(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.likeById(id)
            .enqueue(object : Callback<Post> {

                override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                    try {
                        if (!response.isSuccessful) {
                            callback.onError(java.lang.RuntimeException(response.message()))
                        }
                        callback.onSuccess(
                            response.body() ?: throw RuntimeException("body is null")
                        )
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }

    override fun unlikeByIdAsync(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.dislikeById(id)
            .enqueue(object : Callback<Post> {

                override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                    try {
                        if (!response.isSuccessful) {
                            callback.onError(java.lang.RuntimeException(response.message()))
                        }
                        callback.onSuccess(
                            response.body() ?: throw RuntimeException("body is null")
                        )
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }

    override fun saveAsync(post: Post, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                try {
                    if (!response.isSuccessful) {
                        callback.onError(java.lang.RuntimeException(response.message()))
                        return
                    }
                    callback.onSuccess(
                        response.body() ?: throw RuntimeException("Saving mistake")
                    )
                } catch (e: Exception) {
                    callback.onError(e)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.Callback<Unit>) {
        PostsApi.retrofitService.likeById(id)
            .enqueue(object : Callback<Post> {

                override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                    try {
                        if (!response.isSuccessful) {
                            callback.onError(java.lang.RuntimeException(response.message()))
                        }
                        callback.onSuccess(
                            value = Unit
                        )
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }

}

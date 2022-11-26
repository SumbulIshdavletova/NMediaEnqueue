package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun getAllAsync(callback: GetAllCallback<List<Post>>)
    fun likeByIdAsync(id: Long, callback: GetAllCallback<Post>)
    fun unlikeByIdAsync(id: Long, callback: GetAllCallback<Post>)
    fun saveAsync(post: Post, callback: GetAllCallback<Post>)
    fun removeByIdAsync(id: Long, callback: GetAllCallback<Unit>)

    interface GetAllCallback<T> {
        fun onSuccess(value: T) {}
        fun onError(e: Exception) {}
    }


}

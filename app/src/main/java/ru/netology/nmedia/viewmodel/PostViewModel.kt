package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.enumeration.AttachmentType
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.*
import ru.netology.nmedia.util.SingleLiveEvent
import ru.netology.nmedia.entity.Image


private val empty = Post(
    id = 0,
    content = "",
    author = "",
    authorAvatar = "",
    likedByMe = false,
    likes = 0,
    published = "",
    attachment = Image(url = "", "", AttachmentType.IMAGE)
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(value: List<Post>) {
                _data.postValue(FeedModel(posts = value, empty = value.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun save() {
        edited.value?.let {
            repository.saveAsync(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(value: Post) {
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(saveError = true))

                }
            })
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        repository.likeByIdAsync(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(value: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts.orEmpty()
                            .map { if (it.id == id) value else it }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(likeError = true))
            }
        })
    }

    fun unlikeById(id: Long) {
        repository.unlikeByIdAsync(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(value: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts.orEmpty()
                            .map { if (it.id == id) value else it }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(likeError = true))
            }
        })
    }

    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        repository.removeByIdAsync(id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(value: Unit) {

                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id }
                    )
                )
            }

            override fun onError(e: Exception) {
       //         _data.postValue(_data.value?.copy(posts = old))
                _data.postValue(FeedModel(removeError = true))

            }
        })
    }
}

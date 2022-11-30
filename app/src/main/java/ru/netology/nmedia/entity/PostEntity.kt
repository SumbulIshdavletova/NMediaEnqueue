package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val authorAvatar: String,
    var attachment: Image,
) {
    fun toDto() =
        Post(id, author, content, published, authorAvatar, likedByMe, likes,
            attachment)

    companion object {
        fun fromDto(dto: Post) =
            dto.attachment?.let {
                PostEntity(
                    dto.id,
                    dto.author,
                    dto.content,
                    dto.published,
                    dto.likedByMe,
                    dto.likes,
                    dto.authorAvatar,
                    it
                )
            }
    }
}




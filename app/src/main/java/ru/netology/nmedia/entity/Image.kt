package ru.netology.nmedia.entity

import ru.netology.nmedia.enumeration.AttachmentType

data class Image(
    val url: String,
    val description: String?,
    val type: AttachmentType,
){}
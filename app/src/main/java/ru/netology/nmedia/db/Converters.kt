package ru.netology.nmedia.db

import androidx.room.TypeConverter
import ru.netology.nmedia.entity.Image
import ru.netology.nmedia.enumeration.AttachmentType

class Converters {

    @TypeConverter
    fun fromImage(image: Image): String {
        return image.url
    }

    @TypeConverter
    fun toImage(url: String): Image {
        return Image(url, "", AttachmentType.IMAGE)
    }
}
package com.example.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Activity(
    val authorUsername: String,
    val actionType: Int,
    val targetType: Int,
    val targetId: String,
    @BsonId
    val id: String = ObjectId().toString()
)

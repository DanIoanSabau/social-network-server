package com.example.domain.util.extensions

import com.example.domain.util.Token
import com.example.domain.util.Authorization
import com.example.domain.util.RequestParams
import com.example.domain.util.Validation
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*

// authentication

private val JWTPrincipal.requesterId: String
    get() = getClaim(Token.REQUESTER_ID, String::class)
        ?: throw Exception(Authorization.TOKEN_ID)

val ApplicationCall.requesterId: String
    get() = principal<JWTPrincipal>()
        ?. requesterId
        ?: throw Exception(Authorization.TOKEN_ID)

// requests parameters

val ApplicationCall.userId: String
    get() = parameters[RequestParams.OTHER_USER_ID]
        ?: throw Exception(Validation.NULL_PARAM)

val ApplicationCall.pageNumber: Int
    get() = parameters[RequestParams.PAGE_NUMBER]
        ?. toIntOrNull()
        ?: 0

val ApplicationCall.pageSize: Int?
    get() = parameters[RequestParams.PAGE_SIZE]
        ?. toIntOrNull()

val ApplicationCall.postId: String
    get() = parameters[RequestParams.POST_ID]
        ?: throw Exception(Validation.NULL_PARAM)

val ApplicationCall.username: String
    get() = parameters[RequestParams.USERNAME_RGX]
        ?: throw Exception(Validation.NULL_PARAM)

// functions

suspend inline fun <reified T : Any> ApplicationCall.receive(): T =
    this.receiveOrNull() ?: throw Exception(Validation.NULL_REQUEST)

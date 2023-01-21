package com.justai.gramlin.scenario.utils

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

typealias RequestBuilder = HttpRequestBuilder.() -> Unit

object Http {
    val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    inline fun <reified T> request(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.request<T>(url, builder)
    }

    inline fun <reified T> get(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.get<T>(url, builder)
    }

    inline fun <reified T> post(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.post<T>(url, builder)
    }

    inline fun <reified T> put(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.put<T>(url, builder)
    }

    inline fun <reified T> delete(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.delete<T>(url, builder)
    }

    inline fun <reified T> patch(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.patch<T>(url, builder)
    }

    inline fun <reified T> options(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.options<T>(url, builder)
    }

    inline fun <reified T> head(url: String, crossinline builder: RequestBuilder = {}) = runBlocking {
        client.head<T>(url, builder)
    }

    fun request(url: String, builder: RequestBuilder = {}) = request<HttpResponse>(url, builder)

    fun get(url: String, builder: RequestBuilder = {}) = get<HttpResponse>(url, builder)

    fun post(url: String, builder: RequestBuilder = {}) = post<HttpResponse>(url, builder)

    fun put(url: String, builder: RequestBuilder = {}) = put<HttpResponse>(url, builder)

    fun delete(url: String, builder: RequestBuilder = {}) = delete<HttpResponse>(url, builder)

    fun patch(url: String, builder: RequestBuilder = {}) = patch<HttpResponse>(url, builder)

    fun options(url: String, builder: RequestBuilder = {}) = options<HttpResponse>(url, builder)

    fun head(url: String, builder: RequestBuilder = {}) = head<HttpResponse>(url, builder)
}

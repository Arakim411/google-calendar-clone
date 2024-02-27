package com.arakim.googlecalendarclone.util.compose

import androidx.compose.runtime.Immutable

@Immutable
class ImmutableList<out T>(val value: List<T>) : List<T> by value {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is ImmutableList<*>) return false
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
}

fun <T> immutableListOf(): ImmutableList<T> =
    ImmutableList(emptyList())

fun <T> immutableListOf(vararg elements: T): ImmutableList<T> =
    ImmutableList(if (elements.isNotEmpty()) elements.asList() else emptyList())

fun <T> immutableListOf(list: List<T>): ImmutableList<T> =
    ImmutableList(list.ifEmpty { emptyList() })

inline fun <T, R> ImmutableList<T>.map(transform: (T) -> R): ImmutableList<R> {
    return ImmutableList(value.map { transform(it) })
}

inline fun <T, R> Iterable<T>.mapToImmutable(transform: (T) -> R): ImmutableList<R> {
    return ImmutableList(this.map { transform(it) })
}

@Suppress("UNCHECKED_CAST")
fun <T, R> Iterable<T>.toImmutableList(): ImmutableList<R> {
    return mapToImmutable { it as R }
}

fun <T> ImmutableList<T>.getWithNewItem(newItem: T): ImmutableList<T> {
    return ImmutableList(value + newItem)
}

fun <T> ImmutableList<T>.getWithNewItems(newItems: List<T>): ImmutableList<T> {
    val newList = value.toMutableList().apply {
        addAll(newItems)
    }
    return ImmutableList(newList)
}
package androidx.lifecycle

fun <T> ViewModel.setTagIfAbsent(key: String, value: T): T = setTagIfAbsent(key, value)

fun <T> ViewModel.getTag(key: String): T? = getTag(key)
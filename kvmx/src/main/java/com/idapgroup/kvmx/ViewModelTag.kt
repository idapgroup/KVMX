package androidx.lifecycle

/**
 * Public version of [setTagIfAbsent][androidx.lifecycle.ViewModel.setTagIfAbsent]
 */
fun <T> ViewModel.setTagIfAbsent(key: String, value: T): T = setTagIfAbsent(key, value)

/**
 * Public version of [getTag][androidx.lifecycle.ViewModel.getTag]
 */
fun <T> ViewModel.getTag(key: String): T? = getTag(key)
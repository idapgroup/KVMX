package com.idapgroup.kvmx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.getTag
import androidx.lifecycle.setTagIfAbsent
import org.junit.Test

const val PRIMITIVE_KEY = "test_key"
const val OBJECT_KEY = "object_key"
const val CHANGE_KEY = "change_key"
const val MISSING_KEY = "missing_key"

class GetSetTagTest {
    private val viewModel = object : ViewModel() {}

    @Test
    fun `tag is setting for primitive`() {
        val primitive = 4
        viewModel.setTagIfAbsent(PRIMITIVE_KEY, primitive)
        assert(viewModel.getTag<Int>(PRIMITIVE_KEY) == primitive)
    }

    @Test
    fun `tag is setting for object`() {
        data class TestObject(
            val field1: Int,
            val field2: String
        )
        val tagObject = TestObject(0, "")
        viewModel.setTagIfAbsent(OBJECT_KEY, tagObject)
        assert(viewModel.getTag<TestObject>(OBJECT_KEY) == tagObject)
    }

    @Test
    fun `tag cannot be changed`() {
        val initialValue = "initial"
        val changedValue = "changed"
        viewModel.setTagIfAbsent(CHANGE_KEY, initialValue)
        viewModel.setTagIfAbsent(CHANGE_KEY, changedValue)
        assert(viewModel.getTag<String>(CHANGE_KEY) == initialValue)
    }

    @Test
    fun `non existent tag returns null`() {
        assert(viewModel.getTag<Any>(MISSING_KEY) == null)
    }
}
package com.idapgroup.kvmx

import androidx.lifecycle.getTag
import androidx.lifecycle.setTagIfAbsent
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class ViewModelTagTest {
    private lateinit var viewModel: TestViewModel

    @Before
    fun setup() {
        viewModel = TestViewModel()
    }

    @Test
    fun `set primitive value to tag `() {
        val key = "key"
        val value = 4
        viewModel.setTagIfAbsent(key, value)
        assertEquals(viewModel.getTag<Int>(key), value)
    }

    @Test
    fun `set object value to tag`() {
        val key = "key"
        val value = Pair(0, "")
        viewModel.setTagIfAbsent(key, value)
        assertEquals(viewModel.getTag<Pair<Int, String>>(key), value)
    }

    @Test
    fun `can not change tag value`() {
        val key = "key"
        val initialValue = "initial"
        val changedValue = "changed"
        viewModel.setTagIfAbsent(key, initialValue)
        viewModel.setTagIfAbsent(key, changedValue)
        assertEquals(viewModel.getTag<String>(key), initialValue)
    }

    @Test
    fun `return null for tag that is not set yet`() {
        val key = "key"
        assertNull(viewModel.getTag<Any>(key))
    }
}
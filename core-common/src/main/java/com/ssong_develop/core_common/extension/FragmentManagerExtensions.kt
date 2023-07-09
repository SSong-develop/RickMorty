package com.ssong_develop.core_common.extension

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Allows calls like
 *
 * `supportFragmentManager.inTransaction { add(...) }`
 */
fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

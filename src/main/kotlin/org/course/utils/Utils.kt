package org.course.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Field
import kotlin.reflect.KProperty1

inline fun <reified T, reified A> T.reAssignVal(fieldName: KProperty1<T, A>, replace: (A) -> A): T {
    val f = recurseField(T::class.java, fieldName.name)
    f.isAccessible = true
    val existingValue = f.get(this)
    f.set(this, replace(existingValue as A))
    return this
}

fun recurseField(clazz: Class<*>, fieldName: String): Field =
        if (clazz == Any::class.java) throw IllegalArgumentException("property $fieldName not found in $clazz") else {
            clazz.declaredFields.find { it.name == fieldName } ?: recurseField(clazz.superclass, fieldName)
        }


inline val <reified T>  T.logger:Logger
    get() = LoggerFactory.getLogger(T::class.java)

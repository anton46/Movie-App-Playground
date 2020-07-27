package com.danger.kotlin.plugin.test.model

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

typealias Mapper<I, O> = (I) -> O
typealias targetParameterSupplier<O> = () -> O

class DataMapper<I : Any, O : Any>(private val inType: KClass<I>, private val outType: KClass<O>) : Mapper<I, O> {

    companion object {
        inline operator fun <reified I : Any, reified O : Any> invoke() = DataMapper(I::class, O::class)

        fun <I : Any, O : Any> arrayToListMapper(mapper: Mapper<I, O>) = object : Mapper<Array<I>, List<O>> {
            override fun invoke(data: Array<I>): List<O> = data.map(mapper).toList()
        }

        fun <T : Any> arrayToListMapper() = object : Mapper<Array<T>, List<T>> {
            override fun invoke(data: Array<T>): List<T> = data.toList()
        }
    }

    val fieldMappers = mutableMapOf<String, Mapper<Any, Any>>()
    private val targetParameterProviders = mutableMapOf<String, targetParameterSupplier<Any>>()
    private val outConstructor = outType.primaryConstructor!!
    private val inPropertiesByName by lazy { inType.memberProperties.associateBy { it.name } }

    private fun argFor(parameter: KParameter, data: I): Any? {
        // get value from input data or apply a default value to the target class
        val value = inPropertiesByName[parameter.name]?.get(data) ?: return targetParameterProviders[parameter.name]?.invoke()
        // if a special mapper is registered, use it, otherwise keep value
        return fieldMappers[parameter.name]?.invoke(value) ?: value
    }

    inline fun <reified S : Any, reified T : Any> register(parameterName: String, crossinline mapper: Mapper<S, T>): DataMapper<I, O> = apply {
        this.fieldMappers[parameterName] = object : Mapper<Any, Any> {
            override fun invoke(data: Any): Any = mapper.invoke(data as S)
        }
    }

    inline fun <reified C : Any, reified S : Any, reified T : Any> register(property: KProperty1<C, S>, crossinline mapper: Mapper<S, T>): DataMapper<I, O> = apply {
        this.fieldMappers[property.name] = object : Mapper<Any, Any> {
            override fun invoke(data: Any): Any = mapper.invoke(data as S)
        }
    }

    fun <T : Any> targetParameterSupplier(parameterName: String, mapper: targetParameterSupplier<T>): DataMapper<I, O> = apply {
        this.targetParameterProviders[parameterName] = object : targetParameterSupplier<Any> {
            override fun invoke(): Any = mapper.invoke()
        }
    }

    fun <S : Any, T : Any> targetParameterSupplier(property: KProperty1<S, Any?>, mapper: targetParameterSupplier<T>): DataMapper<I, O> = apply {
        this.targetParameterProviders[property.name] = object : targetParameterSupplier<Any> {
            override fun invoke(): Any = mapper.invoke()
        }
    }

    override fun invoke(data: I): O = with(outConstructor) {
        callBy(parameters.associateWith { argFor(it, data) })
    }
}

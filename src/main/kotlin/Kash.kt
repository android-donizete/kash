import kotlin.reflect.KClass

class MutableKash : Kash() {
    fun modules(vararg builder: Builder<MutableKash>) = builder.forEach { it(this) }

    fun <T : Any> push(clazz: KClass<T>, producer: Producer<T>) {
        producers[clazz] = producer
    }

    inline fun <reified T : Any> push(
        noinline producer: Producer<T>
    ) = push(T::class, producer)

    inline fun <reified Type : Any> single(
        noinline producer: Producer<Type>
    ) = push(producer.toSingle())

    inline fun <reified Type : Any> factory(
        noinline producer: Producer<Type>
    ) = push(producer)

    inline fun <reified Type : Any> singleOf(
        noinline fn: Function0<Type>
    ) = single(fn)

    inline fun <reified A, reified Type : Any> singleOf(
        noinline fn: Function1<A, Type>
    )  = single<Type> { fn(get()) }

    inline fun <reified A, reified B, reified Type : Any> singleOf(
        noinline fn: Function2<A, B, Type>
    )  = single<Type> { fn(get(), get()) }

    inline fun <reified A, reified B, reified C, reified Type : Any> singleOf(
        noinline fn: Function3<A, B, C, Type>
    )  = single<Type> { fn(get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified Type : Any> singleOf(
        noinline fn: Function4<A, B, C, D, Type>
    )  = single<Type> { fn(get(), get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified E, reified Type : Any> singleOf(
        noinline fn: Function5<A, B, C, D, E, Type>,
    )  = single<Type> { fn(get(), get(), get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified E, reified F, reified Type : Any> singleOf(
        noinline fn: Function6<A, B, C, D, E, F, Type>,
    )  = single<Type> { fn(get(), get(), get(), get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified E, reified F, reified G, reified Type : Any> singleOf(
        noinline fn: Function7<A, B, C, D, E, F, G, Type>,
    )  = single<Type> { fn(get(), get(), get(), get(), get(), get(), get()) }

    inline fun <reified Type : Any> factoryOf(
        noinline fn: Function0<Type>,
    )  = factory<Type>(fn)

    inline fun <reified A, reified Type : Any> factoryOf(
        noinline fn: Function1<A, Type>,
    )  = factory<Type> { fn(get()) }

    inline fun <reified A, reified B, reified Type : Any> factoryOf(
        noinline fn: Function2<A, B, Type>,
    )  = factory<Type> { fn(get(), get()) }

    inline fun <reified A, reified B, reified C, reified Type : Any> factoryOf(
        noinline fn: Function3<A, B, C, Type>,
    )  = factory<Type> { fn(get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified Type : Any> factoryOf(
        noinline fn: Function4<A, B, C, D, Type>,
    )  = factory<Type> { fn(get(), get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified E, reified Type : Any> factoryOf(
        noinline fn: Function5<A, B, C, D, E, Type>,
    )  = factory<Type> { fn(get(), get(), get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified E, reified F, reified Type : Any> factoryOf(
        noinline fn: Function6<A, B, C, D, E, F, Type>,
    )  = factory<Type> { fn(get(), get(), get(), get(), get(), get()) }

    inline fun <reified A, reified B, reified C, reified D, reified E, reified F, reified G, reified Type : Any> factoryOf(
        noinline fn: Function7<A, B, C, D, E, F, G, Type>,
    )  = factory<Type> { fn(get(), get(), get(), get(), get(), get(), get()) }
}

abstract class Kash {
    protected val producers = hashMapOf<KClass<*>, Producer<Any>>()

    private val history = linkedSetOf<KClass<*>>()

    inline operator fun <reified T : Any> invoke(): T = get()

    inline fun <reified T : Any> get(): T = get(T::class)

    fun <T : Any> get(clazz: KClass<T>): T {
        if (clazz in history) {
            error(
                """
                We have found a circular dependency.
                Please revise your dependencies:
                ${history(clazz)}
                """
            )
        }
        history += clazz
        val producer = producers[clazz] ?: throw Throwable("$clazz not found")
        val dependency = producer() as T
        history -= clazz
        return dependency
    }

    private fun history(clazz: KClass<*>) = StringBuilder().apply {
        history.forEach {
            append("${it.simpleName} -> ")
        }
        append("${clazz.simpleName}")
    }.toString()
}
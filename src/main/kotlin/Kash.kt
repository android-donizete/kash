import kotlin.reflect.KClass

class MutableKash : Kash() {
    fun module(builder: Builder<MutableKash>) = builder(this)

    fun modules(vararg builders: Builder<MutableKash>) = builders.forEach(::module)

    inline fun <reified T : Any> single(
        noinline producer: Producer<T>
    ) = push(producer.toSingle())

    inline fun <reified T : Any> factory(
        noinline producer: Producer<T>
    ) = push(producer)

    inline fun <reified T : Any> singleOf(
        noinline fn: Function0<T>
    ) = push({ fn() }.toSingle())

    inline fun <reified A, reified T : Any> singleOf(
        noinline fn: Function1<A, T>,
    ) = push({ fn(get()) }.toSingle())

    inline fun <reified A, reified B, reified T : Any> singleOf(
        noinline fn: Function2<A, B, T>,
    ) = push({ fn(get(), get()) }.toSingle())

    inline fun <reified A, reified B, reified C, reified T : Any> singleOf(
        noinline fn: Function3<A, B, C, T>,
    ) = push({ fn(get(), get(), get()) }.toSingle())

    inline fun <reified T : Any> factoryOf(
        noinline fn: Function0<T>
    ) = push(fn)

    inline fun <reified A, reified T : Any> factoryOf(
        noinline fn: Function1<A, T>,
    ) = push { fn(get()) }

    inline fun <reified A, reified B, reified T : Any> factoryOf(
        noinline fn: Function2<A, B, T>,
    ) = push { fn(get(), get()) }

    inline fun <reified A, reified B, reified C, reified T : Any> factoryOf(
        noinline fn: Function3<A, B, C, T>,
    ) = push { fn(get(), get(), get()) }

    inline fun <reified T : Any> push(noinline producer: Producer<T>) = push(T::class, producer)

    fun <T : Any> push(clazz: KClass<T>, producer: Producer<T>) {
        producers[clazz] = producer
    }
}

abstract class Kash {
    protected val producers = hashMapOf<KClass<*>, Producer<*>>()

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
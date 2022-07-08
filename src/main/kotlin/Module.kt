import kotlin.reflect.KClass

class Module(
    private val kash: Kash
) {
    val dependencies = hashMapOf<KClass<*>, Producer<*>>()

    inline fun <reified T> single(noinline producer: Producer<T>) {
        dependencies[T::class] = SingleProducer(producer)
    }

    inline fun <reified T> factory(noinline producer: Producer<T>) {
        dependencies[T::class] = producer
    }

    inline fun <reified T> singleOf(
        noinline fn: Function0<T>
    ) {
        dependencies[T::class] = SingleProducer {
            fn()
        }
    }

    inline fun <reified A, reified T> singleOf(
        noinline fn: Function1<A, T>,
    ) {
        dependencies[T::class] = SingleProducer {
            fn(get())
        }
    }

    inline fun <reified A, reified B, reified T> singleOf(
        noinline fn: Function2<A, B, T>,
    ) {
        dependencies[T::class] = SingleProducer {
            fn(get(), get())
        }
    }

    inline fun <reified A, reified B, reified C, reified T> singleOf(
        noinline fn: Function3<A, B, C, T>,
    ) {
        dependencies[T::class] = SingleProducer {
            fn(get(), get(), get())
        }
    }

    inline fun <reified T> factoryOf(
        noinline fn: Function0<T>
    ) {
        dependencies[T::class] = fn
    }

    inline fun <reified A, reified T> factoryOf(
        noinline fn: Function1<A, T>,
    ) {
        dependencies[T::class] = {
            fn(get())
        }
    }

    inline fun <reified A, reified B, reified T> factoryOf(
        noinline fn: Function2<A, B, T>,
    ) {
        dependencies[T::class] = {
            fn(get(), get())
        }
    }

    inline fun <reified A, reified B, reified C, reified T> factoryOf(
        noinline fn: Function3<A, B, C, T>,
    ) {
        dependencies[T::class] = {
            fn(get(), get(), get())
        }
    }

    inline fun <reified T : Any> get(): T = get(T::class)
    fun <T : Any> get(kClass: KClass<T>): T = kash.get(kClass)
}

class Bind<T>(
    producer: Producer<T>
) {
    fun <A, T> bind()
    where T : A {

    }
}
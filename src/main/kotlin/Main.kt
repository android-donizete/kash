import kotlin.reflect.KClass

typealias Producer<T> = () -> T

interface ProducerWrapper<T> {
    fun get(): T
}

class SingleProducerWrapper<T>(
    private val producer: Producer<T>
): ProducerWrapper<T> {
    private var cached: T? = null

    override fun get(): T = cached ?: producer().also {
        cached = it
    }
}

class Module {
    val hashMap = hashMapOf<KClass<*>, ProducerWrapper<*>>()

    inline fun <reified T> single(noinline producer: Producer<T>) {
        hashMap[T::class] = SingleProducerWrapper(producer)
    }
}

class Kash {
    val hashMap = hashMapOf<KClass<*>, ProducerWrapper<*>>()

    fun module(module: Module) {
        module.hashMap.forEach {(key, value) ->
            hashMap[key] = value
        }
    }

    inline fun <reified T> get(): T = hashMap[T::class]!!.get() as T
}

fun startKash(builder: Kash.() -> Unit): Kash =
    Kash().apply(builder)

fun module(builder: Module.() -> Unit): Module =
    Module().apply(builder)

class Car {
    operator fun invoke() {
        println("I get a car!!!")
    }
}

fun main(args: Array<String>) {
    val module = module {
        single { Car() }
    }

    val kash = startKash {
        module(module)
    }

    val car1 = kash.get<Car>()
    val car2: Car = kash.get()

    car1()
    car2()

    if (car1 === car2) {
        println("I've got the same car!!!")
    }
}
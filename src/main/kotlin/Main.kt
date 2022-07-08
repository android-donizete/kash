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

class FactoryProducerWrapper<T>(
    private val producer: Producer<T>
): ProducerWrapper<T> {
    override fun get(): T = producer()
}

class Module {
    val hashMap = hashMapOf<KClass<*>, ProducerWrapper<*>>()

    inline fun <reified T> single(noinline producer: Producer<T>) {
        hashMap[T::class] = SingleProducerWrapper(producer)
    }

    inline fun <reified T> factory(noinline producer: Producer<T>) {
        hashMap[T::class] = FactoryProducerWrapper(producer)
    }
}

class Kash {
    val hashMap = hashMapOf<KClass<*>, ProducerWrapper<*>>()

    fun module(module: Module) = hashMap.putAll(module.hashMap)

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

class Engine {
    operator fun invoke() {
        println("Vrummmmmm!!!")
    }
}

fun main(args: Array<String>) {
    val module = module {
        single { Car() }
        factory { Engine() }
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

    val engine1 = kash.get<Engine>()
    val engine2 = kash.get<Engine>()

    engine1()
    engine2()

    if (engine1 !== engine2) {
        println("I've got a new engine!!!")
    }
}
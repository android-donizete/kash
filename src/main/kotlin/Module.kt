import kotlin.reflect.KClass

class Module(
    private val kash: Kash
){
    val dependencies = hashMapOf<KClass<*>, Producer<*>>()

    inline fun <reified T> single(noinline producer: Producer<T>) {
        dependencies[T::class] = SingleProducer(producer)
    }

    inline fun <reified T> factory(noinline producer: Producer<T>) {
        dependencies[T::class] = producer
    }

    inline fun <reified T: Any> get(): T = get(T::class)
    fun <T : Any> get(kClass: KClass<T>): T = kash.get(kClass)
}
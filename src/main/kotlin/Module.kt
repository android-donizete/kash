import kotlin.reflect.KClass

class Module(
    private val kash: Kash
){
    val dependencies = hashMapOf<KClass<*>, ProducerWrapper<*>>()

    inline fun <reified T> single(noinline producer: Producer<T>) {
        dependencies[T::class] = SingleProducerWrapper(producer)
    }

    inline fun <reified T> factory(noinline producer: Producer<T>) {
        dependencies[T::class] = FactoryProducerWrapper(producer)
    }

    inline fun <reified T: Any> get(): T = get(T::class)
    fun <T : Any> get(kClass: KClass<T>): T = kash.get(kClass)
}
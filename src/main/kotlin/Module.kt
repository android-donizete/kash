import kotlin.reflect.KClass

typealias ModuleBuilder = Builder<Module>

class Module(
    private val kash: Kash
){
    val hashMap = hashMapOf<KClass<*>, ProducerWrapper<*>>()

    inline fun <reified T> single(noinline producer: Producer<T>) {
        hashMap[T::class] = SingleProducerWrapper(producer)
    }

    inline fun <reified T> factory(noinline producer: Producer<T>) {
        hashMap[T::class] = FactoryProducerWrapper(producer)
    }

    inline fun <reified T: Any> get(): T = get(T::class)
    fun <T : Any> get(kClass: KClass<T>): T = kash.get(kClass)
}
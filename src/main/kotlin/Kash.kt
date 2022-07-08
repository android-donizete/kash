import kotlin.reflect.KClass

class Kash {
    private val hashMap = hashMapOf<KClass<*>, ProducerWrapper<*>>()

    fun module(moduleBuilder: ModuleBuilder) {
        val module = Module(this)
        moduleBuilder(module)
        hashMap.putAll(module.hashMap)
    }

    fun <T: Any> get(kClass: KClass<T>): T {
        println("Searching for $kClass")
        val producer = hashMap[kClass] ?: throw Throwable("$kClass not found")
        return producer.get() as T
    }
    inline fun <reified T: Any> get(): T = get(T::class)
}
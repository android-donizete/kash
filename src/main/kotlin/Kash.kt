import kotlin.reflect.KClass

class Kash {
    private val hashMap = hashMapOf<KClass<*>, ProducerWrapper<*>>()
    private val hashSet = linkedSetOf<KClass<*>>()

    fun module(moduleBuilder: ModuleBuilder) {
        val module = Module(this)
        moduleBuilder(module)
        hashMap.putAll(module.hashMap)
    }

    fun <T : Any> get(kClass: KClass<T>): T {
        if (kClass in hashSet) {
            error(
            """
                We have found a recursive dependency
                Please revise your dependencies:
                ${beautyDependencies(kClass)}
            """
            )
        }
        hashSet += kClass
        val producer = hashMap[kClass] ?: throw Throwable("$kClass not found")
        val clazz = producer.get() as T
        hashSet -= kClass
        return clazz
    }

    private fun beautyDependencies(kClass: KClass<*>): String {
        val builder = StringBuilder()
        hashSet.forEach {
            builder.append("${it.simpleName} -> ")
        }
        builder.append("${kClass.simpleName}")
        return builder.toString()
    }

    inline fun <reified T : Any> get(): T = get(T::class)
}
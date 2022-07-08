import kotlin.reflect.KClass

class Kash {
    private val dependencies = hashMapOf<KClass<*>, ProducerWrapper<*>>()
    private val history = linkedSetOf<KClass<*>>()

    fun module(moduleBuilder: Builder<Module>) {
        val module = Module(this)
        moduleBuilder(module)
        dependencies.putAll(module.dependencies)
    }

    fun <T : Any> get(kClass: KClass<T>): T {
        if (kClass in history) {
            error(
            """
                We have found a recursive dependency
                Please revise your dependencies:
                ${beautyHistory(kClass)}
            """
            )
        }
        history += kClass
        val producer = dependencies[kClass] ?: throw Throwable("$kClass not found")
        val clazz = producer.get() as T
        history -= kClass
        return clazz
    }

    private fun beautyHistory(kClass: KClass<*>): String {
        val builder = StringBuilder()
        history.forEach {
            builder.append("${it.simpleName} -> ")
        }
        builder.append("${kClass.simpleName}")
        return builder.toString()
    }

    inline fun <reified T : Any> get(): T = get(T::class)
}
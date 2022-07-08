import kotlin.reflect.KClass

class Kash {
    private val dependencies = hashMapOf<KClass<*>, Producer<*>>()
    private val history = linkedSetOf<KClass<*>>()

    fun module(builder: Builder<Module>) {
        val module = Module(this)
        builder(module)
        dependencies.putAll(module.dependencies)
    }

    inline operator fun <reified T : Any> invoke(): T = get()
    inline fun <reified T : Any> get(): T = get(T::class)

    fun <T : Any> get(kClass: KClass<T>): T {
        if (kClass in history) {
            error(
            """
                We have found a circular dependency.
                Please revise your dependencies:
                ${beautyHistory(kClass)}
            """
            )
        }
        history += kClass
        val producer = dependencies[kClass] ?: throw Throwable("$kClass not found")
        val clazz = producer() as T
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
}
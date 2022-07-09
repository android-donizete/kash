import kotlin.reflect.KClass

typealias Producer<Type> = Function0<Type>
typealias Builder<Type> = Type.() -> Unit

fun startKash(builder: Builder<MutableKash>): Kash =
    MutableKash().apply(builder)

fun module(builder: Builder<MutableKash>) =
    builder

class SingleProducer<Type>(
    private val producer: Producer<Type>
) : Producer<Type> {
    private var cached: Type? = null

    override fun invoke(): Type = cached ?: producer().also {
        cached = it
    }
}

//inline to eliminate function call overhead
inline fun <Type> Producer<Type>.toSingle(): Producer<Type> =
    SingleProducer(this)

//I don't even know what am I doing
data class Binds<out Type : Any>(
    val clazz: KClass<@UnsafeVariance Type>
)

class BindProducer<Bind, Type : Bind>(
    private val producer: Producer<Type>
) : Producer<Bind> {
    override fun invoke(): Bind = producer()
}

//inline to eliminate function call overhead
inline fun <Bind, Type : Bind> Producer<Type>.toBind(): Producer<Bind> =
    BindProducer(this)
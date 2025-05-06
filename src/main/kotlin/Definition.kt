typealias Producer<Type> = Function0<Type>
typealias Builder<Type> = Type.() -> Unit

fun startKash(
    builder: Builder<MutableKash>
): Kash = MutableKash().apply(builder)

fun module(
    builder: Builder<MutableKash>
) = builder

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
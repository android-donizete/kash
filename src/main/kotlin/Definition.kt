typealias Producer<T> = Function0<T>
typealias Builder<T> = T.() -> Unit

fun startKash(builder: Builder<Kash>) =
    Kash().apply(builder)

fun module(builder: Builder<Kash>) =
    builder

//inline to eliminate function call overhead
inline fun <T> Producer<T>.toSingle() = SingleProducer(this)

class SingleProducer<T>(
    private val producer: Producer<T>
) : Producer<T> {
    private var cached: T? = null

    override fun invoke(): T = cached ?: producer().also {
        cached = it
    }
}
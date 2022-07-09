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

class Binds<out Type>

class BindProducer<Bind, Type : Bind>(
    private val producer: Producer<Type>,
    //only for type inference
    private val unused: Binds<Bind>
) : Producer<Bind> {
    override fun invoke(): Bind = producer()
}

//inline to eliminate function call overhead
inline fun <Bind, Type : Bind> Producer<Type>.toBind(binds: Binds<Bind>): Producer<Bind> =
    BindProducer(this, binds)
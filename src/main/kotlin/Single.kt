class SingleProducer<Type>(
    private val producer: Producer<Type>
) : Producer<Type> {
    private var cached: Type? = null

    override fun invoke(): Type = cached ?: producer().also {
        cached = it
    }
}

//inline to eliminate function call overhead
inline fun <Type> Producer<Type>.toSingle(): Producer<Type> = SingleProducer(this)
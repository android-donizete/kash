class SingleProducer<T>(
    private val producer: Producer<T>
) : Producer<T> {
    private var cached: T? = null

    override fun invoke(): T = cached ?: producer().also {
        cached = it
    }
}
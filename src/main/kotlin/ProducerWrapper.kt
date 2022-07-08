typealias Producer<T> = () -> T

interface ProducerWrapper<T> {
    fun get(): T
}

class SingleProducerWrapper<T>(
    private val producer: Producer<T>
) : ProducerWrapper<T> {
    private var cached: T? = null

    override fun get(): T = cached ?: producer().also {
        cached = it
    }
}

class FactoryProducerWrapper<T>(
    private val producer: Producer<T>
) : ProducerWrapper<T> {
    override fun get(): T = producer()
}
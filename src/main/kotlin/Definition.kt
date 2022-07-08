typealias Producer<T> = () -> T
typealias Builder<T> = T.() -> Unit

fun startKash(builder: Builder<Kash>): Kash =
    Kash().apply(builder)

fun module(builder: Builder<Module>) = builder
typealias Producer<T> = Function0<T>
typealias Builder<T> = T.() -> Unit

fun startKash(builder: Builder<Kash>) =
    Kash().apply(builder)

fun module(builder: Builder<Module>) =
    builder
typealias Producer<Type> = Function0<Type>
typealias Builder<Type> = Type.() -> Unit

fun startKash(
    builder: Builder<MutableKash>
): Kash = MutableKash().apply(builder)

fun module(
    builder: Builder<MutableKash>
) = builder
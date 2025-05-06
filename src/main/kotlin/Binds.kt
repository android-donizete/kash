import kotlin.reflect.KClass

inline infix fun <A, T, reified Bind : Any> Function1<A, Bind>.binds(
    to: KClass<Bind> = Bind::class
): Function1<A, Bind> where T : Bind = this

inline infix fun <A, B, T, reified Bind : Any> Function2<A, B, Bind>.binds(
    to: KClass<Bind> = Bind::class
): Function2<A, B, Bind> where T : Bind = this

inline infix fun <A, B, C, T, reified Bind : Any> Function3<A, B, C, Bind>.binds(
    to: KClass<Bind> = Bind::class
): Function3<A, B, C, Bind> where T : Bind = this

inline infix fun <A, B, C, D, T, reified Bind : Any> Function4<A, B, C, D, Bind>.binds(
    to: KClass<Bind> = Bind::class
): Function4<A, B, C, D, Bind> where T : Bind = this

inline infix fun <A, B, C, D, E, T, reified Bind : Any> Function5<A, B, C, D, E, Bind>.binds(
    to: KClass<Bind> = Bind::class
): Function5<A, B, C, D, E, Bind> where T : Bind = this

inline infix fun <A, B, C, D, E, F, T, reified Bind : Any> Function6<A, B, C, D, E, F, Bind>.binds(
    to: KClass<Bind> = Bind::class
): Function6<A, B, C, D, E, F, Bind> where T : Bind = this

inline infix fun <A, B, C, D, E, F, G, T, reified Bind : Any> Function7<A, B, C, D, E, F, G, Bind>.binds(
    to: KClass<Bind> = Bind::class
): Function7<A, B, C, D, E, F, G, Bind> where T : Bind = this
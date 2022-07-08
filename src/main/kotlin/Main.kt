typealias Builder<T> = T.() -> Unit

fun startKash(builder: Builder<Kash>): Kash =
    Kash().apply(builder)

fun module(builder: Builder<Module>) = builder

class Car(
    private val engine: Engine
) {
    operator fun invoke() {
        println("I get a car!!!")
    }
}

class Engine {
    operator fun invoke() {
        println("Vrummmmmm!!!")
    }
}

fun main(args: Array<String>) {
    val module = module {
        factory { Engine() }
        single { Car(get()) }
    }

    val kash = startKash {
        module(module)
    }

    val car1 = kash.get<Car>()
    val car2: Car = kash.get()

    car1()
    car2()

    if (car1 === car2) {
        println("I've got the same car!!!")
    }

    val engine1 = kash.get<Engine>()
    val engine2 = kash.get<Engine>()

    engine1()
    engine2()

    if (engine1 !== engine2) {
        println("I've got a new engine!!!")
    }
}
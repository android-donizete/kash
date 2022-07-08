class Car(
    private val engine: Engine
) {
    operator fun invoke() {
        println("I get a car!!!")
    }
}

class Engine(
    private val another: Another
) {
    operator fun invoke() {
        println("Vrummmmmm!!!")
    }
}

class Another(
    private val car: Car
)

fun main(args: Array<String>) {
    val module = module {
        factory { Engine(get()) }
        single { Car(get()) }
        factory { Another(get()) }
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
class Car(
    private val engine: Engine
) {
    operator fun invoke() {
        println("I get a car!!!")
        println("I'll turn on engine: ")
        engine()
    }
}

class Engine {
    operator fun invoke() {
        println("Vrummmmmm!!!")
    }
}

fun main(args: Array<String>) {
    val module1 = module {
        factory { Engine() }
    }

    val module2 = module {
        single { Car(get()) }
    }

    val kash = startKash {
        module(module1)
        module(module2)
    }

    val car1 = kash.get<Car>()
    val car2 = kash.get<Car>()

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
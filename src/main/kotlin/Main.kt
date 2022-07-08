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
        factoryOf(::Engine)
    }

    val module2 = module {
        singleOf(::Car)
    }

    val kash = startKash {
        module(module1)
        module(module2)
    }

    val car1 = kash<Car>()
    val car2 = kash<Car>()

    car1()
    car2()

    if (car1 === car2) {
        println("I've got the same car!!!")
    }

    val engine1 = kash<Engine>()
    val engine2 = kash<Engine>()

    engine1()
    engine2()

    if (engine1 !== engine2) {
        println("I've got a new engine!!!")
    }
}
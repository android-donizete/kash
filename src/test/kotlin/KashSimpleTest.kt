import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class KashSimpleTest {
    @Test
    fun shouldBeAbleToInjectSomething() {
        class Car {
            operator fun invoke() {
                println("I'm a simple car")
            }
        }

        val module = module {
            factory { Car() }
        }

        val kash = startKash {
            module(module)
        }

        val car: Car = assertDoesNotThrow(kash::invoke)

        car()
    }

    @Test
    fun shouldBeAbleToInjectNested() {
        class Engine {
            operator fun invoke() {
                println("Vrummm!")
            }
        }

        class Car(
            private val engine: Engine
        ) {
            operator fun invoke() {
                println("I'm turning on")
                engine()
            }
        }

        val module = module {
            factory { Car(get()) }
            factory { Engine() }
        }

        val kash = startKash {
            module(module)
        }

        val car: Car = assertDoesNotThrow(kash::invoke)

        car()
    }

    @Test
    fun shouldBeAbleToDetectCircularDependency() {

        class Engine(
            private val function: Function<Unit>
        ) {
            operator fun invoke() {
                println("Vrummm!")
            }
        }

        class Car(
            private val engine: Engine
        ) {
            operator fun invoke() {
                println("I'm turning on")
                engine()
            }
        }

        class FunctionImpl(
            private val car: Car
        ) : Function<Unit>

        val module = module {
            factory { Car(get()) }
            factory { Engine(get()) }
            factory <Function<Unit>>{ FunctionImpl(get()) }
        }

        val kash = startKash {
            module(module)
        }

        val exception = assertThrows<IllegalStateException> {
            val car = kash<Car>()
            car()
        }

        println(exception)
    }
}
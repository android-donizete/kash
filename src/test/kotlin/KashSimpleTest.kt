import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

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
            modules(module)
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
            modules(module)
        }

        val car: Car = assertDoesNotThrow(kash::invoke)

        car()
    }

    @Test
    fun shouldBeAbleToDetectCircularDependency() {

        abstract class Engine {
            abstract operator fun invoke()
        }

        class Car(
            private val engine: Engine
        ) {
            operator fun invoke() {
                println("I'm turning on")
                engine()
            }
        }

        class IDontCare(
            private val car: Car
        )

        class EngineImpl(
            val idc: IDontCare
        ) : Engine() {
            override fun invoke() {
                println("Vrummm!")
            }
        }

        val module = module {
            factory { Car(get()) }
            factory<Engine> { EngineImpl(get()) }
            factory { IDontCare(get()) }
        }

        val kash = startKash {
            modules(module)
        }

        val exception = assertThrows<IllegalStateException> {
            val car = kash<Car>()
            car()
        }

        println(exception)
        /*
                We have found a circular dependency.
                Please revise your dependencies:
                Car -> Engine -> IDontCare -> Car
        */
        //Beautiful. Isn't it?
    }

    @Test
    fun shouldBeAbleToBindUsingDSLFactory() {
        abstract class Engine {
            abstract operator fun invoke()
        }

        class EngineImpl : Engine() {
            override fun invoke() {
                println("Vrummm!")
            }
        }

        class Car(
            val engine: Engine
        ) {
            operator fun invoke() {
                println("I'm turning on")
                engine()
            }
        }

        val module = module {
            factoryOf<Engine>(::EngineImpl)
            factoryOf(::Car)
        }

        val kash = startKash {
            modules(module)
        }

        val car: Car = assertDoesNotThrow(kash::invoke)

        car()
    }

    @Test
    fun shouldBeAbleToBindUsingDSLSingle() {
        abstract class Engine {
            abstract operator fun invoke()
        }

        class DepA
        class DepB
        class DepC
        class DepD
        class DepE
        class DepF
        class DepG

        class EngineImpl(
            private val depA: DepA,
            private val depB: DepB,
            private val depC: DepC,
            private val depD: DepD,
            private val depE: DepE,
            private val depF: DepF,
            private val depG: DepG
        ) : Engine() {
            override fun invoke() {
                println("Vrummm!")
            }
        }

        val module = module {
            singleOf(::EngineImpl binds Engine::class)
            factoryOf(::DepA)
            factoryOf(::DepB)
            factoryOf(::DepC)
            factoryOf(::DepD)
            singleOf(::DepE)
            singleOf(::DepF)
            singleOf(::DepG)
        }

        val kash = startKash {
            modules(module)
        }

        val engine1: Engine = assertDoesNotThrow(kash::invoke)
        val engine2: Engine = assertDoesNotThrow(kash::invoke)

        assertEquals(engine1, engine2)
    }
}
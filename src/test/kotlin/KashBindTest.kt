import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class KashBindTest {
    @Test
    fun `dsl should bind the abstract object 1`() {
        abstract class Contract
        class Impl : Contract()

        val module = module {
            singleOf<Contract>(::Impl)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }

    @Test
    fun `dsl should bind the abstract object 2`() {
        class Param1
        abstract class Contract

        class Impl(
            param1: Param1
        ) : Contract()

        val module = module {
            singleOf(::Param1)
            singleOf(::Impl binds Contract::class)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }

    @Test
    fun `dsl should bind the abstract object 3`() {
        class Param1
        class Param2
        abstract class Contract

        class Impl(
            param1: Param1,
            param2: Param2
        ) : Contract()

        val module = module {
            singleOf(::Param1)
            singleOf(::Param2)
            singleOf(::Impl binds Contract::class)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }

    @Test
    fun `dsl should bind the abstract object 4`() {
        class Param1
        class Param2
        class Param3
        abstract class Contract

        class Impl(
            param1: Param1,
            param2: Param2,
            param3: Param3
        ) : Contract()

        val module = module {
            singleOf(::Param1)
            singleOf(::Param2)
            singleOf(::Param3)
            singleOf(::Impl binds Contract::class)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }

    @Test
    fun `dsl should bind the abstract object 5`() {
        class Param1
        class Param2
        class Param3
        class Param4
        abstract class Contract

        class Impl(
            param1: Param1,
            param2: Param2,
            param3: Param3,
            param4: Param4
        ) : Contract()

        val module = module {
            singleOf(::Param1)
            singleOf(::Param2)
            singleOf(::Param3)
            singleOf(::Param4)
            singleOf(::Impl binds Contract::class)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }

    @Test
    fun `dsl should bind the abstract object 6`() {
        class Param1
        class Param2
        class Param3
        class Param4
        class Param5
        abstract class Contract

        class Impl(
            param1: Param1,
            param2: Param2,
            param3: Param3,
            param4: Param4,
            param5: Param5
        ) : Contract()

        val module = module {
            singleOf(::Param1)
            singleOf(::Param2)
            singleOf(::Param3)
            singleOf(::Param4)
            singleOf(::Param5)
            singleOf(::Impl binds Contract::class)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }

    @Test
    fun `dsl should bind the abstract object 7`() {
        class Param1
        class Param2
        class Param3
        class Param4
        class Param5
        class Param6
        abstract class Contract

        class Impl(
            param1: Param1,
            param2: Param2,
            param3: Param3,
            param4: Param4,
            param5: Param5,
            param6: Param6
        ) : Contract()

        val module = module {
            singleOf(::Param1)
            singleOf(::Param2)
            singleOf(::Param3)
            singleOf(::Param4)
            singleOf(::Param5)
            singleOf(::Param6)
            singleOf(::Impl binds Contract::class)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }

    @Test
    fun `dsl should bind the abstract object 8`() {
        class Param1
        class Param2
        class Param3
        class Param4
        class Param5
        class Param6
        class Param7
        abstract class Contract

        class Impl(
            param1: Param1,
            param2: Param2,
            param3: Param3,
            param4: Param4,
            param5: Param5,
            param6: Param6,
            param7: Param7
        ) : Contract()

        val module = module {
            singleOf(::Param1)
            singleOf(::Param2)
            singleOf(::Param3)
            singleOf(::Param4)
            singleOf(::Param5)
            singleOf(::Param6)
            singleOf(::Param7)
            singleOf(::Impl binds Contract::class)
        }

        val kash = startKash {
            modules(module)
        }

        val contract: Contract = assertDoesNotThrow { kash<Contract>() }
        println(contract)
    }
}
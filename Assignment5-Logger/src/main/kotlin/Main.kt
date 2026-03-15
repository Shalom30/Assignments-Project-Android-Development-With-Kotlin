// 1. Interface
interface Logger {
    fun log(message: String)
    fun warn(message: String)
    fun error(message: String)
}

//  2. ConsoleLogger implementation
class ConsoleLogger : Logger {
    override fun log(message: String) {
        println("[LOG]   $message")
    }

    override fun warn(message: String) {
        println("[WARN]  $message")
    }

    override fun error(message: String) {
        println("[ERROR] $message")
    }
}

// 3. FileLogger implementation (simulated with println)
class FileLogger : Logger {
    override fun log(message: String) {
        println("File: [LOG]   $message")
    }

    override fun warn(message: String) {
        println("File: [WARN]  $message")
    }

    override fun error(message: String) {
        println("File: [ERROR] $message")
    }
}

//  4. Application delegates ALL Logger calls to injected logger
class Application(
    val name: String,
    private val logger: Logger
) : Logger by logger {

    fun start() {
        log("$name started successfully")
    }

    fun doWork() {
        log("$name is processing data...")
        warn("$name detected a minor issue, continuing...")
    }

    fun crash() {
        error("$name encountered a fatal error and stopped")
    }

    fun stop() {
        log("$name shut down gracefully")
    }
}

// 5. Main entry point
fun main() {
    println("===== Console Logger Demo =====")
    val consoleApp = Application("MyApp", ConsoleLogger())
    consoleApp.start()
    consoleApp.doWork()
    consoleApp.crash()
    consoleApp.stop()

    println()

    println("===== File Logger Demo =====")
    val fileApp = Application("MyApp", FileLogger())
    fileApp.start()
    fileApp.doWork()
    fileApp.crash()
    fileApp.stop()

    println()

    println("===== Swapping Logger at Runtime =====")
    val loggers: List<Logger> = listOf(ConsoleLogger(), FileLogger())
    loggers.forEach { logger ->
        val app = Application("RuntimeApp", logger)
        app.log("This message goes to: ${logger::class.simpleName}")
    }
}



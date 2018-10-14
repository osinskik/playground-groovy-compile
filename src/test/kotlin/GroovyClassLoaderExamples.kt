
import com.kaosn.example.groovycompile.Bar
import com.kaosn.example.groovycompile.FooInterface
import groovy.lang.GroovyClassLoader
import groovy.lang.GroovyObject
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import kotlin.system.measureTimeMillis

/**
 * @author kamil.osinski
 */
class GroovyClassLoaderExamples {

  val classLoader = GroovyClassLoader()
  var groovyInstance: FooInterface? = null
  var groovyObjectInstance: GroovyObject? = null
  val kotlinInstance = Bar()

  @BeforeMethod
  fun initGroovy() {
    /**
     * Example Groovy Class as string,
     * which implements interface which exists in this module
     */
    val scriptFooClass = """
      class Foo implements com.kaosn.example.groovycompile.FooInterface {
        String ping(String message) {
          println message
          return message
        }
        double returnNum() {
          def a = 103 * Math.random()
          return 10 + 30 * a
        }
      }
    """.trimIndent()

    val fooClass = classLoader.parseClass(scriptFooClass)
    groovyInstance = fooClass.newInstance() as FooInterface
    groovyObjectInstance = groovyInstance as GroovyObject
  }

  @Test
  fun `checking usage`() {
    /**
     * instance of groovy class inflicts on actual program
     * - println (in ping method) will print to the console
     */
    assertThat(groovyInstance!!.ping("ok")).isEqualTo("ok")
    assertThat(groovyObjectInstance!!.invokeMethod("ping", arrayOf("ok"))).isEqualTo("ok")
    println(groovyInstance!!.returnNum())
  }

  @Test
  fun `checking performance`() {
    /**
     * Checking performance of using groovy and kotlin
     */
    println("measuring dynamic compile groovy class: " + measure(10_000_000, groovyInstance!!::returnNum))
    println("measuring static compile class: " + measure(10_000_000, kotlinInstance::returnNum))
    println("measuring dynamic compile groovy class: " + measure(10_000_000, groovyInstance!!::returnNum))
    println("measuring static compile class: " + measure(10_000_000, kotlinInstance::returnNum))
  }


  fun measure(times: Int, functionCall: () -> Double): Long {
    return measureTimeMillis {
      (0..times).forEach { functionCall() }
    }
  }

}
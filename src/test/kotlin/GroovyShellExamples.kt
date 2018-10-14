import groovy.lang.Binding
import groovy.lang.GroovyShell
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test

/**
 * @author kamil.osinski
 */
class GroovyShellExamples {

  @Test
  fun `using shell to evaluate`() {
    val shell = GroovyShell()

    val script = """
      def f = {a, b -> a + b}
      f(100, 200)
    """.trimIndent()

    val s1 = shell.evaluate(script)
    assertThat(s1).isEqualTo(300)
  }

  @Test
  fun `using shell to evaluate script with binding - its a map key to property`() {
    val binding = Binding()
    val shell = GroovyShell(binding)

    val script = """
      def f = {a, b -> a + b}
      toReturn = f(x, y)
    """.trimMargin()
    // above it must be some kind of return at the end in script! (instead MissingPropertyException)

    binding.setProperty("x", 100)
    binding.setProperty("y", 200)

    assertThat(shell.evaluate(script)).isEqualTo(300)
    assertThat(binding.getProperty("toReturn")).isEqualTo(300)
  }
}
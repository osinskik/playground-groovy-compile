import groovy.util.Eval
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test

/**
 * @author kamil.osinski
 */
class GroovyEvalExamples {

  @Test
  fun `simple scripts using Evalme`() {
    assertThat(Eval.me(""" "Example " + "String" """)).isEqualTo("Example String")

    assertThat(Eval.me(""" 4 * 4 + 2 """)).isEqualTo(18)

    assertThat(Eval.me("""
       def add = { a, b, c -> a + b * c}
       add(10, 2, 3)
    """.trimIndent())).isEqualTo(16)

    assertThat(Eval.me("2 +2") is Int).isTrue()
    assertThat(Eval.me(""" "abs" """) is String).isTrue()
  }


  /**
   * x, y, z are variable which can be used in parsed scripts
   */
  @Test
  fun `simple scripts using Evalx, xy, xyz`() {
    assertThat(Eval.x("String", """ "Example " + x """)).isEqualTo("Example String")

    assertThat(Eval.xy(4, 2, """ x * x + y """)).isEqualTo(18)

    assertThat(Eval.xyz(10, 2, 3, """
       def add = { a, b, c -> a + b * c}
       add(x, y, z)
    """.trimIndent())).isEqualTo(16)
  }
}
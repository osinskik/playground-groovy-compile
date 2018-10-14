package spel

import org.assertj.core.api.Assertions.assertThat
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.testng.annotations.Test

/**
 * @author kamil.osinski
 */
class ExpressionParserTests {

  @Test
  fun `example usage of parsing expression`() {
    val expressionParser = SpelExpressionParser()
    val expression = expressionParser.parseExpression("'Just a random text ' + (5 + 5)")
    assertThat(expression.value as String).isEqualTo("Just a random text 10")
  }

  @Test
  fun `string manipulation in expression parser`() {
    val expressionParser = SpelExpressionParser()
    val expression = expressionParser.parseExpression("'Whats my name, ' + new String('huh?').toUpperCase()")
    assertThat(expression.value as String).isEqualTo("Whats my name, HUH?")
  }

  @Test
  fun `example of using EvaluationContext to do operations on custom object`() {

    /**
     * Creating "context" which will be used in parsing expression
     */
    val myContext = MyContext("number", 5)
    val context = StandardEvaluationContext(myContext)

    /**
     * Parse expression with methods and variables
     */
    val expressionParser = SpelExpressionParser()
    val expression = expressionParser.parseExpression("'r=' + randomNumber() + ' message=' + message + ' ' + number ")
    assertThat(expression.getValue(context) as String).isEqualTo("r=4.0 message=number 5")

    /**
     * Set value to context property object
     */
    expressionParser.parseExpression("message").setValue(myContext, "nextMessage")
    assertThat(myContext.message).isEqualTo("nextMessage")
  }

  class MyContext (
      var message: String,
      val number: Int
  ) {
    fun randomNumber() = 4.0
  }
}
package io.foxcapades.spigot.block.compression.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MathKtTest {

  @Test
  @DisplayName("Returns 1 if input is 0")
  fun test1() {
    assertEquals(1, 9 pow 0)
  }

  @Test
  @DisplayName("Returns the original value if input is 1")
  fun test2() {
    assertEquals(9, 9 pow 1)
  }

  @Test
  @DisplayName("Returns the correct result when applying the pow function")
  fun test3() {
    val tests = arrayOf(
      Triple(1, 2, 1),
      Triple(2, 2, 4),
      Triple(2, 3, 8),
      Triple(2, 4, 16),
      Triple(9, 9, 387420489),
    )

    for (test in tests) {
      assertEquals(test.third, test.first.pow(test.second))
    }
  }
}
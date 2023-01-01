package dev.lub0s

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AvlTests {

  @Test
  fun testInsert() {
    val avl = BalancingTree<Int>()
    avl.insert(12)
    avl.insert(8)
    avl.insert(5)

    assertContentEquals(listOf(5, 12, 8), avl.postorderElements())
    assertContentEquals(listOf(8, 5, 12), avl.preorderElements())
  }

	@Test
	fun testSearch() {
		val avl = BalancingTree<Int>()
		avl.insert(12)
		avl.insert(8)
		avl.insert(5)

		assertTrue { avl.contains(12) }
		assertFalse { avl.contains(50) }
	}
}

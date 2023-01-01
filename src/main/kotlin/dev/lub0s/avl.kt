package dev.lub0s

import kotlin.math.max

@Suppress("FunctionName")
fun <T : Comparable<T>> BalancingTree(): AvlTree<T> =
  AvlTreeImpl()

internal data class AvlNode<T>(
  val element: T,
  var left: AvlNode<T>? = null,
  var right: AvlNode<T>? = null,
  var height: Int = 0
)

interface AvlTree<T : Comparable<T>> {
  fun insert(element: T)
  fun contains(element: T): Boolean

  fun preorderElements(): List<T>
  fun postorderElements(): List<T>
}

internal class AvlTreeImpl<T : Comparable<T>> : AvlTree<T> {

  private var root: AvlNode<T>? = null

  val isEmpty: Boolean
    get() = root == null

  private val AvlNode<T>?.getHeight
    get() =
      this?.height ?: -1

  override fun insert(element: T) {
    root = insert(element, root)
  }

  override fun contains(element: T): Boolean {
    if (isEmpty) return false
    return contains(element, root!!)
  }

  override fun preorderElements(): List<T> =
    preorder(root)

  override fun postorderElements(): List<T> =
    postOrder(root)

  fun clear() {
    root = null
  }

  @Suppress("NAME_SHADOWING")
  private fun insert(element: T, node: AvlNode<T>?): AvlNode<T> {
    var node = node

    if (node == null) {
      node = AvlNode(element)
    } else if (element < node.element) {
      node.left = insert(element, node.left)
      if (node.left.getHeight - node.right.getHeight == 2) {
        node = if (element < node.left!!.element) {
          rotateWithLeftChild(node)
        } else {
          doubleWithLeftChild(node)
        }
      }
    } else if (element > node.element) {
      node.right = insert(element, node.right)
      if (node.right.getHeight - node.left.getHeight == 2) {
        node = if (element > node.right!!.element) {
          rotateWithRightChild(node)
        } else {
          doubleWithRightChild(node)
        }
      }
    } else {
      // already exists
    }

    node?.height = max(node?.left.getHeight, node?.right.getHeight) + 1

    return node!!
  }

  @Suppress("NAME_SHADOWING")
  private fun contains(element: T, current: AvlNode<T>?): Boolean {
    var current = current
    var found = false

    while (current != null && !found) {
      val nodeElement = current.element
      if (element < nodeElement) {
        current = current.left
      } else if (element > nodeElement) {
        current = current.right
      } else {
        found = true
        break
      }

      found = contains(element, current)
    }

    return found
  }

  private fun preorder(current: AvlNode<T>?): List<T> = if (current != null) {
    listOf(current.element) + preorder(current.left) + preorder(current.right)
  } else {
    emptyList()
  }

  private fun postOrder(current: AvlNode<T>?): List<T> = if (current != null) {
    postOrder(current.left) + postOrder(current.right) + listOf(current.element)
  } else {
    emptyList()
  }

  private fun rotateWithLeftChild(node2: AvlNode<T>?): AvlNode<T>? {
    val node1 = node2?.left
    node2?.left = node1?.right
    node1?.right = node2
    node2?.height = max((node2?.left.getHeight), node2?.right.getHeight) + 1
    node1?.height = max((node1?.left.getHeight), node2?.height ?: -1) + 1
    return node1
  }

  private fun rotateWithRightChild(node1: AvlNode<T>?): AvlNode<T>? {
    val node2 = node1?.right
    node1?.right = node2?.left
    node2?.left = node1
    node1?.height = max((node1?.left.getHeight), (node1?.right.getHeight)) + 1
    node2?.height = max((node2?.right.getHeight), node1?.height ?: -1) + 1
    return node2
  }

  private fun doubleWithLeftChild(node3: AvlNode<T>): AvlNode<T>? {
    node3.left = rotateWithRightChild(node3.left)
    return rotateWithLeftChild(node3)
  }

  private fun doubleWithRightChild(node1: AvlNode<T>): AvlNode<T>? {
    node1.right = rotateWithLeftChild(node1.right)
    return rotateWithRightChild(node1)
  }
}

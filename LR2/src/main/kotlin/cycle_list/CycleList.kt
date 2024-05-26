package cycle_list

import comparator.Comparator
import types.users.UserType
import java.io.*

class CycleList : Serializable {
    private var head: Node? = null
    var length = 0
        private set

    private inner class Node(var data: Any?) : Serializable {
        var next: Node? = null
        var prev: Node? = null
        override fun toString(): String {
            return data.toString()
        }
    }

    fun add(data: Any?) {
        if (head == null) {
            val node: Node = Node(data)
            node.prev = node
            node.next = node.prev
            head = node
            length++
            return
        }
        val tail: Node? = head!!.prev
        val node: Node = Node(data)
        node.next = head
        head!!.prev = node
        node.prev = tail
        tail!!.next = node
        length++
    }

    fun add(data: Any?, index: Int) {
        val tmp = getNode(index)
        val newNode: Node = Node(data)
        val tail: Node? = head!!.prev
        if (tmp !== head) {
            tmp!!.prev!!.next = newNode
            newNode.prev = tmp.prev
        } else {
            head = newNode
        }
        newNode.next = tmp
        tmp!!.prev = newNode
        tail!!.next = head
        head!!.prev = tail
        length++
    }

    fun remove(index: Int) {
        val tmp = getNode(index)
        var tail = head!!.prev
        if (tmp !== head) {
            tmp!!.prev!!.next = tmp.next
        } else {
            head = tmp!!.next
        }
        if (tmp !== tail) {
            tmp.next!!.prev = tmp.prev
        } else {
            tail = tmp.prev
        }
        tmp.prev = null
        tmp.next = tmp.prev
        tail!!.next = head
        head!!.prev = tail
        length--
    }

    fun getByIndex(index: Int): Any? {
        return getNode(index)!!.data
    }

    private fun getNode(index: Int): Node? {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException()
        var tmp = head
        for (i in 0 until index) {
            tmp = tmp!!.next
        }
        return tmp
    }

    @Throws(IOException::class)
    fun forEach(iterator: Iterator) {
        var tmp = head
        for (i in 0 until length) {
            iterator.toDo(tmp!!.data)
            tmp = tmp.next
        }
    }

    @Throws(IOException::class)
    fun forEachReverse(iterator: Iterator) {
        var tmp = head
        tmp = tmp!!.prev
        for (i in 0 until length) {
            iterator.toDo(tmp!!.data)
            tmp = tmp.prev
        }
    }

    fun sort(comparator: Comparator) {
        if (head != null && head!!.next !== head && head!!.prev !== head) {
            var tail = head!!.prev
            tail!!.next = null
            head!!.prev = null
            head = mergeSort(head, comparator)
            tail = getNode(length - 1)
            tail!!.next = head
            head!!.prev = tail
        }
    }

    private fun mergeSort(headNode: Node?, comparator: Comparator): Node? {
        if (headNode?.next == null) {
            return headNode
        }
        val middle = getMidNode(headNode)
        val middleNext = middle!!.next
        middle.next = null
        val left = mergeSort(headNode, comparator)
        val right = mergeSort(middleNext, comparator)
        return merge(left, right, comparator)
    }

    private fun merge(_firstNode: Node?, _secondNode: Node?, comparator: Comparator): Node? {
        var firstNode: Node? = _firstNode
        var secondNode: Node? = _secondNode
        val merged: Node = Node(null)
        var temp: Node? = merged
        var tail = head!!.prev
        while (firstNode != null && secondNode != null) {
            if (comparator.compare(firstNode.data!!, secondNode.data!!) < 0) {
                temp!!.next = firstNode
                firstNode.prev = temp
                firstNode = firstNode.next
            } else {
                temp!!.next = secondNode
                secondNode.prev = temp
                secondNode = secondNode.next
            }
            temp = temp.next!!
        }
        while (firstNode != null) {
            temp!!.next = firstNode
            firstNode.prev = temp
            firstNode = firstNode.next
            temp = temp.next
        }
        while (secondNode != null) {
            temp!!.next = secondNode
            secondNode.prev = temp
            secondNode = secondNode.next
            temp = temp.next
            tail = temp
        }
        return merged.next
    }

    private fun getMidNode(node: Node): Node? {
        var previousNode: Node? = node
        var currentNode: Node? = node
        while (currentNode!!.next != null && currentNode.next!!.next != null) {
            previousNode = previousNode!!.next
            currentNode = currentNode.next!!.next
        }
        return previousNode
    }

    fun printList() {
        var tmp = head
        for (i in 0 until length) {
            print("$i) ")
            println(tmp!!.data)
            tmp = tmp.next
        }
    }

    override fun toString(): String {
        val str = StringBuilder()
        var tmp = head
        for (i in 0 until length) {
            str.append(tmp!!.data).append("\n")
            tmp = tmp.next
        }
        return str.toString()
    }

    fun clearList() {
        head = null
        length = 0
    }

    fun save(userType: UserType, fileName: String?) {
        try {
            BufferedWriter(FileWriter(fileName)).use { writer ->
                writer.write(userType.typeName() + "\n")
                this.forEach(object : Iterator {
                    override fun toDo(data: Any?) {
                        writer.write(
                            """
                        ${userType.toString(data!!)}
                        
                        """.trimIndent()
                        )
                    }
                })
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun load(userType: UserType, fileName: String?) {
        clearList()
        try {
            BufferedReader(FileReader(fileName)).use { br ->
                var line: String?
                line = br.readLine()
                if (userType.typeName() != line) {
                    throw Exception("Wrong file structure")
                }
                while (br.readLine().also { line = it } != null) {
                    add(userType.parseValue(line!!))
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

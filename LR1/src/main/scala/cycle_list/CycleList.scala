package cycle_list

import comparator.Comparator
import types.users.UserType
import java.io._

class CycleList extends Serializable {
  private var head: Node = _
  private var length: Int = 0

  private class Node(var data: AnyRef) extends Serializable {
    var next: Node = _
    var prev: Node = _

    override def toString: String = data.toString
  }

  def add(data: AnyRef): Unit = {
    if (head == null) {
      var node = new Node(data)
      node.next = node
      node.prev = node
      head = node
      length += 1
      return
    }
    var tail: Node = head.prev
    var node: Node = new Node(data)
    node.next = head
    head.prev = node
    node.prev = tail
    tail.next = node
    length += 1
  }

  def add(data: AnyRef, index: Int): Unit = {
    var tmp = getNode(index)
    var newNode = new Node(data)
    var tail = head.prev
    if (tmp ne head) {
      tmp.prev.next = newNode
      newNode.prev = tmp.prev
    }
    else head = newNode
    newNode.next = tmp
    tmp.prev = newNode
    tail.next = head
    head.prev = tail
    length += 1
  }

  def remove(index: Int): Unit = {
    var tmp = getNode(index)
    var tail: Node = head.prev
    if (tmp ne head) tmp.prev.next = tmp.next
    else head = tmp.next
    if (tmp ne tail) tmp.next.prev = tmp.prev
    else tail = tmp.prev
    tmp.next = null
    tmp.prev = null
    tail.next = head
    head.prev = tail
    length -= 1
  }

  def getByIndex(index: Int): AnyRef = getNode(index).data

  def getLength: Int = length

  private def getNode(index: Int): Node = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    var tmp = head
    for (i <- 0 until index) {
      tmp = tmp.next
    }
    tmp
  }

  @throws[IOException]
  def forEach(iterator: Iterator): Unit = {
    var tmp = head
    for (i <- 0 until length) {
      iterator.toDo(tmp.data)
      tmp = tmp.next
    }
  }

  @throws[IOException]
  def forEachReverse(iterator: Iterator): Unit = {
    var tmp = head
    tmp = tmp.prev
    for (i <- 0 until length) {
      iterator.toDo(tmp.data)
      tmp = tmp.prev
    }
  }

  def sort(comparator: Comparator): Unit = {
    if (head != null && (head.next ne head) && (head.prev ne head)) {
      var tail = head.prev
      tail.next = null
      head.prev = null
      head = mergeSort(head, comparator)
      tail = getNode(length - 1)
      tail.next = head
      head.prev = tail
    }
  }

  private def mergeSort(headNode: Node, comparator: Comparator): Node = {
    if (headNode == null || headNode.next == null) return headNode
    val middle = getMidNode(headNode)
    val middleNext = middle.next
    middle.next = null
    val left = mergeSort(headNode, comparator)
    val right = mergeSort(middleNext, comparator)
    merge(left, right, comparator)
  }

  private def merge(first: Node, second: Node, comparator: Comparator): Node = {
    var firstNode: Node = first
    var secondNode: Node = second
    val merged = new Node(null)
    var temp = merged
    var tail = head.prev
    while (firstNode != null && secondNode != null) {
      if (comparator.compare(firstNode.data, secondNode.data) < 0) {
        temp.next = firstNode
        firstNode.prev = temp
        firstNode = firstNode.next
      }
      else {
        temp.next = secondNode
        secondNode.prev = temp
        secondNode = secondNode.next
      }
      temp = temp.next
    }
    while (firstNode != null) {
      temp.next = firstNode
      firstNode.prev = temp
      firstNode = firstNode.next
      temp = temp.next
    }
    while (secondNode != null) {
      temp.next = secondNode
      secondNode.prev = temp
      secondNode = secondNode.next
      temp = temp.next
      tail = temp
    }
    merged.next
  }

  private def getMidNode(node: Node): Node = {
    var previousNode = node
    var currentNode = node
    while (currentNode.next != null && currentNode.next.next != null) {
      previousNode = previousNode.next
      currentNode = currentNode.next.next
    }
    previousNode
  }

  def printList(): Unit = {
    var tmp = head
    for (i <- 0 until length) {
      System.out.print(i + ") ")
      System.out.println(tmp.data)
      tmp = tmp.next
    }
  }

  override def toString: String = {
    var str = ""
    var tmp = head
    for (i <- 0 until length) {
      str = str + tmp.data + "\n"
      tmp = tmp.next
    }
    str
  }

  def clearList(): Unit = {
    head = null
    length = 0
  }

  def save(userType: UserType, fileName: String): Unit = {
    try {
      val writer = new BufferedWriter(new FileWriter(fileName))
      try {
        writer.write(userType.typeName + "\n")
        this.forEach((data: AnyRef) => writer.write(data.toString + "\n"))
      } catch {
        case e: IOException =>
          e.printStackTrace()
      } finally if (writer != null) writer.close()
    }
  }

  def load(userType: UserType, fileName: String): Unit = {
    clearList()
    try {
      val br = new BufferedReader(new FileReader(fileName))
      try {
        var line: String = null
        line = br.readLine
        if (!(userType.typeName == line)) throw new Exception("Wrong file structure")
        line = br.readLine
        while (line != null) {
          add(userType.parseValue(line))
          line = br.readLine
        }
      } catch {
        case e: Exception =>
          throw new RuntimeException(e)
      } finally if (br != null) br.close()
    }
  }

  // func

  def mergeSortFuncStyle(comparator: Comparator): CycleList = {
    if (this.length <= 1)
      this
    else {
      val sortedList = new CycleList
      var leftList = new CycleList
      var rightList = new CycleList
      val middle = this.length / 2;
      forEachListLeft(x => {
        leftList.add(x)
      }, 0, middle)

      forEachListRight(x => {
        rightList.add(x)
      }, middle, this.length)

      leftList = leftList.mergeSortFuncStyle(comparator)
      rightList = rightList.mergeSortFuncStyle(comparator)

      var left = leftList.head
      var right = rightList.head

      while (leftList.length > 0 && rightList.length > 0) {
        if (comparator.compare(left.data, right.data) <= 0) {
          sortedList.add(left.data)
          left = left.next
          leftList.remove(0)
        } else {
          sortedList.add(right.data)
          right = right.next
          rightList.remove(0)
        }
      }

      while (leftList.length > 0) {
        sortedList.add(left.data)
        left = left.next
        leftList.remove(0)
      }

      while (rightList.length > 0) {
        sortedList.add(right.data)
        right = right.next
        rightList.remove(0)
      }

      sortedList
    }
  }

  def forEachListLeft(iterator: Iterator, idBegin: Int, idEnd: Int): Unit = {
    var tmp = head
    for (i <- idBegin until idEnd) {
      iterator.toDo(tmp.data)
      tmp = tmp.next
    }
  }

  def forEachListRight(iterator: Iterator, idBegin: Int, idEnd: Int): Unit = {
    var tmp = head
    for (i <- 0 until idBegin) {
      tmp = tmp.next
    }
    for (i <- idBegin until idEnd) {
      iterator.toDo(tmp.data)
      tmp = tmp.next
    }
  }

}
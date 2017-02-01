package fil.iagl.idl.sma.hunter.util

import fil.iagl.idl.sma.core.model.Environment
import fil.iagl.idl.sma.core.util.Position

import scala.collection.mutable
import scala.util.Random

class DijkstraPathFinder {

  var currentDijkstraDistancesOption: Option[Array[Array[Int]]] = None

  def updateDijkstraDistances(environment: Environment, agentPosition: Position): Unit = {
    currentDijkstraDistancesOption = Some(Array.fill(environment.width, environment.height)(-1))
    currentDijkstraDistancesOption.get(agentPosition.x)(agentPosition.y) = 0
    val queue = mutable.Queue[Position]()
    queue.enqueue(agentPosition)
    while (queue.nonEmpty) {
      enqueueNeighbors(queue, environment)
    }
  }

  private def enqueueNeighbors(queue: mutable.Queue[Position], environment: Environment): Unit = {
    val head = queue.dequeue
    val list1 = Random.shuffle((-1).to(1))
    val list2 = Random.shuffle((-1).to(1))
    list1.foreach(i => list2.foreach(j => {
      val nextX = if ((head.x + i) >= 0) head.x + i else (head.x + i) + environment.width
      val nextY = if ((head.y + j) >= 0) head.y + j else (head.y + j) + environment.height
      val neighbor = Position(nextX % environment.width, nextY % environment.height)
      if (currentDijkstraDistancesOption.get(neighbor.x)(neighbor.y) > currentDijkstraDistancesOption.get(head.x)(head.y) + 1 || currentDijkstraDistancesOption.get(neighbor.x)(neighbor.y) == -1) {
        currentDijkstraDistancesOption.get(neighbor.x)(neighbor.y) = currentDijkstraDistancesOption.get(head.x)(head.y) + 1
        queue.enqueue(neighbor)
      }
   }))
  }

}

object DijkstraPathFinder {

  def apply(): DijkstraPathFinder = new DijkstraPathFinder()

}

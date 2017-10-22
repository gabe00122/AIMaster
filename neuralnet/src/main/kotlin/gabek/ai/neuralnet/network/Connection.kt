package gabek.ai.neuralnet.network

/**
 * @author Gabriel Keith
 * @date 8/27/2017
 */
class Connection(val forwardNode: Node, val backwardNode: Node) {
    var weight: Double = 0.0
}

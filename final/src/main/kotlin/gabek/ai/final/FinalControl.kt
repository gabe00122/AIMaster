package gabek.ai.final

import javafx.animation.AnimationTimer
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.paint.Color

class FinalControl {
    @FXML lateinit var errorDisplay: Canvas

    @FXML lateinit var algorithmInput: ComboBox<Problem>

    @FXML lateinit var bitsizeInput: TextField

    @FXML lateinit var learningRate: TextField
    @FXML lateinit var hiddenLayers: TextField

    @FXML lateinit var xInput: TextField
    @FXML lateinit var yInput: TextField

    @FXML lateinit var ansOut: Label

    @FXML lateinit var networkOut: Label

    @FXML lateinit var iterCount: Label

    var solver: NetworkSolver? = null
    var running = false
    var count = 0

    val updateLoop = object: AnimationTimer(){
        override fun handle(now: Long) {
            val solver = solver

            if(solver != null) {
                if(count++.rem(50) == 0) {
                    drawTo(errorDisplay, solver.testGrid())
                    iterCount.text = "Iteration: ${solver.iter}"
                }
                solver.train(500)
            } else {
                stop()
            }
        }
    }

    @FXML fun initialize(){
        algorithmInput.items.addAll(Add, Subtract, Greater, XOr, Multiply, Shift, RandomPairs)


    }

    fun buildSolver(): NetworkSolver{
        val bitsize = bitsizeInput.text.toInt()
        val learningRate = learningRate.text.toDouble()
        val problem = algorithmInput.selectionModel.selectedItem

        val hiddenSizes = hiddenLayers.text
                .split(",")
                .mapTo(ArrayList())
                { it.trim().toInt() }

        return NetworkSolver(bitsize, problem, hiddenSizes, learningRate)
    }

    @FXML fun go(event: ActionEvent){
        var solver = solver

        if(!running) {
            if (solver == null) {
                solver = buildSolver()
                this.solver = solver
            }

            updateLoop.start()
            running = true
        }
    }

    @FXML fun pause(event: ActionEvent){
        if(running){
            updateLoop.stop()
            running = false
        }
    }

    @FXML fun reset(event: ActionEvent){
        if(running){
            updateLoop.stop()
            running = false
            count = 0
        }

        solver = null
    }

    @FXML fun calculate(event: ActionEvent){
        val solver = solver

        if(solver != null) {
            val bitsize = solver.bitsize
            val x = xInput.text.toInt()
            val y = yInput.text.toInt()
            val z = solver.problem.apply(x, y, bitsize)

            val temp = ArrayList<Double>()
            repeat(bitsize*2) { temp.add(0.0) }

            toBinSignals(x, bitsize, temp, 0)
            toBinSignals(y, bitsize, temp, bitsize)

            val netZ = toNumber(solver.network.process(temp))

            ansOut.text = z.toString()
            networkOut.text = netZ.toString()
        }
    }
}

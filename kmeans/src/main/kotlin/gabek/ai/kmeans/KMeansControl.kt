package gabek.ai.kmeans

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Gabriel Keith
 * @date 10/2/2017
 */

class KMeansControl {
    @FXML private lateinit var root: BorderPane
    @FXML private lateinit var canvas: Canvas
    @FXML private lateinit var groupInput: TextField
    @FXML private lateinit var iterationLabel: Label

    private lateinit var renderer: DataRenderer

    private val random = Random()

    private var colors: List<Color>? = null
    private var points: PointField? = null
    private var frames: List<Frame>? = null

    private var frameIndex = 0

    @FXML private fun initialize(){
        renderer = DataRenderer(canvas)

        //points = uniformRandom(random, 140)

        render()
    }

    @FXML private fun run(event: ActionEvent){
        val points = points

        if(points != null) {
            val numGroups = groupInput.text.toIntOrNull()

            if(numGroups != null) {
                genColors(numGroups)

                val frames = runKmeans(points, startFrame(random, points, numGroups))
                this.frames = frames

                frameIndex = frames.size - 1
                render()
            } else {
                error("Error Running KMeans", "Invalid input for number of groups.")
            }
        } else {
            error("Error Running KMeans", "No Point data loaded in the program.")
        }
    }

    @FXML private fun loadData(event: ActionEvent){
        val fileChooser = FileChooser()
        fileChooser.title = "Choose data to load"
        fileChooser.initialDirectory = File("./")

        val file: File? = fileChooser.showOpenDialog(root.scene.window)

        if(file != null){
            points = readXY(file)
            frameIndex = 0
            frames = null

            render()
        }
    }

    @FXML private fun prevFrame(event: ActionEvent){
        if(frameIndex > 0){
            frameIndex--

            render()
        }
    }

    @FXML private fun nextFrame(event: ActionEvent){
        val frames = frames

        if(frames != null && frameIndex < frames.size - 1){
            frameIndex++

            render()
        }
    }

    private fun genColors(num: Int){
        val colors = ArrayList<Color>()
        colors.ensureCapacity(num)


        var hue = random.nextDouble() * 360.0
        val hueStep = 360.0 / num

        repeat(num){
            colors.add(Color.hsb(hue, 0.6 + random.nextDouble() * 0.2, 0.8))

            hue += hueStep
            if (hue > 360.0){
                hue -= 360.0
            }
        }

        this.colors = colors
    }

    private fun error(title: String, message: String){
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = message

        alert.showAndWait()
    }

    private fun render(){
        val points = points
        val frames = frames
        val colors = colors

        renderer.clear()
        if(points != null){
            if(frames != null && colors != null){
                renderer.renderPoints(points, frames[Math.min(frameIndex, frames.size-1)], colors)
                renderer.renderGroups(frames, 0 until Math.min(frameIndex+1, frames.size), colors)

                iterationLabel.text = "Iteration: $frameIndex"
            } else {
                renderer.renderPoints(points)

                iterationLabel.text = "Iteration: -"
            }
        }
    }
}
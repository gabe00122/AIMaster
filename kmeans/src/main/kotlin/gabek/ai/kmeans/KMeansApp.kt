package gabek.ai.kmeans

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 * @author Gabriel Keith
 * @date 10/1/2017
 */

class KMeansApp : Application() {

    override fun start(primaryStage: Stage) {
        val root: Parent = FXMLLoader.load(javaClass.getResource("/gabek/ai/kmeans/KMeans.fxml"))
        val scene = Scene(root)

        primaryStage.scene = scene
        primaryStage.isResizable = false
        primaryStage.title = "KMeans"
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(KMeansApp::class.java)
        }
    }
}

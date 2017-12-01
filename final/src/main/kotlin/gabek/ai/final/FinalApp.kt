package gabek.ai.final

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage


class FinalApp: Application() {
    override fun start(primaryStage: Stage) {
        val root: Parent = FXMLLoader.load(javaClass.getResource("/gabek/ai/final/Final.fxml"))
        val scene = Scene(root, 800.0, 800.0)

        primaryStage.scene = scene
        primaryStage.title = "Final"
        primaryStage.show()
    }
}
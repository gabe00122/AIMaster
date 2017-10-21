package gabek.ai.neuralnet.input.config

import gabek.ai.neuralnet.format.BoolConverter
import gabek.ai.neuralnet.format.DataConverter
import gabek.ai.neuralnet.net.NetworkConfig
import gabek.ai.neuralnet.net.TrainingConfig
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream

/**
 * @author Gabriel Keith
 * @date 9/11/2017
 */

fun readConfig(configFile: File): Config{
    val stream = FileInputStream(configFile)
    val yaml = Yaml()
    val config = parseConfig(yaml.load(stream) as Map<*, *>)
    stream.close()

    return config
}

private fun parseConfig(configMap: Map<*, *>) = Config(
        title = configMap.getOrDefault("title", "") as String,
        network = parseNetworkConfig(configMap),
        training = parseTrainingConfig(configMap, BoolConverter())
)

@Suppress("UNCHECKED_CAST")
private fun parseNetworkConfig(configMap: Map<*, *>): NetworkConfig{
    val netMap = configMap["network"] as Map<*, *>

    return NetworkConfig(
            inputSize = netMap["input"] as Int,
            hiddenSizes = netMap["hidden"] as ArrayList<Int>,
            outputSize = netMap["output"] as Int,
            learningRate = netMap["learingRate"] as Double
    )
}

@Suppress("UNCHECKED_CAST")
private fun parseTrainingConfig(configMap: Map<*, *>, converter: DataConverter<*>): TrainingConfig{
    val trainingMap = configMap["training"] as Map<*, *>

    val trainingData = ArrayList<TrainingConfig.DataPair>()
    val data = trainingMap["data"] as ArrayList<ArrayList<*>>

    for(d in data){
        val Xl = converter.dataFromYaml(d[0]) as ArrayList
        val yl = converter.dataFromYaml(d[1]) as ArrayList

        val X = Array(Xl.size, { 0.0 })
        val y = Array(yl.size, { 0.0 })

        Xl.toArray(X)
        yl.toArray(y)

        trainingData.add(TrainingConfig.DataPair(X, y))
    }

    return TrainingConfig(
            maxReps = trainingMap["maxReps"] as Int,
            testInterval = trainingMap["testInterval"] as Int,
            targetError = trainingMap["targetError"] as Double,
            data = trainingData
    )
}
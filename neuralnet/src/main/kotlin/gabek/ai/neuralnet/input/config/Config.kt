package gabek.ai.neuralnet.input.config

import gabek.ai.neuralnet.format.BoolConverter
import gabek.ai.neuralnet.format.DataConverter
import gabek.ai.neuralnet.net.NetworkConfig
import gabek.ai.neuralnet.net.TrainingConfig

/**
 * @author Gabriel Keith
 * @date 9/11/2017
 */
class Config (
    val title: String,
    val network: NetworkConfig,
    val training: TrainingConfig,
    val converter: DataConverter<*> = BoolConverter()
)
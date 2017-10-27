package gabek.ai.neuralnet

abstract class ExecutionMonitor {
    open fun onExecutionBegin(execution: Execution){}
    open fun onTest(execution: Execution, epoc:Int, rmse: Double){}
    open fun onCompletion(execution: Execution){}
}
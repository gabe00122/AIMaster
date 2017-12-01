package gabek.ai.final

import javafx.application.Application

fun main(args: Array<String>){
    Application.launch(FinalApp::class.java)
}

/*
fun main(args: Array<String>) {

    val output = OutputStreamWriter(BufferedOutputStream(FileOutputStream("data.txt")))

    for(nbits in 6 .. 8) {
        val domainsq = (domain(nbits) * domain(nbits)).toDouble()

        output.write("$nbits")
        output.flush()

        var highscore = -1
        for(n in 0 until 10) {
            var done = false
            for (nerons in (if(highscore == -1) 40 else highscore-5)..200) {
                if(highscore != -1 && nerons > highscore){
                    output.write(", -")
                    output.flush()
                    break
                }

                val solver = NetworkSolver(nbits, Multiply, listOf(nerons, nerons), 0.2)

                for (i in 0 until 25) {
                    solver.train(50000)
                    solver.testGrid()
                    if (solver.numRight / domainsq > 0.95) {
                        done = true
                        if(highscore == -1 || nerons < highscore){
                            highscore = nerons
                        }
                        output.write(", $nerons")
                        output.flush()
                        break
                    }
                }

                if (done) {
                    break
                }
            }
        }
        output.write("\n")
    }

    output.close()
}
*/

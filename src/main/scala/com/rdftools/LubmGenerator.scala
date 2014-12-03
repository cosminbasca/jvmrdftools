//
// author: Cosmin Basca
//
// Copyright 2010 University of Zurich
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package com.rdftools

import edu.lehigh.swat.bench.uba.Generator

/**
 * Created by basca on 17/09/14.
 */
case class LubmConfig(numUniversities: Int = -1,
                      startIndex: Int = -1,
                      seed: Int = -1,
                      daml: Boolean = false,
                      ontology: String = "http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl",
                      outputPath: String = ".")

object LubmGenerator extends App {
  override def main(args: Array[String]) = {
    val parser = new scopt.OptionParser[LubmConfig]("LubmGenerator") {
      head(BuildInfo.name, BuildInfo.version)

      opt[Int]('n', "num_universities") action {
        (x, c) => c.copy(numUniversities = x)
      } text "the number of universities to generate"

      opt[Int]('i', "start_index") action {
        (x, c) => c.copy(startIndex = x)
      } text "the university start index"

      opt[Int]('s', "seed") action {
        (x, c) => c.copy(seed = x)
      } text "the university seed"

      opt[Unit]("daml") optional() action {
        (_, c) => c.copy(daml = true)
      } text "if set to true (default) will generate DAML+OIL data"

      opt[String]('o', "ontology") action {
        (x, c) => c.copy(ontology = x)
      } text "the LUBM ontology default is: http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl"

      opt[String]('p', "output_path") action {
        (x, c) => c.copy(outputPath = x)
      } text "the output path of the generator"
    }

    parser.parse(args, LubmConfig()) map { config =>
      val generator: Generator = new Generator()
      generator.start(config.numUniversities, config.startIndex, config.seed, config.daml, config.ontology, config.outputPath)
    } getOrElse {
      println("arguments could not be parsed.")
    }
  }
}

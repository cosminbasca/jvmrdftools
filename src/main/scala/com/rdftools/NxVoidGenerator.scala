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

import java.io.{FileOutputStream, FileInputStream}

import org.semanticweb.yars.nx.file.FileInput
import org.semanticweb.yars.stats.VoiD


case class NxVoidConfig(source: String = "",
                        datasetId: String = "",
                        outputPath: String = ".")

object NxVoidGenerator extends App {
  override def main(args: Array[String]) = {
    val parser = new scopt.OptionParser[NxVoidConfig]("NxVoidGenerator") {
      head(BuildInfo.name, BuildInfo.version)

      opt[String]('o', "source") action {
        (x, c) => c.copy(source = x)
      } text "the source file to analize"

      opt[String]('d', "dataset_id") action {
        (x, c) => c.copy(datasetId = x)
      } text "the dataset id"

      opt[String]('p', "output_path") action {
        (x, c) => c.copy(outputPath = x)
      } text "the output path of the generator"
    }

    parser.parse(args, NxVoidConfig()) map { config =>
      val statsEngine: VoiD = new VoiD()
      val is: FileInputStream = new FileInputStream(config.source)
      val os: FileOutputStream = new FileOutputStream(config.outputPath)
      statsEngine.analyseVoid(is, config.datasetId, os)
    } getOrElse {
      println("arguments could not be parsed.")
    }
  }
}

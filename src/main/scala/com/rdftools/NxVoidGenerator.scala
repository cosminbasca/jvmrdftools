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

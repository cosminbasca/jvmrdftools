package com.rdftools
import com.elaunira.sbf.ScalableBloomFilter
import org.semanticweb.yars.nx.Node
import org.semanticweb.yars.nx.parser.NxParser
import scala.collection
import scala.collection.mutable
import java.io._
import net.liftweb.json.Extraction._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Printer._


object Constants {
  val MIL = 1000000
  val INIT_CAPACITY_MED = 10 * MIL
  val FP_ERROR_RATE = 0.5
}

class UniqueCounter(capacity: Int, err_rate: Double = Constants.FP_ERROR_RATE) {
  var unique_count: Int = 0
  var sbf: ScalableBloomFilter[String] = new ScalableBloomFilter[String](capacity, err_rate)

  def add(item: String) = {
    if (!sbf.add(item)) {
      unique_count += 1
    }
  }
}

class PartitionCounter(capacity: Int, err_rate: Double = Constants.FP_ERROR_RATE) {
  var unique_counts: mutable.Map[String, UniqueCounter] = mutable.Map[String, UniqueCounter]()

  def add(key: String, value: String) = {
    if (!(unique_counts contains key)) {
      unique_counts += (key -> new UniqueCounter(capacity, err_rate))
    }
    unique_counts(key).add(value)
  }

  def counts(): collection.Map[String, Int] = {
    unique_counts.mapValues((value: UniqueCounter) => value.unique_count)
  }

  def size(): Int = unique_counts.size
}


object VoIDGenerator extends App {
  def get_void_fragment(source: InputStream, capacity: Int = Constants.INIT_CAPACITY_MED): mutable.Map[String, Any] = {
    val sbf_subjects: UniqueCounter = new UniqueCounter(capacity, Constants.FP_ERROR_RATE)
    val sbf_objects: UniqueCounter = new UniqueCounter(capacity, Constants.FP_ERROR_RATE)
    val part_classes: PartitionCounter = new PartitionCounter(capacity, Constants.FP_ERROR_RATE)
    val part_properties: PartitionCounter = new PartitionCounter(capacity, Constants.FP_ERROR_RATE)
    var t_count: Int = 0

    val stats = mutable.Map[String, Any](
      "properties" -> 0,
      "triples" -> 0,
      "objects" -> 0,
      "subjects" -> 0,
      "classes" -> 0,
      "partition_classes" -> Unit,
      "partition_properties" -> Unit
    )

    val parser: NxParser = new NxParser(source)

    var node: Array[Node] = Array.empty[Node]

    var s: String = ""
    var p: String = ""
    var o: String = ""

    while (parser.hasNext) {
      node = parser.next
      s = node(0).toString
      p = node(1).toString
      o = node(2).toString
      if ((t_count % 50000 == 0) && (t_count > 0))
        println("[processed " + t_count + " triples]")
      sbf_subjects.add(s)
      sbf_objects.add(o)
      if (p == "http://www.w3.org/1999/02/22-rdf-syntax-ns#type") {
        part_classes.add(o, s + " " + p)
      }
      part_properties.add(p, s + " " + o)
      t_count += 1
    }

    stats += ("triples" -> t_count)
    stats.update("properties", part_properties.size())
    stats.update("classes", part_classes.size())
    stats.update("subjects", sbf_subjects.unique_count)
    stats.update("objects", sbf_objects.unique_count)
    stats.update("partition_classes", part_classes.counts().toMap)
    stats.update("partition_properties", part_properties.counts().toMap)

    stats
  }

  override def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[NxVoidConfig]("VoidGenerator") {
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
      processFile(config.source, config.outputPath)
    } getOrElse {
      println("arguments could not be parsed.")
    }

  }

  def processFile(name: String, outputPath: String) {
    val t0 = System.currentTimeMillis()
    val source: FileInputStream = new FileInputStream(name)
    val stats: mutable.Map[String, Any] = VoIDGenerator.get_void_fragment(source)
    println(s"Have stats :\n $stats")
    val t1 = System.currentTimeMillis()
    println("Elapsed time: " + (t1 - t0) / 1000.0 + " seconds")
    val out:PrintWriter = new PrintWriter(outputPath)
    implicit val formats = net.liftweb.json.DefaultFormats
    out.write(pretty(render(decompose(stats.toMap[String, Any]))))
    out.close()
  }
}

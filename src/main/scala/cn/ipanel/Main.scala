package cn.ipanel

import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("trainpre")
//      .master("local")
      .getOrCreate()

    val logger = LoggerFactory.getLogger("trainpredict")
    logger.info("")
    logger.info("**************************************************************")
    logger.info("**                       训练Word2Vec并预测                 **")
    logger.info("**************************************************************")
    // 训练文件路径
    val fileName: String = args(0)
    // 参与训练的csv文件列名
    val colName: String = args(1)
    // 模型保存路径
    val modelPath: String = args(2)
    // 被预测词文件存放路径
    val predictWordFile: String = args(3)

    logger.info("fileName: " + fileName)
    logger.info("colName: " + colName)
    logger.info("modelPath: " + modelPath)
    logger.info("predictWordFile: " + predictWordFile)


    val readFile = new ReadFile
    val csvFile = readFile.readCSV(fileName, colName, spark, logger)


    val wordSeg = new WordSegment
    val seg = wordSeg.ansjSeg(csvFile, spark, logger)


    val trainModel = new TrainModel
    val predWordModel = trainModel.train(seg, modelPath, logger)


    val similarWord = new SimilarWord()
    similarWord.printSimilarWd(spark, modelPath, readFile, predictWordFile, logger)


  }
}

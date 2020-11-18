package cn.ipanel

import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory

object PredictSimWd {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("hadoopFileTest")
      .master("local")
      .getOrCreate()

    val logger = LoggerFactory.getLogger("simword")

    // w2v模型存放路径
    val modelPath = args(2)
    // 要查找的相似词
    val preWordFilePath = args(3)
    logger.info("")
    logger.info("**************************************************************")
    logger.info("**                       预测给定词的相似词                 **")
    logger.info("**************************************************************")
    val similarWord = new SimilarWord()
    similarWord.printSimilarWd(spark, modelPath, new ReadFile, preWordFilePath)
  }
}

package cn.ipanel

import org.apache.spark.sql.SparkSession

object PredictSimWd {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("hadoopFileTest")
      .master("local")
      .getOrCreate()

    // 设置spark日志级别
    spark.sparkContext.setLogLevel("WARN")

    // w2v模型存放路径
    val modelPath = args(2)
    // 要查找的相似词
    val preWordFilePath = args(3)

    println("==================== 相似词预测 ==========================")
    val similarWord = new SimilarWord()
    similarWord.printSimilarWd(spark, modelPath, new ReadFile, preWordFilePath)
    println("Done!")
  }
}

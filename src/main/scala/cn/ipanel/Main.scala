package cn.ipanel

import org.apache.spark.sql.SparkSession

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("hadoopFileTest")
      .master("local")
      .getOrCreate()

    // 设置spark日志级别
    spark.sparkContext.setLogLevel("WARN")

    // 训练文件路径
    val fileName: String = args(0)
    // 参与训练的csv文件列名
    val colName: String = args(1)
    // 模型保存路径
    val modelPath: String = args(2)
    // 被预测词文件存放路径
    val predictWordFile: String = args(3)

    println("fileName: " + fileName)
    println("colName: " + colName)
    println("modelPath: " + modelPath)
    println("predictWordFile: " + predictWordFile)

    println("==================== 读取csv文件 ==========================")
    val readFile = new ReadFile
    val csvFile = readFile.readCSV(fileName, colName, spark)

    println("==================== ansj分词 ==========================")
    val wordSeg = new WordSegment
    val seg = wordSeg.ansjSeg(csvFile, spark)

    println("==================== 训练word2vec模型 ==========================")
    println("训练时间和语料库规模以及向量空间维度有关，会比较长。。。。。。")
    val trainModel = new TrainModel
    val predWordModel = trainModel.train(seg, modelPath)


    println("==================== 相似词预测 ==========================")
    val similarWord = new SimilarWord()
    similarWord.printSimilarWd(spark, modelPath, readFile, predictWordFile)



  }
}

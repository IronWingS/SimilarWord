package cn.ipanel

import org.apache.spark.ml.feature.Word2VecModel
import org.apache.spark.sql.{DataFrame, SparkSession}

class SimilarWord {
  def printSimilarWd(spark: SparkSession, modelPath: String, readFile: ReadFile, preWordFilePath: String): Unit = {

    // 读取被预测词文件，并转换为Array
    import spark.implicits._
    val preWordArr = readFile.readTXT(preWordFilePath, spark).map(_.toString()).collect()
    // 加载训练好的w2v模型
    val model = Word2VecModel.load(modelPath)
    // 替换被预测词Array的非法字符，遍历数组并使用训练好的模型对其进行预测
    preWordArr.foreach(
      _.replace("[", "")
        .replace("]", "")
        .replace("，", ",")
        .split(",")
        .foreach(x => {
          // w2v预测的结果
          val like = model.findSynonyms(x, 10)
          println("\" " + x + " \"" + " 的相似词以及相似概率为：")
          like.foreach(println(_))
          println()
        }
        )
    )
  }
}

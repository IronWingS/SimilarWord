package cn.ipanel

import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.sql.DataFrame

class TrainModel {
  def train(segmentResultDF: DataFrame, path:String) = {
    //创建Word2Vec对象
    val word2Vec = new Word2Vec()
      .setInputCol("content")
      .setOutputCol("result")
      .setVectorSize(100)
      .setMinCount(0)

    //训练模型
    val model = word2Vec.fit(segmentResultDF)
    println("OK! 开始保存模型 ")
    // 保存模型
    model.write.overwrite().save(path)
    println("Done!")
  }
}

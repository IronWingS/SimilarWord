package cn.ipanel

import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.sql.DataFrame
import org.slf4j.LoggerFactory

class TrainModel {
  def train(segmentResultDF: DataFrame, path:String) = {

    val log = LoggerFactory.getLogger("simword")
    log.info("==================== 训练word2vec模型 ==========================")
    log.info("约4分钟左右")
    //创建Word2Vec对象
    val word2Vec = new Word2Vec()
      .setInputCol("content")
      .setOutputCol("result")
      .setVectorSize(100)
      .setMinCount(0)

    //训练模型
    val model = word2Vec.fit(segmentResultDF)
    log.info("模型训练完成")
    // 保存模型
    model.write.overwrite().save(path)
    model
  }
}

package cn.ipanel

import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.DicAnalysis
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

class WordSegment {
  def ansjSeg(cleanFileDF:DataFrame, spark:SparkSession,logger: Logger): DataFrame = {
    logger.info("==================== ansj分词 ==========================")

    val stop = new StopRecognition()
    stop.insertStopNatures("w") //过滤掉标点
    stop.insertStopNatures("m") //过滤掉m词性
    stop.insertStopNatures("null") //过滤null词性
    stop.insertStopNatures("<br />") //过滤<br　/>词性
    stop.insertStopNatures(":")
    stop.insertStopNatures("'")

    import spark.implicits._

    val segmentResultDF = cleanFileDF.map(row => {
      DicAnalysis.parse(row.toString())
        .recognition(stop)
        .toStringWithOutNature()
        .trim
        .split(",")
    }).toDF("content")


    val partSegResultDF = segmentResultDF.limit(5)

    val partSegResultArr = partSegResultDF.map(_.toString()).collect()
    logger.info("分词的部分结果如下：")
    partSegResultArr.foreach(logger.info(_))

    //    segmentResultDF.map(_.toString())
    //      .coalesce(1).write
    //      .option("header",true)
    //      .mode("overwrite")
    //      .option("sep","|")
    //      .text("/output/simword/segmentResul")

    logger.info("分词完成")
    segmentResultDF
  }
}

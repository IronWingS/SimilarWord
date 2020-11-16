package cn.ipanel

import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.DicAnalysis
import org.apache.spark.sql.{DataFrame, SparkSession}

class WordSegment {
  def ansjSeg(cleanFileDF:DataFrame, spark:SparkSession): DataFrame = {

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

    segmentResultDF.limit(10).show()
    println("输入文件中的数据条数为:   " + segmentResultDF.count())

    println("Done!")
    segmentResultDF
  }
}

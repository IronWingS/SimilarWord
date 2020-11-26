package com.ipanel.user

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SparkSession}

object Main {
  //  首先读入hive表，得到某个用户30天内的所有数据
  //  然后利用spark读入mysql表中的数据，并作为DataFrame
  // 然后利用各种各样的条件筛选之类的方法，得到想要的数据
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("trainpre")
      .master("local")
      .getOrCreate()
    // set log level
    spark.sparkContext.setLogLevel("WARN")

        val userID: String = "50000143"
        // select last 30 days value from t_user_log_info
        val userSql =
          s"""
             |SELECT
             |  user_id, program_id, series_id, duration, day
             |FROM
             |  t_user_log_info
             |DATEDIFF(time, from_unixtime(unix_timestamp(), 'yyyy-MM-dd HH:mm:ss')) < 30
             |LIMIT 10
             |""".stripMargin

        val userLogDF: DataFrame = spark.sql(userSql)
        userLogDF.show()

        // 总共观看了多少次
        val totalCount = userLogDF.groupBy("user_id").count()

        // 看了多少天
        val value1 = userLogDF.dropDuplicates("day").groupBy("user_id").count()


    // spark read mysql data as dataframe
    val properties = new Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "mysql")

    val personDF = spark.read.jdbc("jdbc:mysql://localhost:3306/learn", "t_user_log_info", properties)

    val count = personDF.count()

    import spark.implicits._
    val value = personDF.rdd.map(x => (x(0).toString, x(1).toString, x(2).toString, x(15).toString, count))
      .toDF("user_id", "program_name", "series_name", "day", "count")


    value.dropDuplicates("day").show()


  }
}

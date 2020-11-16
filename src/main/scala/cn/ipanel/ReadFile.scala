package cn.ipanel

import org.apache.spark.sql.{DataFrame, SparkSession}

class ReadFile {
  def readCSV(fileName: String, col: String, spark: SparkSession): DataFrame = {
    val cleanFileDF = spark.read.format("csv")
      .option("delimiter", ",")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(fileName)
      .select(col)

    println("Done!")
    cleanFileDF
  }

  def readTXT(file:String, spark:SparkSession):DataFrame = {
    val txtDF = spark.read.text(file)
    println("Done!")
    txtDF
  }

}

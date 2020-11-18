import cn.ipanel.ReadFile
import org.apache.spark.sql.SparkSession
import org.junit.Test

class test {

  @Test
  def DF2List(): Unit = {
    val spark = SparkSession.builder()
      .appName("hadoopFileTest")
      .master("local")
      .getOrCreate()

    // 设置打印的日志级别
    spark.sparkContext.setLogLevel("WARN")

    val readFile = new ReadFile
    val txtDF = readFile.readTXT("preWord.txt", spark).toDF("words")

    txtDF.show(false)

    import spark.implicits._

    val rows = txtDF.map(_.toString()).collect().toList


    for (x <- rows) {
      val y = x.replace("[", "").replace("]", "").split("，")
      y.foreach(x =>
        if (x.equals("清朝")) println(x)
        else if (x.equals("李莲英")) println(x)
        else if (x.equals("明朝")) println(x)
        else if (x.equals("东汉")) println(x)
        else println("nothing")
      )
    }


    val tmp = txtDF.toString().split(",")

  }

  @Test
  def replaceTest():Unit = {
    val s = "[清朝,李莲英,明朝,东汉]"
    s.replace("[","")
  }


  @Test
  def logTest() = {
    import org.apache.log4j.LogManager
    import org.apache.log4j.Logger
    import org.apache.log4j.LogManager

    val logger = LogManager.getLogger(classOf[test].getName)
    logger.error("Did it again!");

    logger.info("这是info级信息");

    logger.debug("这是debug级信息");

    logger.warn("这是warn级信息");

    logger.fatal("严重错误");

    logger.trace("exit");
    logger.error("hello")
  }

}

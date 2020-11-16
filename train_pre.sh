#########################################
#相似词模型训练和预测执行脚本
#2020/11/15
#########################################
#!/bin/bash
if [ $# -eq 0 ];then
  day=`date +%Y%m%d "-d 1 days ago"`
  else
    day=$1
fi

. configuration.properties

SPARK_HOME=/bigdata/spark-2.4.7

DRIVER_MEM=4g
NUM_EXECUTORS=10
EXECUTOR_MEM=4g
EXECUTOR_CORES=2

FileName=$fileName
ColName=$colName
ModelPath=$modelPath
PredictWordFile=$predictWordFile

echo $FileName
echo $ColName
echo $ModelPath
echo $PredictWordFile


echo "======================================== 训练Word2Vec并预测 =========================================="
$SPARK_HOME/bin/spark-submit --master yarn --driver-memory $DRIVER_MEM --executor-memory $EXECUTOR_MEM \
   --num-executors $NUM_EXECUTORS --executor-cores $EXECUTOR_CORES --conf spark.io.compression.codec=lz4 \
   --conf spark.sql.shuffle.partitions=32  --conf spark.default.parallelism=32\
   --conf spark.io.compression.codec=snappy --conf spark.shuffle.manager=sort \
   --conf spark.memory.storageFraction=0.6 --conf spark.rdd.compress=true\
   --conf spark.network.timeout=1200 \
   --class cn.ipanel.Main /root/similar_word/similar_word-jar-with-dependencies.jar > /root/similar_word/train_pre.log \
    $FileName $ColName $ModelPath $PredictWordFile

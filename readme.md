训练新模型，执行train_pre.sh

用已经训练好的模型预测其他词，执行predict.sh

输出日志为logs/similarwd.log


配置文件configuration.properties中的文件路径都是hdfs路径，训练前需要将相关的文件上传到配置文件中指定的路径下。

```properties
# 训练语料文件的hdfs存放路径
fileName=/input/t_media_series_i1.csv
# csv文件的列
colName=f_media_desc
# 训练好的模型的hdfs输出路径
modelPath=/output/simword/word2vec.model
# 被预测词文件的hdfs存放路径
predictWordFile=/input/predictWordFile.txt
```


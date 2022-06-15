# function-json
前端string类型到后端date类型,后端date类型到前端字符串类型
````
spring:
  #前端显示统一格式日期,时间戳统一转换为指定格式，时区修改为东八区
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
````
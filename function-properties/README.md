# 读取配置文件
## 场景
- 读取配置文件属性到非静态变量
- 读取配置文件属性到实体类
- 读取配置文件属性到静态变量
- 读取文件

## 配置文件属性赋值给静态变量
- 用到set()方法(set方法需要为非静态方法)，在方法上使用@Value，方法中将入参值赋给静态变量，同时类上需要加入@Component注解，方法名和参数名可以任意命名。
- 注解修饰非静态方法，在@PostConstruct注解修饰的方法中将@VAlue修饰的非静态变量的赋值给静态变量
- 通过@Component @ConfigurationProperties(prefix = "mail")修饰properties实体类，注入到工具类中，直接当调用其属性。
- 三种方法都需要使用@Component注解，配合spring语法来做。

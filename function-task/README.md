# spring 定时任务 异步
## spring定时任务
> 主流定时任务分三种方式实现
- cron表达式
- 固定频率
- 固定间隔
### 实现步骤
- 入口程序XXXApplication添加注解@EnableScheduling，如果有异步任务需求需增加注解@EnableAsync。
- 创建定时任务类添加@Compoment注解将类纳入spring bean管理，创建方法添加@Scheduled注解配置cron定时属性
### @Scheduled注解详解
- fixedRate属性
> fixedRate属:固定频率：如果业务执行时间超过固定频率时间，那么业务执行完毕会立即执行该任务，也就是说固定速率时间小于业务执行时间时，执行频率会变大。
- fixedDelay属性
> fixedDelay属性:固定间隔：方法执行完成后延迟配置的时间再次执行该方法。
- initialDelay 属性
> initialDelay属性：该属性跟上面的fixedDelay、fixedRate有着密切的关系，该属性的作用是第一次执行延迟时间，只是做延迟的设定，一般用于启动程序后第一次执行的时间，并不会控制其他逻辑，所以要配合fixedDelay或者fixedRate来使用。
- 线程使用说明
> @Scheduled 如果不搭配异步来直接使用，使用的是只有一个线程的线程池，所以如果有很多线程，每个线程执行时间较长，那么多数任将会争抢线程，导致任务执行时间不符合预期。

### cron表达式
- cron属性值是一个String类型的时间表达式，各部分的含义如下：
  
位置 | 说明
--- | ---
Second |  可出现", - * /“四个字符，有效范围为0-59的整数
Minutes | 可出现”, - * /“四个字符，有效范围为0-59的整数
Hours | 可出现”, - * /“四个字符，有效范围为0-23的整数
DayofMonth | 可出现”, - * / ? L W C"八个字符，有效范围为0-31的整数
Month | 可出现", - * /“四个字符，有效范围为1-12的整数或JAN-DEc
DayofWeek | 可出现”, - * / ? L C #“四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一，依次类推
Year | 可出现”, - * /"四个字符，有效范围为1970-2099年

- cron通配符

值 | 说明
--- | ---
*  | 所有值，在秒字段上表示每秒执行，在月字段上表示每月执行
？ | 不指定值，不需要关系当前指定的字段的值，比如每天每天都执行不需要关心周几，就可以将周的字段设置为？
- | 区间，如秒的0-2，表示0秒，1秒，2秒都执行
， | 多个值，比如在0秒，20秒触发，可以将秒字段设置为0,20
/ | 递增触发，比如秒的字段上设为 0/3,从0秒开始，每3秒执行一次
L | 最后，只允许在日字段或周字段上，在日字段上表示最后一天，在周字段上3L表示最后一个周四
W | 只允许在日字段上使用，表示举例最近的该日的工作日。工作日指的是周一至周五
# | 只允许在周字段上，表示每月的第几个周几，如2#3，每月的第三个周二

示例：
- “0 0 12 * * ?” 每天中午十二点触发
- “0 30 10 ? * *” 每天早上10：30触发
- “0 15 10 * * ?” 每天早上10：15触发
- “0 15 10 * * ? *” 每天早上10：15触发
- “0 15 10 * * ? 2018” 2018年的每天早上10：15触发
- “0 * 14 * * ?” 每天从下午2点开始到2点59分每分钟一次触发
- “0 0/5 14 * * ?” 每天从下午2点开始到2：55分结束每5分钟一次触发
- “0 0/5 14,18 * * ?” 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
- “0 0-5 14 * * ?” 每天14:00至14:05每分钟一次触发
- “0 10,44 14 ? 3 WED” 三月的每周三的14：10和14：44触发
- 0 15 10 ? * MON-FRI” 每个周一、周二、周三、周四、周五的10：15触发

### 线程池
- @Scheduled 默认使用的线程池进行替换扩大线程数
````
@Component
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(executorService());
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService() {
        return Executors.newScheduledThreadPool(20);
    }
}
````
- 自定义线程池，通过 @EnableAsync开启
````
@Configuration
@Slf4j
public class TaskConfig {
    @Autowired
    ImocExecutorProperties imocExecutorProperties;
    @Bean("imocExcutor")
    public Executor imocExcutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(imocExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(imocExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(imocExecutorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(imocExecutorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(imocExecutorProperties.getThreadNamePrefix());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setRejectedExecutionHandler(new CustomRejectionHandler());
        executor.initialize();

        return executor;
    }
}
````

````
@Scheduled(cron = "*/5 * * * * ?")
@Async("imocExcutor")
````
### 事务、异步失效
> spring的声明式事务,和Async是基于代理模式的，相当于spring创建了一个代理类，如果通过this.xxx来调用方法，调用的不是代理类的方法，会导致上述配置失效。
> 可以通过spring提供的工具类获取当前bean，来调用方法
````
//解决事务、Async失效
private TaskService getService(){
    return SpringUtil.getBean(this.getClass());   //SpringUtil工具类见下面代码
}
````
失效情况：
- private、static、final的使用
- 通过this.xxx()调用当前类的方法
- 使用默认的事务处理方式
- 线程Thread中声明式事务不起作用

### 线程池拒绝策略
- AbortPolicy拒绝并抛出异常
- DiscardPolicy丢弃任务并不做任何操作
- DiscardOldestPolicy丢弃最老的任务
- CallerRunsPolicy调用主线程处理
- 自定义拒绝策略 - 通过反射拿到异步方法参数做其他处理
 
> 创建任务执行时首先判断是否大于核心线程数，超过核心线程数后，新进入的任务开始进入队列中，再大于队列数后会继续创建线程执行任务，直到大于最大线程数后就会开始执行拒绝策略。

### 配置线程池方式
- 为@Scheduled配置默认线程池,如果不适用默认线程池可通过@Async指定其他线程池
````
@Component
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(executorService());
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService() {
        return Executors.newScheduledThreadPool(20);
    }
}
````
- 为@Async配置线程池      
直接配置线程池bean，然后在@Async中指定，该种方式无法配置统一异常处理。
````
@Slf4j
@Profile({"prod"})
public class TaskConfig {
    @Autowired
    ImocExecutorProperties imocExecutorProperties;
    @Bean("imocExcutor")
    public Executor imocExcutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(imocExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(imocExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(imocExecutorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(imocExecutorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(imocExecutorProperties.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //executor.setRejectedExecutionHandler(new CustomRejectionHandler());
        executor.initialize();

        return executor;
    }
}
````
实现AsyncConfigurer接口，重写定义线程池方法、异常处理方法
````
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {
    @Autowired
    ImocExecutorProperties imocExecutorProperties;

    /**
     * 自定义线程池，控制并发数，
     * @return
     */
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(imocExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(imocExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(imocExecutorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(imocExecutorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(imocExecutorProperties.getThreadNamePrefix());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setRejectedExecutionHandler(new CustomRejectionHandler());
        executor.initialize();

        return executor;
    }

    /**
     * 统一处理异常
     * 只能捕获异步方法中抛出的异常，拒绝策略异常不会被捕获
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}
````
  
### 其他
线程核心数不一定是cpu核心数，某些业务耗时较长，线程数太少可能导致频繁触发拒绝策略，线程核心数适当扩大有利于和其他非本业务争抢线程，项目整体线程时间边长，本业务整体时间变短。

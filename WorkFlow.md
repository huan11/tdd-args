# TDD 工作流程

## 需求分析

清晰的产品定义（因为是单一的一个模块和服务，不需要考虑这个）

1. 【以终为始】产出物是什么？（输入，输出）

   * 形态：Java解析类

   * 输入：

     样例一： `-l -p 8080 -d /usr/logs`

     样例二（列表参数）：`-g this is a list -d 1 2-3 5`

   * 输出：

     1. 参数与模式匹配

        没有默认值，则指定默认值

     2. 参数与模式不匹配， 给出错误信息

## 【概要设计】产出物的规格应该是什么样的？

   解析方法的规格

   * 使用 JDBC 传统的写法 

   * 使用 Anotation 的方式 ✅
     

     ![image-20240926073603756](./img/image-20240926073603756-1020840.png)

## 【详细设计】（数据结构 + 算法）

   数据结构

   * 思路一：根据下标直接进行读取

   * 思路二：根据标位出现的位置进行分段，分段数组

   * 思路三：Map

   算法 TODO

## 把最终要实现的功能（样例）写成测试

注意事项：如果根据上面的测试驱动开发，步子太大了，处理的情况太多了

* bool 型
* 整型
* string 型
* 字符串数组型
* 整型数组型

TDD 是通过一系列的测试控制我们整个研发的节奏，所以我们要控制测试的粒度，不能太大



有没有什么通过一步，两步，三步这样的方式进行开发？并且让我们的第一个测试尽快通过测试？

* 考虑  happy path

  * 分而治之，分解成单独的任务
  * 合并

* 考虑 Sad Path

* 考虑 default value
  ![image-20240926075358356](./img/image-20240926075358356.png)

## 产出任务列表

大概10个测试的样子，到第四个测试的时候，我们要的第一个功能从正向来看就实现了

![image-20240926075820299](./img/image-20240926075820299.png)



```
// Single Option:
// TODO:      - Bool -l
// TODO:      - Integer -p 8080
// TODO:      - String -d /usr/logs
// TODO:      - multi options: -l -p 8080 -d /usr/logs

// sad path:
// TODO:      - bool -l t / -l t f
// TODO:      - int -p / -p 8080 8081
// TODO:      - string -d / -d /usr/logs /usr/vars

// default value
// TODO:      - bool : false
// TODO:      - int : 0
// TODO:      - string ""
```

## Before 进入红绿重构循环

1. Disable example test
2. Make the code pass compilation quickly.





## 进入First 红绿重构循环

3 Status:

* TODO 
* ~~xxxTask~~  Finished
* **// TODO:  XXX** in progress



任务列表

> // Single Option:
> **// TODO:      - Bool -l**
> // TODO:      - Integer -p 8080
> // TODO:      - String -d /usr/logs
> // TODO:      - multi options: -l -p 8080 -d /usr/logs
>
> // sad path:
> // TODO:      - bool -l t / -l t f
> // TODO:      - int -p / -p 8080 8081
> // TODO:      - string -d / -d /usr/logs /usr/vars
>
> // default value
> // TODO:      - bool : false
> // TODO:      - int : 0
> // TODO:      - string ""



### Red Failed Test

![image-20241108101357322](./img/image-20241108101357322.png)



### Fix Red test in the quick

（最简单的方式实现，不要考虑边界，并且不要担心犯下的任何罪恶，强迫你编程）

![image-20241108104222923](./img/image-20241108104222923.png)



### 如何让上面的代码变成我最终要实现的功能？-- 写相反的情况

![image-20241108104902829](./img/image-20241108104902829.png)



### Green

![image-20241108213715401](./img/image-20241108213715401.png)



## Update Task List

Mark the Task as finished

- // Single Option:
  * ~~// TODO:      - Bool -l~~
  * **// TODO:      - Integer -p 8080**
  * // TODO:      - String -d /usr/logs
  * // TODO:      - multi options: -l -p 8080 -d /usr/logs
- // sad path:
  - // TODO:      - bool -l t / -l t f
  - // TODO:      - int -p / -p 8080 8081
  - // TODO:      - string -d / -d /usr/logs /usr/vars
- // default value
  * // TODO:      - bool : false
  * // TODO:      - int : 0
  * // TODO:      - string ""



## 进入后续红绿重构循环

注意：

1. 如何写测试的时候就不要动生产代码
2. 如何写生产代码的时候就

## 考虑是否要进行重构？

进入重构的条件：

1. 第一是测试都是绿的，也就是当前功能正常（Green）
1. 第二是坏味道足够明显。（Bad Smell）



当前代码是否满足两个条件

1. green ✅
2. bad smell ✅
   1. 一个明显的面向对象误用的坏味道——分支语句（Switch Statements、Object-Oriented Abusers）
      重构手法：利用多态替换条件分支（Replacing Conditional with Polymorphism）





利用多态替换条件分支（Replacing Conditional with Polymorphism）



## 开始重构

1. 设计

2. delegate 然后 inline 旧实现

![image-20241109152903666](./img/image-20241109152903666.png)



去掉手术台，改变代码调用链路。

![image-20241109153145917](./img/image-20241109153145917.png)



## 开始重构2 -- 代码结构的重复 

解决方案：

1. 策略模式 Stratege pattern 
2. 利用函数式编程 
   1. 把String.valueOf(value); 变成 function field



能不能把父类中别被子类覆盖的方法删掉呢？

1. 把父类中别被子类覆盖的方法的主体提取成一个 Field （Extract Funtional Field）
   ![image-20241109192038971](./img/image-20241109192038971.png)

2. ...

3. 方式1:
   最后使用 StringParser 的地方可以用 New IntParser() 的方式书写，然后把 函数（String::valueOf）传过去，这样，就合二为一了。
   ![image-20241109195220636](./img/image-20241109195220636.png)

   

   方式2:
   将构造函数变成工厂方法
   https://www.jetbrains.com/help/idea/replace-constructor-with-factory-method.html
   

 

## 更新任务列表

Mark the Task as finished

- // Single Option:
  * ~~// TODO:      - Bool -l~~
  * ~~// TODO:      - Integer -p 8080~~
  * ~~// TODO:      - String -d /usr/logs~~
  * ~~// TODO:      - multi options: -l -p 8080 -d /usr/logs~~
- // sad path:
  - // TODO:      - bool -l t / -l t f
  - // TODO:      - int -p / -p 8080 8081
  - // TODO:      - string -d / -d /usr/logs /usr/vars
- // default value
  * // TODO:      - bool : false
  * // TODO:      - int : 0
  * // TODO:      - string ""

## New 调整任务列表

架构调整了，任务列表也要跟着动

![img](./img/6bf1a4e2548e5139a021278ddc0d53de.jpg)

* BooleanOptionParserTest
  * // sad path:
    // TODO: -bool -l t / -l t f
  * // default:
    // TODO: - bool : false

* SingleValuedOptionParserTest
  * // sad path:
    // TODO: - int -p/ -p 8080 8081
    // TODO: - string -d/ -d /usr/logs /usr/vars
  * // default value:
    // TODO: -int :0
    // TODO: - string ""



## 进入红绿循环

![image-20241110111535000](./img/image-20241110111535000.png)

但是引起了另外一个测试的超界

![image-20241110111935401](./img/image-20241110111935401.png)



## 测试过程中发现某两个测试数据属于同一类测试--参数化测试

![image-20241110160612100](./img/image-20241110160612100.png)



## 测试代码重构 

任务列表的改变反应了架构的改变。

测试是围绕着系统的单元进行组织的。

随着单元的演变，测试也需要进行重构。

让对应的功能归属到各种不同的测试当中去。

**重构测试的时候，不要改生产代码。重构生产代码的时候，不要改测试。**

而不是说写生产代码的时候，不要改测试。重点是重构的时候，要注意分离关注点，是重构生产代码还是测试代码。 





## 用测试重现 bug

这个地方可能有一个bug ，我们用 测试重现出来

![image-20241110190705890](./img/image-20241110190705890.png)

测试代码

![image-20241110190749383](./img/image-20241110190749383.png)



## 审视代码坏味道

尝试用方法提取意图：

![image-20241110192554513](./img/image-20241110192554513.png)



上面的操作虽然可以，但是仍然暴露了非常多的细节，

Bad Smell ： 我们选择的实现方式本身就是一种不直观的方法，通过重构，通过加注释只能让他略微直观，我们可以考虑变换一种实现方式。

* 通过取到下一个标志位的下标后，将参数不足和参数过多的问题转化为当前标志位到下一个标志位之间元素个数的问题 （< 1 , 不足； > 1 , 多了）

## 审视代码坏味道-实现方式可以复用

这两个代码可以用同一个实现方式 getValuesBetweenCurrentAndNextFlag

![image-20241110195702061](./img/image-20241110195702061.png)



效果

![image-20241110200614530](./img/image-20241110200614530.png)

## 审视代码坏味道--相同的结构

![image-20241110201055706](./img/image-20241110201055706.png)



重构方式：使用 Optional 改造

![image-20241110203017210](./img/image-20241110203017210.png)



提取成方法后，移除变量，去掉 else

![image-20241110204144657](./img/image-20241110204144657.png)





## 重组代码结构--让上下文相关的部分放到一起

问题：BooleanOptionParser 依赖的 values 方法的实现在 SingValueOptionParser 里面。

思路：

1. 使用 基类进行重组，代码编程继承结构

2. 利用新的 java 语法

   BooleanOptionParser的 接口 OptionParser 是一个只有一个方法的接口，可以转成一个 lambda

   而BooleanOptionParser 可以用一个匿名的 lambda 函数代表整个类







Step1 ：**脱离对 BooleanOptionParser 对依赖**。

生成 构造函数
工厂方法替代构造函数
尽可能使用 interface



Step2: **在工厂方法里面 做手术。**

1. 修改实现方式：在工厂方法里面 使用匿名函数进行实现。



Step3：工厂方法改名



Step4: 搬移工厂方法 move member 



## 代码风格不一致问题

一个是继承的实现方式，一个是 lambda 的实现方式。 

![image-20241110211715615](./img/image-20241110211715615.png)



### 将方法变成 private static -- introduce parameter







## 合并类之后，测试名字略微不合适



使用 Junit 5 的 nested test 进行



# 列表参数需求  example2 

//TODO: -g this is a list -d 1 2 -3 5

### 任务分解--任务列表

//TODO: -g "this" "is" {"this", is"}
//TODO: default value []
//TODO: -d a throw exception



## 设计函数签名

```
public static <T> OptionParser<T[]> list(Function<String, T> valueParser) {
    return null;
}
```



## 补充一个测试

```
[ListOptionParser][should_use_empty_array_as_default_value][red-已经实现了]
```

  

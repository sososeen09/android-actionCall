# ActionCall框架

[![](https://jitpack.io/v/sososeen09/android-actionCall.svg)](https://jitpack.io/#sososeen09/android-actionCall)

在根 build.gradle 中 添加：

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

添加依赖

```
dependencies {
	implementation 'com.github.sososeen09:android-actionCall:Tag'
}
```

## 1 前置条件场景

1. 登录后去订阅书籍
2. 充值后去订阅书籍
3. 充会员后刷新页面
4. 充值成功之后领取奖励
5. ...

## 2 动作拆解

1. 需要进行某个操作
2. 查看该操作是否满足某个条件
1. 满足，则直接进行操作
2. 不满足，则去跳转到相关页面去处理条件
1. 条件处理成功，继续进行某种操作
2. 条件处理不成功，放弃操作
3. 还要注意一种情况是用户手动取消，如何判断这种情况？

## 3 角色拆分

1. targetAction，例如进行购买
2. conditionAction，例如是否余额足够，包含两个动作，checkAction（进行检查），doAction（进行相应处理）
3. resultAction，在第二步中进行了处理，但是处理结果是未知的，需要在返回的时候来检查结果是否满足，有两个重点，在什么时候检查，whenCheck，检查什么，whatCheck。当所有的条件都满足，进行某些跳转或者动作，当该动作执行成功之后需要有后续的动作。



## 4 后置条件场景

除了之前跳转页面存在的前置条件，也存在后置条件

1.  进入会员页，然后开通了会员，再返回该页面时应该刷新页面


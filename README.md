### String StringBuffer StringBuilder

   - 区别
        
        1.运行速度 : StringBuilder > StringBuffer > String 
        
        2.线程安全 : StringBUffer 线程安全 / StringBuilder线程不安全
        
        String 之所以慢，是因为 Java 对 String 对象进行操作实际上是一个不断创建新对对象并且将旧对对象回收的一个过程，所以执行速度很慢，而 StringBuffer 和 StringBuilder 的对象是变量，对变量进行操作就是直接对该对象进行更改，而不需要进行创建和回收的操作，所以速度要比 String 快很多。
        
   - 底层数据结构以及实现原理
      
        String 类不可变，内部维护的 char[] 数组长度不可变，为 final 修饰，而 String 类也是 final 修饰，不存在扩容。字符串拼接，截取都会生成一个新的对象。频繁操作字符串效率低下，因为每次都会生成新的对象。
        
        StringBuilder 类内部维护可变长度 char[]，初始化数组容量为16，存在扩容，其 append 拼接字符串方法内部调用 System 的 native 方法，进行数组的拷贝，不会重新生成新的 StringBuilder 对象。非线程安全的字符串操作类，每次调用 toString 方法重新生成的 String 对象，不会共享 StringBuilder 对象内部的 char[]，会进行一次 char[] 的 copy 操作。
        
        StringBuffer 类内部维护可变长度 char[]，基本上与 StringBuilder 一致，但其为线程安全但字符串操作类，大部分方法都采用了 synchronized 关键字修饰，以此来实现在多线程下的字符串安全性，其 toString 方法重新生成的 String 对象，会共享 StringBuffer 对象中的 toStringCache 属性(char[])，但是每次的 StringBuffer 对象修改都会置 null 该属性值。
        
        适用场景
        String：适用于少量的字符串操作的情况
        StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
        StringBuffer：适用于多线程下在字符缓冲区进行大量操作的情况
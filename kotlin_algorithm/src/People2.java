import java.security.ProtectionDomain;

public class People2 {

    String name; // 没有添加final 和kotlin进行对比

    public People2(String name) {
        System.out.println("People2 构造器传入的name参数:" + name);
        this.name = "People";
    }

    void test1() {
        System.out.println(this.getClass());
        // 如果说通过引用地址来查找,那么这个this指代的实例中是有name属性,
        // 那么如果先从上往下找,先从父类中查找,在往下(子类)中查找,有点说不过去,因为父类中肯定有这个属性,
        // 要不然编译器不通过,既然父类中有了,我觉得就没有必要再往下找了
        // TODO: 2021/8/22 java并中没有属性覆盖的概念,只有方法覆盖的概念
        // 如果说属性可以覆盖,那么下面打印的应该是zyt,而不是People,所以调用的还是父类中的属性
        System.out.println(this.name); // 子类和父类的属性名字一样的话,调用的也是父类中的属性,那么下面的方法为什么没有问题呢
        this.overrideFun();// 因为JAVA中有方法覆盖的概念,子类实例调用的还是子类中的方法(属性不适用)
    }

    public static void main(String[] args) {
        new Student2("zyt").test1();
    }

    void overrideFun() {
        System.out.println("overrideFun in People2");
    }

}

class Student2 extends People2 {

    String name;

    public Student2(String name) {
        super(name);
        this.name = name;
    }

    void overrideFun() {
        System.out.println("overrideFun in Student2");
    }
}

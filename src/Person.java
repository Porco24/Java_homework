import java.io.Console;

public abstract class Person
{
    String name;
    int id;
    int age;
    String sex;
    int wages;
    int unfinishWorks;

    IworkProduction workProduction;

    Person(String name, int id, int age, String sex, int wages, int unfinishWorks)
    {
        this.name = name;
        this.id = id;
        this.age = age;
        this.sex = sex;
        this.wages = wages;
        this.unfinishWorks = unfinishWorks;
    }

    public String workProduction()
    {
        return workProduction.workProduction();
    }

    @Override
    public String toString()
    {
        return "姓名:"+this.name+",ID:"+this.id+",性别:"+this.sex+",工资:"+this.wages + ","+workProduction();
    }
}

//程序员类
class programmar extends Person
{
    programmar(String name, int id, int age, String sex, int wages, int unfinishWorks)
    {
        super(name, id, age, sex, wages, unfinishWorks);
        workProduction = new programmarWorkProduction(unfinishWorks);
    }
}

//美术类
class artist extends Person
{
    artist(String name, int id, int age, String sex, int wages, int unfinishWorks)
    {
        super(name, id, age, sex, wages, unfinishWorks);
        workProduction = new artistWorkProduction(unfinishWorks);
    }
}

//策划类
class designer extends Person
{
    designer(String name, int id, int age, String sex, int wages, int unfinishWorks)
    {
        super(name, id, age, sex, wages, unfinishWorks);
        workProduction = new designerWorkProduction(unfinishWorks);
    }
}

class test
{
    void test()
    {
        programmar a = new programmar("a",1,20,"男",10000,0);
        System.out.println(a.toString());
    }
    public static void main(String[] args)
    {
        test t =new test();
        t.test();
    }
}

//////////////////////////////////工作进度的方便修改迭代设计模式///////////////////////////////////
//工作进度接口
interface IworkProduction
{
    public String workProduction();
}

class programmarWorkProduction implements IworkProduction
{
    int unfinishWorks;
    programmarWorkProduction(int unfinishWorks)
    {
        this.unfinishWorks = unfinishWorks;
    }
    public String workProduction()
    {
        String result = "剩余Bug数量为：" + unfinishWorks;
        return result;
    }
}

class artistWorkProduction implements IworkProduction
{
    int unfinishWorks;
    artistWorkProduction(int unfinishWorks)
    {
        this.unfinishWorks = unfinishWorks;
    }
    public String workProduction()
    {
        String result = "剩余模型数量为：" + unfinishWorks;
        return result;
    }
}

class designerWorkProduction implements IworkProduction
{
    int unfinishWorks;
    designerWorkProduction(int unfinishWorks)
    {
        this.unfinishWorks = unfinishWorks;
    }
    public String workProduction()
    {
        String result = "剩余策划案数量为：" + unfinishWorks;
        return result;
    }
}
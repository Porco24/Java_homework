import java.time.Period;
import java.util.List;

public abstract class DatabaseAbstract implements IDAO{

    class DataStruct{
        String name;
        int id;
        int age;
        String sex;
        float wages;
        int unfinishWorks;
        String type;
        DataStruct(String name, int id, int age, String sex, float wages, int unfinishWorks,String type)
        {
            this.name = name;
            this.id = id;
            this.age = age;
            this.sex = sex;
            this.wages = wages;
            this.unfinishWorks = unfinishWorks;
            this.type = type;
        }
    }
    void ErrorOutput( String ErrorInfor ){
        System.out.print(ErrorInfor);
    }
    Person DataStruct2Person(DataStruct Input)
    {
        Person Output = null;
        switch (Input.type)
        {
            case "programmar":
                Output = new programmar(Input.name,Input.id,Input.age,Input.sex,Input.wages,Input.unfinishWorks);
                break;
            case "artist":
                Output = new artist(Input.name,Input.id,Input.age,Input.sex,Input.wages,Input.unfinishWorks);
                break;
            case "designer":
                Output = new designer(Input.name,Input.id,Input.age,Input.sex,Input.wages,Input.unfinishWorks);
                break;
        }
        return Output;
    }

    DataStruct Person2DataStruct(Person Input)
    {
        DataStruct Output = null;
        switch(Input.getClass().getName())
        {
            case "programmar":
                Output = new DataStruct(Input.name,Input.id,Input.age,Input.sex,Input.wages,Input.unfinishWorks,"programmar");
                break;
            case "artist":
                Output = new DataStruct(Input.name,Input.id,Input.age,Input.sex,Input.wages,Input.unfinishWorks,"artist");
                break;
            case "designer":
                Output = new DataStruct(Input.name,Input.id,Input.age,Input.sex,Input.wages,Input.unfinishWorks, "designer");
                break;
        }
        return Output;
    }
    abstract boolean ConnectDB(String Path);                //连接数据库
    abstract boolean DisconnectDB();                        //断开数据库链接
    public abstract List<Person> findPersonByNmae(String name);          //通过姓名查找人员
    public abstract Person findPersonByID(int id);           //通过ID查找人员
    public abstract boolean addPerson(Person person);            //添加人员
    public abstract boolean delletePerson(int id);           //删除人员
    public abstract List<Person> allPositionPerson(String positionName);         //查找该职位的所有人员
    public abstract boolean changeWages(int id, float Newages);         //改变工资
    public abstract List<Person> allPerson();            //查找所有人


}

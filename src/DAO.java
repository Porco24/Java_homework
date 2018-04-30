import java.util.List;

public interface DAO
{
    public Person findPersonByNmae(String name);      //通过姓名查找人员
    public Person findPersonByID(int id);            //通过ID查找人员
    public boolean addPerson(Person person);        //添加人员
    public boolean delletePerson(int id);          //删除人员
}

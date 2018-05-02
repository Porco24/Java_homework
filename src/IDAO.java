import java.util.List;

public interface IDAO
{
    public List<Person> findPersonByNmae(String name);          //通过姓名查找人员
    public Person findPersonByID(int id);           //通过ID查找人员
    public boolean addPerson(Person person);            //添加人员
    public boolean delletePerson(int id);           //删除人员
    public List<Person> allPositionPerson(String positionName);         //查找该职位的所有人员
    public boolean changeWages(int id);         //改变工资
    public List<Person> allPerson();            //查找所有人
}

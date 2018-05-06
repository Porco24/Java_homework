import java.util.List;

public class Database_behave extends DatabaseAbstract {

    private DatabaseAbstract BehaveDB = null;

    public  Database_behave(String type,String Path)
    {
        switch (type)
        {
            case "Access":
                BehaveDB = new Database_Access(Path);
                break;
            case "Xml":
                BehaveDB = new Database_XML(Path);
                break;
             default:
                 ErrorOutput("无此类型数据库");
                 System.exit(-1);
                 break;
        }
    }

    @Override
    boolean ConnectDB(String Path) {
        return false;
    }

    @Override
    boolean DisconnectDB() {
        return false;
    }

    @Override
    public List<Person> findPersonByNmae(String name) {
        return BehaveDB.findPersonByNmae(name);
    }

    @Override
    public Person findPersonByID(int id) {
        return BehaveDB.findPersonByID(id);
    }

    @Override
    public boolean addPerson(Person person) {
        return BehaveDB.addPerson(person);
    }

    @Override
    public boolean delletePerson(int id) {
        return BehaveDB.delletePerson(id);
    }

    @Override
    public List<Person> allPositionPerson(String positionName) {
        return BehaveDB.allPositionPerson(positionName);
    }

    @Override
    public boolean changeWages(int id, float Newages) {
        return BehaveDB.changeWages(id,Newages);
    }

    @Override
    public List<Person> allPerson() {
        return BehaveDB.allPerson();
    }
}

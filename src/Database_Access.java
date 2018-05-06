import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Database_Access extends DatabaseAbstract {

    private String DBPath;
//    private Connection DBConnection = null;
//    private Statement stmt = null;

    public Database_Access(String Path ){
        this.DBPath = Path;
        boolean check = this.ConnectDB(Path);
        if(check!=true)     this.ErrorOutput("ConnectDB_ERROE!");
    }

    private Person Result2Person(ResultSet rs)
    {
        try {
            DataStruct rsData = new DataStruct( rs.getString("personame"),
                                                rs.getInt("id"),
                                                rs.getInt("age"),
                                                rs.getString("sex"),
                                                rs.getFloat("wages"),
                                                rs.getInt("unfinishWorks"),
                                                rs.getString("type"));
            return DataStruct2Person(rsData);
        } catch (SQLException e) {
            this.ErrorOutput("Result2PersonSQL:"+e.toString());
            return null;
        }
    }

    private Connection Getcon()
    {
        Connection DBConnection= null;
        try{
            Class.forName("com.hxtt.sql.access.AccessDriver");
            String url="jdbc:Access:///"+DBPath;
            DBConnection =(Connection) DriverManager.getConnection(url);
        }catch (SQLException e){
            this.ErrorOutput("SQL Exception:"+e.toString());
            return null;
        }catch(ClassNotFoundException CE){
            this.ErrorOutput("ClassNotFoundException"+CE.toString());
            return null;
        }
        return DBConnection;
    }

    @Override
    boolean ConnectDB(String Path) {
        try{
            Class.forName("com.hxtt.sql.access.AccessDriver");
            String url="jdbc:Access:///"+Path;
            Connection DBConnection = DriverManager.getConnection(url);
            Statement stmt = DBConnection.createStatement();
            stmt.close();
            DBConnection.close();
        }catch (SQLException e){
            this.ErrorOutput("SQL Exception:"+e.toString());
            return false;
        }catch(ClassNotFoundException CE){
            this.ErrorOutput("ClassNotFoundException"+CE.toString());
            return false;
        }
        return true;
    }

    @Override
    boolean DisconnectDB() {
        return false;
    }

    @Override
    public List<Person> findPersonByNmae(String name) {
        List<Person> output = new ArrayList<>();
        try {
            Connection DBConnection = Getcon();
            PreparedStatement pstmt = DBConnection.prepareStatement("select * from Person where personame = '"+String.valueOf(name)+"'");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   output.add(Result2Person(rs));
            pstmt.close();
            DBConnection.close();
            return output;
        } catch (SQLException e) {
            ErrorOutput("findPersonByNmae:"+e.toString());
            return null;
        }
    }

    @Override
    public Person findPersonByID(int id) {
        Person output = null;
        try {
            Connection DBConnection = Getcon();
            PreparedStatement pstmt = DBConnection.prepareStatement("select * from Person where id="+id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   output = Result2Person(rs);
            pstmt.close();
            DBConnection.close();
            return output;
        } catch (SQLException e) {
            ErrorOutput("findPersonByID:"+e.toString());
            return null;
        }
    }

    @Override
    public boolean addPerson(Person person) {
        try {
            DataStruct input = Person2DataStruct(person);
            Connection DBConnection = Getcon();
            PreparedStatement pstmt = DBConnection.prepareStatement("INSERT INTO Person (id,type,personame,sex,age,wages,unfinishWorks) VALUES "+
                    "("+input.id+", '"+input.type+"','"+input.name+"','"+input.sex+"',"+input.age+","+input.wages+","+input.unfinishWorks+")");
            pstmt.executeUpdate();
            pstmt.close();
            DBConnection.close();
            return true;
        } catch (SQLException e){
            ErrorOutput("addPersonSQL:"+e.toString());
            return  false;
        }
    }

    @Override
    public boolean delletePerson(int id) {
        try {
            Connection DBConnection = Getcon();
            PreparedStatement pstmt = DBConnection.prepareStatement("DELETE FROM Person WHERE id="+id);
            pstmt.executeUpdate();
            pstmt.close();
            DBConnection.close();
            return true;
        } catch (SQLException e){
            ErrorOutput("delletePersonSQL:"+e.toString());
            return  false;
        }
    }

    @Override
    public List<Person> allPositionPerson(String positionName) {
        List<Person> output = new ArrayList<>();
        try {
            Connection DBConnection = Getcon();
            PreparedStatement pstmt = DBConnection.prepareStatement("select * from Person where type = '"+String.valueOf(positionName)+"'");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   output.add(Result2Person(rs));
            pstmt.close();
            DBConnection.close();
            return output;
        } catch (SQLException e) {
            ErrorOutput("allPositionPerson:"+e.toString());
            return null;
        }
    }

    @Override
    public boolean changeWages(int id, float Newages) {
        try {
            Connection DBConnection = Getcon();
            PreparedStatement pstmt = DBConnection.prepareStatement("UPDATE Person SET wages = "+Newages
                                                                          +" WHERE id = "+id );
            pstmt.executeUpdate();
            pstmt.close();
            DBConnection.close();
            return true;
        } catch (SQLException e){
            ErrorOutput("changeWagesSQL:"+e.toString());
            return  false;
        }
    }

    @Override
    public List<Person> allPerson() {
        List<Person> output = new ArrayList<>();
        try {
            Connection DBConnection = Getcon();
            PreparedStatement pstmt = DBConnection.prepareStatement("select * from Person");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   output.add(Result2Person(rs));
            pstmt.close();
            DBConnection.close();
            return output;
        } catch (SQLException e) {
            ErrorOutput("allPersonSQL:"+e.toString());
            return null;
        }
    }
}

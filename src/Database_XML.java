import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
public class Database_XML extends DatabaseAbstract  {

    private Document XMLDB;
    private String XMLPath;

    public Database_XML(String Path ){
        this.XMLPath = Path;
        boolean check = this.ConnectDB(Path);
        if(check!=true)     this.ErrorOutput("ConnectDB_XML_ERROE!");
    }

    private DataStruct Node2Struct(Node Person_Node) {
        Element e = (Element)Person_Node;
        return new DataStruct(  e.getElementsByTagName("name").item(0).getTextContent(),
                            Integer.parseInt(e.getAttribute("id")),
                            Integer.parseInt(e.getElementsByTagName("age").item(0).getTextContent()),
                            e.getElementsByTagName("sex").item(0).getTextContent(),
                            Float.parseFloat(e.getElementsByTagName("wages").item(0).getTextContent()),
                            Integer.parseInt(e.getElementsByTagName("unfinishWorks").item(0).getTextContent()),
                            e.getAttribute("type")
                          );
    }

    private boolean SaveXML(){
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer former = null;
        try {
            former = tf.newTransformer();
            former.setParameter("version", "1.0");
            former.setParameter("encoding", "UTF-8");
            DOMSource xmlSource = new DOMSource(XMLDB);
            StreamResult outputTarget = new StreamResult(new File(XMLPath));
            former.transform(xmlSource, outputTarget);
        } catch (TransformerConfigurationException e) {
            this.ErrorOutput("SaveXML:"+e.toString());
            return false;
        } catch (TransformerException e) {
            this.ErrorOutput("SaveXML:"+e.toString());
            return false;
        }
        return true;
    }

    @Override
    boolean ConnectDB(String Path) {
        File inputXMLDB = new File(Path);
        DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            XMLDB = dBuilder.parse(inputXMLDB);
        } catch (ParserConfigurationException e) {
            this.ErrorOutput(e.toString());
            return false;
        } catch (SAXException e) {
            this.ErrorOutput(e.toString());
            return false;
        } catch (IOException e) {
            this.ErrorOutput(e.toString());
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
        List<Person> Output = new ArrayList<>();
        NodeList PersonList = XMLDB.getElementsByTagName("Person");
        for(int i=0;i<PersonList.getLength();i++)
        {
            DataStruct child = Node2Struct(PersonList.item(i));
            if(child.name.equals(name)) Output.add(DataStruct2Person(child));
        }
        return Output;
    }

    @Override
    public Person findPersonByID(int id) {
        Person Output = null;
        NodeList PersonList = XMLDB.getElementsByTagName("Person");
        for(int i=0;i<PersonList.getLength();i++)
        {
            DataStruct child = Node2Struct(PersonList.item(i));
            if(child.id== id)
            {
                Output = DataStruct2Person(child);
                break;
            }
        }
        return Output;
    }

    @Override
    public boolean addPerson(Person person) {
        DataStruct InputData = Person2DataStruct(person);
        Element root = (Element) XMLDB.getFirstChild();
        Element personNode = XMLDB.createElement("Person");
        root.appendChild(personNode);
        personNode.setAttribute("id", String.valueOf(InputData.id));
        personNode.setAttribute("type",InputData.type);
        Element name = XMLDB.createElement("name");
        Element sex = XMLDB.createElement("sex");
        Element age = XMLDB.createElement("age");
        Element wages = XMLDB.createElement("wages");
        Element unfinishWorks = XMLDB.createElement("unfinishWorks");
        name.setTextContent(InputData.name);
        sex.setTextContent(InputData.sex);
        age.setTextContent(String.valueOf(InputData.age));
        wages.setTextContent(String.valueOf(InputData.wages));
        unfinishWorks.setTextContent(String.valueOf(InputData.unfinishWorks));
        personNode.appendChild(name);
        personNode.appendChild(sex);
        personNode.appendChild(age);
        personNode.appendChild(wages);
        personNode.appendChild(unfinishWorks);
        if(!this.SaveXML()) return true;
        return false;
    }

    @Override
    public boolean delletePerson(int id) {
        Element root = (Element) XMLDB.getFirstChild();
        NodeList PersonList = XMLDB.getElementsByTagName("Person");
        for(int i=0;i<PersonList.getLength();i++)
        {
            DataStruct child = Node2Struct(PersonList.item(i));
            if(child.id == id)
            {
                root.removeChild(PersonList.item(i));
                if(!this.SaveXML()) return true;
                break;
            }
        }
        return false;
    }

    @Override
    public List<Person> allPositionPerson(String positionName) {
        List<Person> Output = new ArrayList<>();
        NodeList PersonList = XMLDB.getElementsByTagName("Person");
        for(int i=0;i<PersonList.getLength();i++)
        {
            DataStruct child = Node2Struct(PersonList.item(i));
            System.out.println(child.type);
            if(child.type.equals(positionName)) Output.add(DataStruct2Person(child));
        }
        return Output;
    }

    @Override
    public boolean changeWages(int id, float Newages) {
        NodeList PersonList = XMLDB.getElementsByTagName("Person");
        for(int i=0;i<PersonList.getLength();i++)
        {
            DataStruct child = Node2Struct(PersonList.item(i));
            if(child.id == id)
            {
                Element e = (Element)PersonList.item(i);
                NodeList chillest = e.getElementsByTagName("wages");
                chillest.item(0).setTextContent(String.valueOf(Newages));
                if(!this.SaveXML()) return true;
                break;
            }
        }
        return false;
    }

    @Override
    public List<Person> allPerson() {
        List<Person> Output = new ArrayList<>();
        NodeList PersonList = XMLDB.getElementsByTagName("Person");
        for(int i=0;i<PersonList.getLength();i++)
        {
            DataStruct child = Node2Struct(PersonList.item(i));
            Output.add(DataStruct2Person(child));
        }
        return Output;
    }
}

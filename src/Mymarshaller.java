import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.HealthProfile;
import model.Person;
import dao.Storage;

public class Mymarshaller 
{  	
	public static Storage people = new Storage();	// Storage is the Data Access Object

	/* Creates some people to be marshaled and pushes them to the storage */
	public static void initializeDB()
	{
		Person pallino = new Person();
		Person me = new Person("0001", "Andrea", "Orler", "1993-05-21");
		HealthProfile hp = new HealthProfile(68.0, 1.72);
		Person john = new Person("0002", "Firstname", "Lastname", "1985-06-26", hp);

		people.getData().add(pallino);
		people.getData().add(me);
		people.getData().add(john);
	}	

	
	public static void main(String[] args) throws Exception 
	{
		
		initializeDB();
		
		/* Sets up the marshaler and its settings */
		JAXBContext jc = JAXBContext.newInstance(Storage.class);		
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        /* Marshals to the file */
        m.marshal(people, new File("marshalpeople.xml")); 
        m.marshal(people, System.out);			  
    }
}
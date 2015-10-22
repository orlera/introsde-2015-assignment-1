import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import model.HealthProfile;
import model.Person;
import dao.Storage;

public class Jsonizer 
{  	
	public static Storage people = new Storage();

	/* Creates some people to be marshaled and pushes them to the storage */
	public static void initializeDB() 
	{
		Person pallino = new Person();
		Person pallo = new Person("0001", "Andrea", "Orler", "1993-05-21");
		HealthProfile hp = new HealthProfile(68.0, 1.72);
		Person john = new Person("0002", "Firstname", "Lastname", "1985-03-20", hp);

		people.getData().add(pallino);
		people.getData().add(pallo);
		people.getData().add(john);
	}	

	public static void main(String[] args) throws Exception {
		
		initializeDB();
		ObjectMapper mapper = new ObjectMapper();
		
		/* Sets up the JSONizer and its settings */
        JaxbAnnotationModule module = new JaxbAnnotationModule();
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        
        /* Prints on screen and on file */
        System.out.println(result);
        mapper.writeValue(new File("people.json"), people);
    }
}

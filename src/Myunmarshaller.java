import java.io.FileReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import model.Person;
import dao.Storage;
import model.HealthProfile;

public class Myunmarshaller 
{  	
	public static Storage people = new Storage();

	public static void main(String[] args) throws Exception
	{
		int argCount = args.length;
		String filein = argCount == 1 ? args[0] : "marshalpeople.xml";  // Checks if there's a file as argument, otherwise uses the default
		
		/* Sets up the unmarshaller and extrapolates the data */
		JAXBContext jc = JAXBContext.newInstance(Storage.class);
        Unmarshaller um = jc.createUnmarshaller();
        Storage people = (Storage) um.unmarshal(new FileReader(filein));
        List<Person> list = people.getData();
        
        /* Prints the data out in a proper way */
        for (Person person : list)
        {
        	HealthProfile hp = person.gethProfile();
        	System.out.println("Person " + person.getPersonId() +  ":");
        	System.out.println("\tName: " + person.getFirstname());
        	System.out.println("\tSurname: " + person.getLastname());
        	System.out.println("\tBirthdate: " + person.getBirthdate());
        	System.out.println("\tHealth Profile (updated on " + hp.getLastupdate() + "):");
        	System.out.println("\t\tHeight: " + hp.getHeight());
        	System.out.println("\t\tWeight: " + hp.getWeight());
        	System.out.println("\t\tBMI: " + hp.getBMI());
            System.out.println();
        }
    }
}

package model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;

@XmlRootElement(name = "person")	
@XmlType(propOrder = {"firstname", "lastname", "birthdate", "hProfile"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Person 
{
	private String firstname;		
	private String lastname;
	@XmlElement(name="healthprofile")
	private HealthProfile hProfile;	
	private String birthdate;
	
	/* Empty constructor -- sets default name, surname and HealthProfile and random ID and birthdate */
	public Person() 
	{
		this.firstname = "Nome";
		this.lastname = "Cognome";
		this.hProfile = new HealthProfile();
		this.birthdate = randomizeDate().toString();
		this.personId = Long.toString(Math.round(Math.floor(Math.random()*9998))+1);
	}
	
	/* Full constructor -- everything is given */
	public Person(String personId, String firstname, String lastname, String birthdate, HealthProfile hProfile) 
	{	
		String[] parts = birthdate.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.hProfile = hProfile;
		this.birthdate = new DateTime(year, month, day, 0, 0).toString();
		this.personId = personId;
	}

	/* Constructor without HealthProfile - sets it to the default (by calling its empty constructor */
	public Person( String personId, String firstname, String lastname, String birthdate) 
	{
		String[] parts = birthdate.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = new DateTime(year, month, day, 0, 0).toString();
		this.personId = personId;
		this.hProfile = new HealthProfile();
	}

	/* --- Setters and getters --- */
	public String getFirstname()
	{
		return firstname;
	}
	
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}
	
	public String getLastname()
	{
		return lastname;
	}
	
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	
	public HealthProfile gethProfile()
	{
		return hProfile;
	}
	
	public void sethProfile(HealthProfile hProfile)
	{
		this.hProfile = hProfile;
	}
	
	public String getBirthdate()
	{
		return birthdate;
	}
	
	public void setBirthdate(String birthdate)
	{
		this.birthdate = birthdate;
	}
	
	public String getPersonId()
	{
		return personId;
	}
	
	public void setPersonId(String personId)
	{
		this.personId = personId;
	}
	
	/* Sets ID as an attribute */
	@XmlAttribute(name="id")
	private String personId;
	
	/* Randomizes a date and creates a DateTime object */
	private DateTime randomizeDate() 
	{
		int currentYear = Calendar.getInstance().get(Calendar.YEAR); 		
		int year = (int) Math.round(Math.random()*(currentYear-1950)+1950); 

		int month = (int) Math.round(Math.floor(Math.random()*11)+1);
		int[] months = new int[]{31,28,30,30,31,30,31,31,30,31,30,31};
		if ((currentYear % 4 == 0) && ((currentYear % 100 != 0) || (currentYear % 400 == 0))) 
		{
			months[1] = 29;
		}
		int day = (int) Math.round(Math.floor(Math.random()*(months[month-1]-1)+1));
		return new DateTime(year, month, day, 0, 0, 0);
	}
}

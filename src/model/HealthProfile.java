package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;

@XmlRootElement(name="healthprofile")
@XmlType(propOrder = {"lastupdate", "weight", "height", "BMI"})
@XmlAccessorType(XmlAccessType.FIELD)

public class HealthProfile 
{
	private double weight;
	private double height;
	private String lastupdate;
	
	/* Constructor - sets lastupdate to current time */
	public HealthProfile(double weight, double height) 
	{
		this.lastupdate = new DateTime().toString();
		this.weight = weight;
		this.height = height;
	}
	
	/* Empty constructor - sets lastupdate to current time and height and height to default values */
	public HealthProfile() 
	{
		this.lastupdate = new DateTime().toString();
		this.weight = 80;
		this.height = 1.80;
	}

	
	/* --- Setters and getters --- */
	public double getWeight() 
	{
		return weight;
	}
	public void setWeight(double weight) 
	{
		this.weight = weight;
		this.lastupdate = new DateTime().toString();	//updates lastupdate
	}
	public double getHeight() 
	{
		return height;
	}
	public void setHeight(double height)
	{
		this.height = height;
		this.lastupdate = new DateTime().toString();	//updates lastupdate
	}
	public String getLastupdate() 
	{
		return lastupdate;
	}
	
	@XmlElement(name="bmi")
	public double getBMI() 
	{
		return Math.round(this.weight/Math.pow(this.height, 2)*100.0)/100.0;	//Calculates the BMI
	}
	
}

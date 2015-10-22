import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XpathGetter 
{
	Document doc;
    XPath xpath;

    /* Loads the XML file */
    public void loadXML() throws ParserConfigurationException, SAXException, IOException 
    {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        doc = builder.parse("people.xml");

        getXPathObj();
    }

    /* Instantiate an XPath object */
    public XPath getXPathObj() 
    {
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }
    
    /* XPath returning the whole list of people IDs */
    public NodeList getAllIDs() throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person/@id");
    	NodeList ids = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return ids;
    }
    
    /* XPath returning the list of IDs of people having the required condition on weight */
    public NodeList getByWeight(double weight, char operator) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[healthprofile/weight"+ operator + weight +"]/@id");
    	NodeList ids = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return ids;
    }
    
    /* Gets the weight of a person given its ID */
    public double getWeight(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/healthprofile/weight/text()");
    	Node weight = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return Double.parseDouble(weight.getTextContent());
    }

    /* Gets the height of a person given its ID */
    public double getHeight(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/healthprofile/height/text()");
    	Node height = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return Double.parseDouble(height.getTextContent());
    }

    /* Gets the birthdate of a person given its ID */
    public String getBirthdate(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/birthdate/text()");
    	Node birthdate = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return birthdate.getTextContent();
    }

    /* Gets the birthdate of a person given its ID */
    public String getFirstname(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/firstname/text()");
    	Node firstname = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return firstname.getTextContent();
    }

    /* Gets the birthdate of a person given its ID */
    public String getLastname(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/lastname/text()");
    	Node lastname = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return lastname.getTextContent();
    }
    
    /* Gets the date of last update of a person's health profile given its ID */
    public String getLastupdate(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/healthprofile/lastupdate/text()");
    	Node lastupdate = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return lastupdate.getTextContent();
    }

    /* Gets the BMI of a person given its ID */
    public double getBMI(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/healthprofile/bmi/text()");
    	Node bmi = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return Double.parseDouble(bmi.getTextContent());
    }

  
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		XpathGetter hpr = new XpathGetter();
        hpr.loadXML();
		
		int argCount = args.length;
		
		if (argCount < 1 || argCount > 3) // 0 or more than 3 arguments --> error!
		{
			System.out.println("Arguments not valid");
		} 
		
		else
		{
			String method = args[0];
			if (argCount == 1)			// 1 argument --> listAll or error
			{
				if (method.equals("listAll"))
				{
					String id, firstname, lastname, birthdate;
					double weight, height, bmi;

					NodeList ids = hpr.getAllIDs();
					for(int i=0; i < ids.getLength(); i++)
					{
						/* Retrieves ID by ID */
						id = ids.item(i).getTextContent();
						
						/* Retrieves all the info of the particular person to be output */
						firstname = hpr.getFirstname(id);
						lastname = hpr.getLastname(id);
						birthdate = hpr.getBirthdate(id);
						weight = hpr.getWeight(id);
						height = hpr.getHeight(id);
						bmi = hpr.getBMI(id);
						
						/* Prints all the infos formatted */
						System.out.println("Person " + id +  ":");
			        	System.out.println("\tName: " + firstname);
			        	System.out.println("\tSurname: " + lastname);
			        	System.out.println("\tBirthdate: " + birthdate);
			        	System.out.println("\tHeight: " + height);
			        	System.out.println("\tWeight: " + weight);
			        	System.out.println("\tBMI: " + bmi);
			        	System.out.println();
					}
				}
				else
				{
					System.out.println("Arguments not valid");
				}
			}
			
			else if (argCount == 2)			// 2 arguments --> getHP or error
			{
				if (method.equals("getHP")) 
				{
					String id = args[1];
					if (id.length() != 4)	// Requires the ID to be 4-digit long
					{
						System.out.println("The ID has to be composed by 4 digits!");
					}
					else 
					{
						/* Prints the data */
						System.out.println(hpr.getFirstname(id) + " " + hpr.getLastname(id) +"'s Health Profile:");
				        System.out.println("\tHeight: " + hpr.getHeight(id));
				        System.out.println("\tWeight: " + hpr.getWeight(id));
				        System.out.println("\tBMI: " + hpr.getBMI(id));
				        System.out.println("Last updated on " + hpr.getLastupdate(id));
				        System.out.println();
					}
				}
				
				else 
				{
					System.out.println("Arguments not valid");
				}
			}
			
			else if (argCount == 3)			// 3 arguments --> getByWeight or error
			{
				if (method.equals("getByWeight")) 
				{
					double weight = Double.parseDouble(args[1]);		// Parses the string passed as argument to a double
					char operator = args[2].charAt(0);					// Parses the string passed as argument to a char
					NodeList ids = hpr.getByWeight(weight, operator);	// Gets the list
					
					String ID, nome, cognome, lastupdate;
					double peso, altezza, bMi;
					
					for(int i=0; i < ids.getLength(); i++)		// Prints every person in the list
					{	
						/* Retrieves ID by ID */
						ID = ids.item(i).getTextContent();
						
						/* Retrieves all the info of the particular person to be output */
						nome = hpr.getFirstname(ID);
						cognome = hpr.getLastname(ID);
						peso = hpr.getWeight(ID);
						
						/* Prints the infos */
			        	System.out.println(nome + " " + cognome +" weights " + peso + " kg");
					}
				}
				else 
				{
					System.out.println("Arguments not valid");
				}
			}
		}
	}
}
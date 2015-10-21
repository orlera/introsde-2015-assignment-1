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
    
    /* XPath returning the whole list of people */
    public NodeList getAllPeople() throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person");
    	NodeList people = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return people;
    }
    
    /* XPath returning the list of people having the required condition on weight */
    public NodeList getByWeight(double weight, char operator) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[healthprofile/weight"+ operator + weight +"]");
    	NodeList people = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return people;
    }
    
    /* Gets the weight of a person given its first name and last name */
    public Node getWeight(String firstName, String lastName) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("/people/person[firstname='" + firstName + "'][lastname='" + lastName + "']/healthprofile/weight/text()");
    	Node weight = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return weight;
    }

    /* Gets the height of a person given its first name and last name */
    public Node getHeight(String firstName, String lastName) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("/people/person[firstname='" + firstName + "'][lastname='" + lastName + "']/healthprofile/height/text()");
    	Node height = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return height;
    }
    
    /* Gets the health profile of a person given its ID */
    public Node getHP(String id) throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("//person[@id='" + id + "']/healthprofile");
    	Node hp = (Node) expr.evaluate(doc, XPathConstants.NODE);
    	return hp;
    }

  
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		XpathGetter hpr = new XpathGetter();
        hpr.loadXML();
		
		int argCount = args.length;
		
		if (argCount < 1 || argCount > 3) 
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
					NodeList people = hpr.getAllPeople();
					for(int i=0; i < people.getLength(); i++)
					{
						System.out.println(people.item(i).getTextContent());
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
						Node hp = hpr.getHP(id);

						if (hp == null)		// The ID didn't match with anybody in the XML
						{
							System.out.println("ID not valid!");
						}
						else
						{
							System.out.println(hp.getTextContent());
						}
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
					double weight = Double.parseDouble(args[1]);	// Parses the string passed as argument to a double
					char operator = args[2].charAt(0);				// Parses the string passed as argument to a char
					NodeList people = hpr.getByWeight(weight, operator);	// Gets the list

					for(int i=0; i < people.getLength(); i++)		// Prints every person in the list
					{
						System.out.println(people.item(i).getTextContent());
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
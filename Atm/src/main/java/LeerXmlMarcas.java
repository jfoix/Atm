import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class LeerXmlMarcas {

	public static void main(String[] args) {
		try{
		File fXmlFile = new File("marcas_autos_xml.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		 
		NodeList nList = doc.getElementsByTagName("option");
	 
		for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			Node nNode = nList.item(temp);
	 
			System.out.println("Insert into marca_vehiculo (descripcion, estado) values ('" + nNode.getTextContent() + "',1);");
		}
		
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}

package register;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.ArrayList;

// https://mkyong.com/java/how-to-create-xml-file-in-java-dom/

public class XmlWriter {
    /**
     * Task 0: Update the project:
     *          - From menu: Git / "Update Project...", OR
     *          - In right upper corner: blue arrow, OR
     *          - Keyboard shortcut: Ctrl+T
     * In "Update the project" popup click on Ok button.
     */

    /**
     * Task 1:
     * Create a saveUsersToXml(users, filepath) method to store
     * the list of User objects in a given XML file.
     * Create a main method.
     * In main method:
     *       - create a list of User objects
     *       - call saveUsersToXml method with the list and
     *              "src/main/resources/users.xml" as parameters
     * Hint: Use the readUsersFromXml method from previous class to
     *       create list of User objects.
     * Hint: Use TransformerFactory.newInstance().newTransformer()
     *       method to write XML files.
     * Hint: Create method to add elements to the hierarchy.
     */

    public static void main(String[] args) {
        String filepath = "src/main/resources/users.xml";
        ArrayList<User> users = XmlReader.readUsersFromXml(filepath);
        System.out.println(users);
        // menu system of the application with create, read, update and delete options
        users.add(new User("Kate", 2000, "Second Street", EyeColor.AMBER));
        saveUsersToXml(users, filepath);
    }

    public static void saveUsersToXml(ArrayList<User> users, String filepath) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element rootElement = document.createElement("users");
            document.appendChild(rootElement);

            for (User user : users) {
                Element userElement = document.createElement("user");
                rootElement.appendChild(userElement);
                createChildElement(document, userElement, "name", user.getName());
                createChildElement(document, userElement, "birthYear", String.valueOf(user.getBirthYear()));
                createChildElement(document, userElement, "address", user.getAddress());
                createChildElement(document, userElement, "eyeColor", user.getEyeColor().toString());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(filepath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createChildElement(Document document, Element parent, String tagName, String value) {
        Element element = document.createElement(tagName);
        element.setTextContent(value);
        parent.appendChild(element);
    }

    /**
     * Task 2:
     * Create a console application in main method. When the application
     * starts read data of users from an XML file and build up a list of
     * User objects. Create a console menu with (1) list users and (0)
     * exit options. When your user enters 1, your application should
     * print all data of users. When your user enters 0, your application
     * should terminate.
     * Hint: Use the readUsersFromXml method from previous class to
     *       create list of User objects.
     */

    /**
     * Task 3:
     * Add new option to your applications: (2) add new user.
     * When your user chooses this option, your application
     * should ask and validate all data of a new user one by
     * one. Before your application terminates, you should
     * update the list of users in the XML file.
     * Hint: Use the saveUsersToXml method from Task 1.
     */

    /**
     * Task 4:
     * Add new option to your applications: (3) modify user.
     * When your user chooses this option, your application
     * should:
     *      - define the user with her/his name
     *      - ask and validate all data of the user one by one
     * Hint: Define an id attribute to User objects, so you can
     *       also support name modifications. Do not forget to
     *       add is to listing and XML file so your user will
     *       know it.
     */

    /**
     * Task 5:
     * Add new option to your applications: (4) delete user.
     * When your user chooses this option, your application
     * should define the user with her/his name and remove it.
     */

    /**
     * Homework:
     * Create similar register applications to Song, PublicVehicle,
     * Note, Goods, Fruit, Employee and Book objects.
     * Note: There is no test to this task.
     */
}

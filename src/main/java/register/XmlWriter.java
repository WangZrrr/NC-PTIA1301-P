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
import java.util.InputMismatchException;
import java.util.Scanner;

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

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String filepath = "src/main/resources/users.xml";
        ArrayList<User> users = XmlReader.readUsersFromXml(filepath);
        //System.out.println(users);
        // menu system of the application with create, read, update and delete options
        int choice = -1;
        while (choice != 0) {
            System.out.println("1 - List users\r\n2 - Add new user\r\n3 - Modify user\r\n" +
                                "4 - Delete user\r\n0 - Exit");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 0 || 4 < choice) {
                    System.out.println("Not valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Not valid option.");
                scanner.nextLine();
            }
            switch (choice) {
                case 1 -> System.out.println(users);
                case 2 -> addNewUser(users);
                case 3 -> modifyUser(users);
                case 4 -> deleteUser(users);
            }
        }
        //users.add(new User("Kate", 2000, "Second Street", EyeColor.AMBER));
        saveUsersToXml(users, filepath);
    }

    private static void addNewUser(ArrayList<User> users) {
        System.out.print("Enter name of new user: ");
        String name = scanner.nextLine();
        int birthYear = readBirthYear();
        System.out.print("Enter address of new user: ");
        String address = scanner.nextLine();
        EyeColor eyeColor = readEyeColor();
        users.add(new User(name, birthYear, address, eyeColor));
    }

    private static void modifyUser(ArrayList<User> users) {
        User user = findUserIn(users);
        int birthYear = readBirthYear();
        System.out.print("Enter address of user: ");
        String address = scanner.nextLine();
        EyeColor eyeColor = readEyeColor();
        users.set(users.indexOf(user),
                  new User(user.getName(), birthYear, address, eyeColor));
    }

    private static void deleteUser(ArrayList<User> users) {
        users.remove(findUserIn(users));
    }

    private static User findUserIn(ArrayList<User> users) {
        User user = new User();
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter name of user: ");
            name = scanner.nextLine();
            for (User userElement : users) {
                if (userElement.getName().equals(name)) {
                    return userElement;
                }
            }
            name = "";
        }
        return user;
    }

    private static int readBirthYear() {
        int birthYear = 0;
        while (birthYear == 0) {
            try {
                System.out.print("Enter birth year of user: ");
                birthYear = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("This is not a valid birth year. Please enter an integer.");
                scanner.nextLine();
            }
        }
        return birthYear;
    }

    private static EyeColor readEyeColor() {
        EyeColor eyeColor = EyeColor.BROWN;
        String rawInput = "";
        while (rawInput.isEmpty()) {
            try {
                System.out.println("Enter eye color of user: ");
                rawInput = scanner.nextLine();
                eyeColor = EyeColor.valueOf(rawInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Not valid eye color.");
                rawInput = "";
            }
        }
        return eyeColor;
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

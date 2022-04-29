package register;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class NewRegisterMain {
    /**
     * Task 0: Update the project:
     *          - From menu: Git / "Update Project...", OR
     *          - In right upper corner: blue arrow, OR
     *          - Keyboard shortcut: Ctrl+T
     * In "Update the project" popup click on Ok button.
     */

    /**
     * Task 1: Create a main method, where:
     *          - Create a new list of User objects.
     *          - Add 3 users to the list:
     *              - Tony Stark, 1970, 10880 Malibu Point, Malibu
     *              - Stephen Strange, 1930, 177A Bleecker Street, New York City
     *              - Steve Rogers, 1918, 569 Leaman Place, Brooklyn
     *          - Print the list
     *          - Remove Tony Stark from the list
     *          - Print the list
     *          - Add to the 2nd position a new user:
     *              - Peter Parker, 2001, 20 Ingram Street, New York
     *          - Print the list
     */
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        User tony = new User("Tony Stark", 1970, "10880 Malibu Point, Malibu");
        users.add(tony);
        users.add(new User("Stephen Strange", 1930, "177A Bleecker Street, New York City"));
        users.add(new User("Steve Rogers", 1918, "569 Leaman Place, Brooklyn"));
        System.out.println(users);
        //users.remove(0);
        //users.remove(tony);
        users.remove(users.get(0));
        System.out.println(users);
        users.add(1, new User("Peter Parker", 2001, "20 Ingram Street, New York"));
        System.out.println(users);
    }

    /**
     * Task 2: Create a printList method to print the name of users in the list:
     * 1. Tony Stark
     * 2. Stephen Strange
     * 3. Steve Rogers
     * Use this method in main to print the list.
     */

    /**
     * //TODO: next time
     * Task 3: Create a writeUsersIntoFile method to save data of users into a file.
     * The method should get the list of users and the filepath.
     * Add a header to the file: Name;BirthYear;Address.
     * After the header write the name, year of birth and address data of users.
     * Each data of the same user should be separated by semicolon (';') character.
     * Data of users should be listed in separated rows.
     * Call the method with previously created list and "src/main/resources/users.csv" string as filepath.
     * Hint: Use BufferedWriter or FileOutputStream to write a file.
     * Hint: You should convert Strings to bytearrays with getBytes() method of String class.
     * Hint: Do not forget to add new line characters.
     * Hint: Create new method for repeating code snippets.
     */

    /**
     * Task 4: Create a readUsersFromFile method to read user's data from the file.
     * The method should return with the list of users.
     * Hint: Use BufferedReader or FileInputStream to read from a file.
     * Hint: Take care about the header row.
     */
    public static ArrayList<User> readUsersFromFile(String filepath) {
        ArrayList<User> users = new ArrayList<>();
        try {
            /*
            FileInputStream is = new FileInputStream(filepath);
            while (is.available() > 0) {
                is.read();
                ...
            }
            is.close();
             */
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            reader.readLine();
            while (reader.ready()) {
                String rowData = reader.readLine();
                String[] data = rowData.split(";");
                users.add(new User(data[0], Integer.parseInt(data[1]), data[2]));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Task 5: Create similar reader and writer methods to read and write Song objects.
     */

    /**
     * Task 6: Create similar reader and writer methods to read and write Note objects.
     */

    /**
     * Task 7: Create similar reader and writer methods to read and write Book objects.
     */
}

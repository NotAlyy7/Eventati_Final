package oop.oop5;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;
import java.util.List;


enum Gender{ Male, Female, Engineer}

public abstract class User {
    private  String  username;
    private  String password;
    private final DateOfBirth dateOfBirth;


    public User(String username, String password, DateOfBirth dateOfBirth) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }


    public static void login() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Login ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            for (Admin admin : Database.getAdmins()) {
                if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                    System.out.println("Admin login successful!");
                    Admin.menu();
                }
            }

            for (Attendee attendee : Database.getAttendees()) {
                if (attendee.getUsername().equals(username) && attendee.getPassword().equals(password)) {
                    System.out.println("Attendee login successful!");
                    Attendee.menu();
                }
            }
            for (Organizer organizer : Database.getOrganizers()) {
                if (organizer.getUsername().equals(username) && organizer.getPassword().equals(password)) {
                    System.out.println("Organizer login successful!");
                    Organizer.menu();
                }
            }
            System.out.println("Username or Password might be incorrect");
            int c;
            while(true) {
                System.out.println("1. Try again");
                System.out.println("2. Back to main menu");
                System.out.print("Select option: ");

                try {
                    c = scanner.nextInt();
                    scanner.nextLine();
                    if (c == 1){break;}
                    if (c == 2){User.mainMenu();}
                    else System.out.println("Invalid selection - please choose 1-2");

                } catch(InputMismatchException e) {
                    System.out.println("Invalid input - please enter a number");
                    scanner.nextLine();
                }
            }
        }

    }

    public static void registerMenu(){
        System.out.println("Choose your user type");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(choice != 5) {
            System.out.println("1. Admin");
            System.out.println("2. Attendee");
            System.out.println("3. Organizer");
            System.out.println("4. Back to main menu");
            System.out.print("Select option: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1 -> Admin.register();
                    case 2 -> Attendee.register();
                    case 3 -> Organizer.register();
                    case 4 -> User.mainMenu();
                    default -> System.out.println("Invalid selection - please choose 1-4");
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid input - please enter a number");
                scanner.nextLine();
            }
        }

    }

    public  String getUsername(){
        return username;
    }

    public  String getPassword(){
        return password;
    }
    public  void setPassword(String password){
        this.password = password;
    }
    public  void setUsername(String username){
        this.username = username;
    }

    public static void mainMenu(){
        System.out.println("Event Management System");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(choice != 5) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1 -> User.login();
                    case 2 -> User.registerMenu();
                    case 3 -> System.exit(0);
                    default -> System.out.println("Invalid selection - please choose 1-3");
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid input - please enter a number");
                scanner.nextLine();
            }
        }
    }
}


class TimeSlot {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(TimeSlot other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }

    @Override
    public String toString() {
        return start.toLocalDate() + " " + start.toLocalTime() + "-" + end.toLocalTime();
    }
}

class DateOfBirth {
    int day;
    int month;
    int year;

    DateOfBirth (int d, int m, int y){
        day = d;
        month = m;
        year = y;
    }

    public void getDate(){
        System.out.print(day + "/" + month + "/" + year);
    }
}

class Database {
    private static List<Admin> admins = new ArrayList<>();
    private static List<Organizer> organizers = new ArrayList<>();
    private static List<Attendee> attendees = new ArrayList<>();
    private static List<Event> events = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();
    private static Database instance = new Database();
    public static Database getInstance(){
        return instance;
    }
private Database(){
    this.admins = new ArrayList<>();
    this.organizers = new ArrayList<>();
    this.attendees = new ArrayList<>();
    this.rooms = new ArrayList<>();
    this.categories = new ArrayList<>();
    this.events = new ArrayList<>();
    initializeDummyData();

}
    public  void initializeDummyData() {
        List<String> list = new ArrayList<>();
        list.add("Wifi");
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Event testcase = new Event(2,"Test","play",today,now,50,new Category("a","a"),new Room("5",2,list));
        admins.add(new Admin("Ali", "ali1234", new DateOfBirth(2, 5, 2006), "Admin", 12));
        rooms.add(new Room("R1", 50, List.of("Projector", "Whiteboard", "Projector", "Chairs and Tables", "Soundproofing")));
        rooms.add(new Room("R2", 100, List.of("Stage", "Sound System", "Soundproofing", "Musical Instruments", "Sound Isolation")));
        rooms.add(new Room("R3", 30, List.of("Natural Light", "Screens and Projectors", "coffeemaker", "Soundproofing")));
        rooms.add(new Room("R4", 100, List.of("Fairy lights", "chandeliers", "Chairs and Tables", "Dance Floor", "Buffet")));
        attendees.add(new Attendee("Hosny", "hosny1234", new DateOfBirth(3, 4, 2006), "Cairo street1", 1200, "5%", Gender.Engineer));
        organizers.add(new Organizer("Maroo","maroo1234",new DateOfBirth(13, 8, 2005)));
        Category c = new Category("C1","s");
    }

    public static List<Attendee> getAttendees() {
        return attendees;
    }

    public static List<Room> getRooms() {
        return rooms;
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static List<Admin> getAdmins() {
        return admins;
    }

    public static List<Organizer> getOrganizers() {
        return organizers;
    }
    public static List<Event> getEvents() {return events;}

    public static boolean isUsernameTaken(String username) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {
                return true;
            }
        }
        for (Attendee attendee : attendees) {
            if (attendee.getUsername().equals(username)) {
                return true;
            }
        }
        for (Organizer organizer : organizers) {
            if (organizer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean updateAttendee(Attendee updatedAttendee) {
        for (int i = 0; i < attendees.size(); i++) {
            if (attendees.get(i).getUsername().equals(updatedAttendee.getUsername())) {
                attendees.set(i, updatedAttendee);
                return true;
            }
        }
        return false;
    }
}

class Admin extends User {
    String role;
    double workingHours;

    public Admin(String u, String p, DateOfBirth d, String r, double w) {
        super(u, p, d);
        role = r;
        workingHours = w;
    }

    public static void register() {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        int day = 0 ;
        int month = 0;
        int year = 0;
        String role;
        double workingHours = 0;

        System.out.print("Username: ");
        username = scanner.nextLine();
        while (Database.isUsernameTaken(username)) {
            System.out.println("Username already taken");
            System.out.print("Username: ");
            username = scanner.nextLine();
        }

        System.out.print("Password: ");
        password = scanner.nextLine();
        while (password == null || password.length() < 8) {
            System.out.println("Password must be 8 or more characters");
            System.out.print("Password: ");
            password = scanner.nextLine();
        }
        while (workingHours <= 0) {
            try {
                System.out.print("Working hours: ");
                workingHours = scanner.nextInt();
                if (workingHours <= 0) {
                    System.out.println("Please enter positive working hours");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        System.out.println("Birthdate");
        while (day <= 0 || day > 31) {
            try {
                System.out.print("Day: ");
                day = scanner.nextInt();
                if (day <= 0 || day > 31) {
                    System.out.println("Please enter a valid day");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        while (month <= 0 || month > 12) {
            try {
                System.out.print("Month: ");
                month = scanner.nextInt();
                if (month <= 0 || month > 12) {
                    System.out.println("Please enter a valid month");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        while (year <= 0 || year > 2025) {
            try {
                System.out.print("Year: ");
                year = scanner.nextInt();
                if (year <= 0 || year > 2025) {
                    System.out.println("Please enter a valid year");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        DateOfBirth dob = new DateOfBirth(day,month,year);

        Admin newAdmin = new Admin(username, password, dob, "Admin", workingHours);
        Database.getAdmins().add(newAdmin);
        System.out.println("Admin registered successfully \n");
        Admin.menu();
    }

    public static void menu() {
        System.out.println("Admin Dashboard");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(true) {
            System.out.println("\nMAIN MENU:");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. View Attendees");
            System.out.println("4. View Categories");
            System.out.println("5. Manage Categories");
            System.out.println("6. Log out");
            System.out.print("Select option: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1 -> addRoom();
                    case 2 -> viewRooms();
                    case 3 -> viewAttendee();
                    case 4 -> viewCategories();
                    case 5 -> manageCategory();
                    case 6 -> User.mainMenu();
                    default -> System.out.println("Invalid selection - please choose 1-6");
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid input - please enter a number");
                scanner.nextLine();
            }
        }
    }

    public static void viewAttendee(){
        Database.getAttendees().forEach(attendee -> System.out.print(attendee.getUsername() + " - " + attendee.getWallet().getBalance() + "\n"));
    }
    public static void viewRooms(){
        Database.getRooms().forEach(room -> System.out.print(room.getRoomId() + " - " + room.getCapacity() + " - " + room.getBookedSlots() + " - " + room.getAmenities() + "\n"));
    }
    public static void viewCategories(){
        Database.getCategories().forEach(category -> System.out.print(category.getCategoryID() +"\n"));
    }
    public static void addRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nAdding New Room");

        String id = "R" + 0 + (Database.getRooms().size()+1);

        int capacity = 0;
        while (capacity <= 0) {
            try {
                System.out.print("Enter Room Capacity: ");
                capacity = scanner.nextInt();
                scanner.nextLine();
                if (capacity <= 0) {
                    System.out.println("Capacity must be positive");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }


        System.out.println("Enter Amenities (comma separated): ");
        List<String> amenities = Arrays.asList(scanner.nextLine().split("\\s*,\\s*"));

        Room newRoom = new Room(id, capacity, amenities);
        Database.getRooms().add(newRoom);

        System.out.println("Room added successfully!");
        System.out.println("New Room Details:");
        System.out.println("ID: " + newRoom.getRoomId());
        System.out.println("Capacity: " + newRoom.getCapacity());
        System.out.println("Amenities: " + newRoom.getAmenities());
    }

    public static void manageCategory(){
        System.out.println("Manage Categories");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(true) {
            System.out.println("1. Create Category");
            System.out.println("2. Search for Category by name");
            System.out.println("3. Update Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Return Back");
            System.out.print("Select option: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1 -> createCategory();
                    case 2 -> viewRooms();
                    case 3 -> deleteCategory();
                    case 4 -> menu();
                    default -> System.out.println("Invalid selection - please choose 1-5");
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid input - please enter a number");
                scanner.nextLine();
            }
        }
    }
    public static void createCategory(){
        String id = "C" + 0 + Database.getCategories().size()+1;
        String name;
        String description;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Category name: ");
        name = scanner.nextLine();
        System.out.println("Category decrsiption: ");
        description = scanner.nextLine();

        Database.getCategories().add(new Category(id,name));
    }
    public static void deleteCategory(){
        System.out.println("Enter the ID of the category you want to delete");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        boolean removed = Database.getCategories().removeIf(category ->
                category.getCategoryID().equals(id));

        if (removed){System.out.println("Category remove");}
        else System.out.println("No category found with that ID \n");
        Admin.manageCategory();
    }


}

class Attendee extends User{
    private  String address;
    private  double balance;
    private  String interests;
    private Gender gender;
    private   Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }
    public  String getAddress() {
        return address;
    }
    public  void setAddress(String address) {

        this.address = address;
    }


    public  String getUsername() {
        return super.getUsername();
    }

    public  String getPassword() {
        return super.getPassword();
    }
    public  String getInterests() {

        return interests;
    }
    public  void setInterests(String s) {

        interests = s;
    }

    public void setGender(Gender gender) {

        this.gender = gender;
    }
    public void setUsername(TextField in, Database db, TextField out) {
        String username = in.getText();
        StringBuilder outputArea = new StringBuilder();
        if (Database.isUsernameTaken(username)) {
            outputArea.append("Username already taken");
            in.clear();
            in.setText(outputArea.toString());
            outputArea.setLength(0);

        }else if (!Database.isUsernameTaken(username)) {
super.setUsername(username);
    }}
    public  double getbalance(){
        return balance;
    }
    public Gender getGender(){
        Gender g = gender;
        return g;
    }
    public void setpassword(TextField in, Database db, TextField out){
        String password = in.getText();
        StringBuilder outputArea = new StringBuilder();
       if (password == null || password.length() < 8) {
            outputArea.append("Password must be 8 or more characters");
            in.clear();
            in.setText(outputArea.toString());
            outputArea.setLength(0);
           System.out.println("Password must be 8 or more characters");
        }else if (password.length() > 8 && password != null) {
           System.out.println("password is valid");
        super.setPassword(password);
    }}
    Attendee(String u, String p, DateOfBirth d, String address, double balance, String interests, Gender gender) {
        super(u, p ,d);
        this.address = address;
        this.balance = balance;
        this.interests = interests;
        this.gender = gender;
        this.wallet = new Wallet(balance);
    }


    public static void register() {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        int day = 0 ;
        int month = 0;
        int year = 0;
        double money = 0;
        String addr = null;
        String intr = null;
        System.out.print("Username: ");
        username = scanner.nextLine();
        while (Database.isUsernameTaken(username)) {
            System.out.println("Username already taken");
            System.out.print("Username: ");
            username = scanner.nextLine();
        }

        System.out.print("Password: ");
        password = scanner.nextLine();
        while (password == null || password.length() < 8) {
            System.out.println("Password must be 8 or more characters");
            System.out.print("Password: ");
            password = scanner.nextLine();
        }
        while (money <= 0) {
            try {
                System.out.print("Balance: ");
                money = scanner.nextInt();
                if (money <= 0) {
                    System.out.println("Please enter positive Balance");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        System.out.println("Birthdate");
        while (day <= 0 || day > 31) {
            try {
                System.out.print("Day: ");
                day = scanner.nextInt();
                if (day <= 0 || day > 31) {
                    System.out.println("Please enter a valid day");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        while (month <= 0 || month > 12) {
            try {
                System.out.print("Month: ");
                month = scanner.nextInt();
                if (month <= 0 || month > 12) {
                    System.out.println("Please enter a valid month");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        while (year <= 0 || year > 2025) {
            try {
                System.out.print("Year: ");
                year = scanner.nextInt();
                if (year <= 0 || year > 2025) {
                    System.out.println("Please enter a valid year");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        DateOfBirth dob = new DateOfBirth(day,month,year);
        System.out.println("Enter your adrress: ");
while (addr == null || addr.length() < 1) {

    addr = scanner.nextLine();
}
        System.out.println("Enter your Interests: ");
        while (intr == null || intr.isBlank()) {
            intr = scanner.nextLine();
            if (intr.isBlank()) {
                System.out.println("Please enter a valid input for interests:");
            }
        }

        System.out.println("Choose your Gender:");
        System.out.println("1 - Engineer");
        System.out.println("2 - Male");
        System.out.println("3 - Female");

        Gender g = null;
        while (g == null) {
            try {
                System.out.print("Enter a valid number (1, 2, or 3): ");
                int j = Integer.parseInt(scanner.nextLine());

                switch (j) {
                    case 1 -> g = Gender.Engineer;
                    case 2 -> g = Gender.Male;
                    case 3 -> g = Gender.Female;
                    default -> System.out.println("Invalid selection. Please choose 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }
        Attendee newAttendee = new Attendee(username, password, dob, addr, money, intr, g);
        Database.getAttendees().add(newAttendee);
        System.out.println("Attendee registered successfully \n");
        Attendee.menu();
    }
    public boolean updateAttendee(String newUsername, String newPassword, String newAddress, String newInterests) {
        if (!newUsername.equals(this.getUsername()) && Database.isUsernameTaken(newUsername)) {
            return false; // Username already taken
        }
        if (newPassword != null && newPassword.length() < 8) {
            return false; // Password too short
        }

        if (newUsername != null && !newUsername.isEmpty()) {
            this.setUsername(newUsername);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            this.setPassword(newPassword);
        }
        if (newAddress != null && !newAddress.isEmpty()) {
            this.setAddress(newAddress);
        }
        if (newInterests != null && !newInterests.isEmpty()) {
            this.setInterests(newInterests);
        }

        return true;
    }


    public static void menu() {

    }
    public String bookEvent(String input) {
        StringBuilder outputArea2 = new StringBuilder();
        int selectedEventIndex;

        try {
            // Parse and convert to 0-based index
            selectedEventIndex = Integer.parseInt(input.trim()) - 1;
        } catch (NumberFormatException e) {
            outputArea2.append("Please enter a valid number.\n");
            return outputArea2.toString();
        }

        // Validate index range
        if (selectedEventIndex < 0 || selectedEventIndex >= Database.getEvents().size()) {
            outputArea2.append("Please enter a number between 1 and " + Database.getEvents().size() + "\n");
            return outputArea2.toString();
        }
        Database db = Database.getInstance();
        Event chosenEvent = db.getEvents().get(selectedEventIndex);

        if (wallet.getBalance() >= chosenEvent.getTicketPrice()) {
            wallet.deducfunds(chosenEvent.getTicketPrice());
            Organizer.setorganizerwallet(chosenEvent.getTicketPrice());
            outputArea2.append("Booked: " + chosenEvent.getName() + "\n");
            outputArea2.append("New balance: $" + wallet.getBalance() + "\n");
        } else {
            outputArea2.append("Insufficient funds for " + chosenEvent.getName() + "\n");
            outputArea2.append("Needed: $" + chosenEvent.getTicketPrice() +
                    " | Have: $" + wallet.getBalance() + "\n");
        }

        return outputArea2.toString();
    }

    public static String viewEvents(Database db) {
        StringBuilder eventsText = new StringBuilder();

        if (db.getEvents().isEmpty()) {
            return "No events available.";
        }

        eventsText.append("=== AVAILABLE EVENTS ===\n\n");

        for (int i = 0; i < db.getEvents().size(); i++) {
            Event event = db.getEvents().get(i);
            eventsText.append(String.format(
                    "%d. %s\nDate: %s\nTime: %s\nPrice: $%.2f\nCategory: %s\nRoom: %s\n\n",
                    i + 1,
                    event.getName(),
                    event.getDate(),
                    event.getTime(),
                    event.getTicketPrice(),
                    event.getCategory().getCategoryID(),
                    event.getRoom().getRoomId()
            ));
        }

        return eventsText.toString();
    }
}



class Organizer extends User {
    public static Wallet walletorganizer = new Wallet(0);
    private static List <Organizer> registeredOrganizers = new ArrayList<>();
    public static void setorganizerwallet(double funds){
        walletorganizer.addfunds(funds);
    }

    public static Wallet getWalletorganizer() {
        return walletorganizer;
    }
    public Organizer (String username, String password, DateOfBirth dateOfBirth)
    {
        super (username, password, dateOfBirth);
        walletorganizer.setWalletId(0);
    }

    public static void register() {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        int day = 0 ;
        int month = 0;
        int year = 0;
        String role;
        double workingHours = 0;

        System.out.print("Username: ");
        username = scanner.nextLine();
        while (Database.isUsernameTaken(username)) {
            System.out.println("Username already taken");
            System.out.print("Username: ");
            username = scanner.nextLine();
        }

        System.out.print("Password: ");
        password = scanner.nextLine();
        while (password == null || password.length() < 8) {
            System.out.println("Password must be 8 or more characters");
            System.out.print("Password: ");
            password = scanner.nextLine();
        }

        System.out.println("Birthdate");
        while (day <= 0 || day > 31) {
            try {
                System.out.print("Day: ");
                day = scanner.nextInt();
                if (day <= 0 || day > 31) {
                    System.out.println("Please enter a valid day");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        while (month <= 0 || month > 12) {
            try {
                System.out.print("Month: ");
                month = scanner.nextInt();
                if (month <= 0 || month > 12) {
                    System.out.println("Please enter a valid month");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        while (year <= 0 || year > 2025) {
            try {
                System.out.print("Year: ");
                year = scanner.nextInt();
                if (year <= 0 || year > 2025) {
                    System.out.println("Please enter a valid year");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
        DateOfBirth dob = new DateOfBirth(day,month,year);

        Organizer newOrganizer = new Organizer(username, password, dob);
        Database.getOrganizers().add(newOrganizer);
        System.out.println("Organizer registered successfully \n");
        Organizer.menu();
    }

    public static void menu()
    {
        System.out.println("Organizer Dashboard");
        System.out.println("1. CREATE EVENT");
        System.out.println("2. UPDATE EVENT");
        System.out.println("3. CANCEL EVENT");
        System.out.println("4. VIEW AVAILABLE ROOMS");
        System.out.println("5. RENT ROOM");

    }

    public static void createEvent(int eventid, String name, String description, LocalDate date, LocalTime time, double ticketprice, Category category, Room room)
    {
        try
        {
            if (eventid == 0 || name == null || description == null || date == null || time == null || category == null || room == null) {
                System.out.println("Failed to create event: One or more fields are null");
                return;
            }
            Event event = new Event(eventid, name, description, date, time, ticketprice, category, room);
            System.out.println("Event is Successfully Created");
        }
        catch (Exception e)
        {
            System.out.println("Event Creation is Unsuccesful. Please Check Input");
        }
    }
    public static void updateEvent(int id, String name, String description, LocalDate date, LocalTime time, double price, Category category, Room room, Database db)
    {
        try
        {
            for (Event event : db.getEvents())
            {
                if (id == event.getEventID())
                {
                    event.setName(name);
                    event.setDescription(description);
                    event.setDate(date);
                    event.setTime(time);
                    event.setTicketPrice(price);
                    event.setCategory(category);
                    event.setRoom(room);
                    System.out.println("Event Successfully Updated");
                    return;
                }
            }
            System.out.println("Event with ID " + id + " is not found");
        }
        catch (Exception e)
        {
            System.out.println("There is an Error while updating the Event. Please Check Input");
        }
    }


    public void cancelEvent(int eventid)
    {
        Database db = Database.getInstance();
        try
        {
            for (int i = 0; i < db.getEvents().size(); i++)
            {
                if (db.getEvents().get(i).getEventID() == eventid)
                {
                    db.getEvents().remove(i);
                    System.out.println("Event with ID " + eventid + " is Cancelled");
                    return;
                }
            }
            System.out.println("Event with ID " + eventid + " is not found");
        }
        catch (Exception e)
        {
            System.out.println("Event Cancellation is Unsuccesfull. Please Check Input");
        }
    }

}

    class Room {
        private final String roomId;
        private final int capacity;
        private final List<String> amenities;
        private final List<TimeSlot> bookedSlots;
Database db = Database.getInstance();
        public Room(String roomId, int capacity, List<String> amenities) {
            this.roomId = roomId;
            this.capacity = capacity;
            this.amenities = new ArrayList<>(amenities);
            this.bookedSlots = new ArrayList<>();
        }

        public boolean hasAmenity(String amenity) {
            return amenities.stream()
                    .anyMatch(a -> a.equalsIgnoreCase(amenity.trim()));
        }

        public void addAmenity(String amenity) {
            String trimmed = amenity.trim();
            if (!hasAmenity(trimmed)) {
                amenities.add(trimmed);
            }
        }

        public boolean isAvailable(TimeSlot slot) {
            return bookedSlots.stream()
                    .noneMatch(booked -> booked.overlaps(slot));
        }

        public boolean bookRoom(TimeSlot slot) {
            if (isAvailable(slot)) {
                bookedSlots.add(slot);
                return true;
            }
            return false;
        }

        public String getRoomId() { return roomId; }
        public int getCapacity() { return capacity; }
        public List<String> getAmenities() { return new ArrayList<>(amenities); }
        public List<TimeSlot> getBookedSlots() { return new ArrayList<>(bookedSlots); }

        @Override
        public String toString() {
            return "Room " + roomId + " (" + capacity + " persons)";
        }



        public static void menu(Database db) {
            System.out.println("=== SMART EVENT MANAGEMENT SYSTEM ===");
            Scanner scanner = new Scanner(System.in);
            int choice = 0;

            while(choice != 5) {
                System.out.println("\nMAIN MENU:");
                System.out.println("1. View Room Details");
                System.out.println("2. Check Availability");
                System.out.println("3. Schedule Event");
                System.out.println("4. Add Facility");
                System.out.println("5. Exit");
                System.out.print("Select option: ");

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch(choice) {
                        case 1 -> handleViewDetails(db.getRooms(), scanner);
                        case 2 -> handleCheckAvailability(db.getRooms(), scanner);
                        case 3 -> handleScheduleEvent(db.getRooms(), scanner);
                        case 4 -> handleAddFacility(db.getRooms(), scanner);
                        case 5 -> System.out.println("Exiting system...");
                        default -> System.out.println("Invalid selection - please choose 1-5");
                    }
                } catch(InputMismatchException e) {
                    System.out.println("Invalid input - please enter a number");
                    scanner.nextLine();
                }
            }
            scanner.close();
        }

        private static void handleViewDetails(List<Room> rooms, Scanner scanner) {
            System.out.println("\nAvailable Rooms:");
            rooms.forEach(room -> System.out.println(" - " + room.getRoomId()));

            System.out.print("\nEnter Room ID to view details: ");
            String roomId = scanner.nextLine().toUpperCase();

            Room selected = findRoomById(rooms, roomId);
            if(selected != null) {
                System.out.println("\nRoom Details:");
                System.out.println("ID: " + selected.getRoomId());
                System.out.println("Capacity: " + selected.getCapacity());
                System.out.println("Facilities: " + selected.getAmenities());
                System.out.println("Booked Slots: " + selected.getBookedSlots().size());
            } else {
                System.out.println("Room not found! Valid IDs: "
                        + rooms.stream().map(Room::getRoomId).toList());
            }
        }

        private static void handleCheckAvailability(List<Room> rooms, Scanner scanner) {
            System.out.print("\nEnter Room ID: ");
            String roomId = scanner.nextLine().toUpperCase();
            Room room = findRoomById(rooms, roomId);

            if(room == null) {
                System.out.println("Room not found!");
                return;
            }

            try {
                System.out.print("Enter start time (yyyy-mm-ddTHH:mm): ");
                LocalDateTime start = LocalDateTime.parse(scanner.nextLine());

                System.out.print("Enter end time (yyyy-mm-ddTHH:mm): ");
                LocalDateTime end = LocalDateTime.parse(scanner.nextLine());

                TimeSlot slot = new TimeSlot(start, end);
                boolean available = room.isAvailable(slot);

                System.out.println("\nAvailability Status: " +
                        (available ? "AVAILABLE" : "NOT AVAILABLE"));
            } catch(DateTimeParseException e) {
                System.out.println("Invalid date format! Use format like 2024-12-31T20:00");
            }
        }

        private static void handleScheduleEvent(List<Room> rooms, Scanner scanner) {
            System.out.print("\nEnter Room ID: ");
            String roomId = scanner.nextLine().toUpperCase();
            Room room = findRoomById(rooms, roomId);

            if(room == null) {
                System.out.println("Room not found!");
                return;
            }

            try {
                System.out.print("Enter start time (yyyy-mm-ddTHH:mm): ");
                LocalDateTime start = LocalDateTime.parse(scanner.nextLine());

                System.out.print("Enter end time (yyyy-mm-ddTHH:mm): ");
                LocalDateTime end = LocalDateTime.parse(scanner.nextLine());

                TimeSlot slot = new TimeSlot(start, end);
                boolean booked = room.bookRoom(slot);

                System.out.println(booked ? "Booking successful!" : "Booking failed - time slot unavailable");
            } catch(DateTimeParseException e) {
                System.out.println("Invalid date format! Use format like 2024-12-31T20:00");
            }
        }

        private static void handleAddFacility(List<Room> rooms, Scanner scanner) {
            System.out.print("\nEnter Room ID: ");
            String roomId = scanner.nextLine().toUpperCase();
            Room room = findRoomById(rooms, roomId);

            if(room == null) {
                System.out.println("Room not found!");
                return;
            }

            System.out.print("Enter new facility name: ");
            String facility = scanner.nextLine();
            room.addAmenity(facility);
            System.out.println("Facility added successfully!");
            System.out.println("Updated facilities: " + room.getAmenities());
        }

        private static Room findRoomById(List<Room> rooms, String id) {
            return rooms.stream()
                    .filter(room -> room.getRoomId().equalsIgnoreCase(id))
                    .findFirst()
                    .orElse(null);
        }
    }

    class Wallet {
        private int walletId;
        private double balance;
        private static int WalletIdnum= 0;

        public void setBalance(double balance) {
            this.balance = balance;
        }
        public double getBalance() {
            return balance;
        }
        public int getWalletId() {

            return walletId;
        }
        @Override
        public String toString() {
            return "Wallet " + walletId + ": $" + balance;
        }
        public void setWalletId(int walletId) {

            this.walletId = walletId;
        }
        public void addfunds(double funds) {

            this.balance += funds;
        }
        public void deducfunds(double funds) {

            this.balance -= funds;
        }
        Wallet(double balance){
            this.walletId = ++WalletIdnum;
            this.balance = balance;

        }

    }

class Event {
    private int eventID;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private double ticketPrice;
    private Category category;
    private Room room;
    @Override
    public String toString() {
        return "Event " + name + " (" + date + " " + time + ") " + category + " " + ticketPrice + eventID;
    }


    public Event(int eventID, String name, String description, LocalDate date, LocalTime time, double ticketprice, Category category, Room room)
    {
        this.eventID = eventID;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.ticketPrice = ticketprice;
        this.category = category;
        this.room = room;
        adddatabase(this);

    }
    public void adddatabase(Object event) {

        Database.getEvents().add((Event) event);
    }
    public int getEventID()
    {
        return eventID;
    }

    public void setEventID(int eventID)
    {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public double getTicketPrice()
    {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Room getRoom()
    {
        return room;
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public void DisplayEventInformation()
    {
        System.out.println("Event: " + name);
        System.out.println("Description: " + description);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Ticket Price: $" + ticketPrice);
        System.out.println("Category: " + category.getCategoryID());
        System.out.println("Room: " + room.getRoomId());

    }

}

class Category {
    private String CategoryID;
    private String CategoryName;
Database db = Database.getInstance();
@Override
public String toString() {
    return "Category " + CategoryName + " (" + CategoryID + ")";
}
    public Category(String id,String name) {
        CategoryID = id;
        CategoryName = name;
        db.getCategories().add(this);
    }

    public String getCategoryID()
    {
        return CategoryID;
    }

    public void setCategoryID(String CategoryID)
    {
        this.CategoryID = CategoryID;
    }
    public String getCategoryName() {
        return CategoryName;
    }
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }
}

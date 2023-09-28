import java.util.*;
import java.util.stream.Collectors;



class CashRegister {
    private double cashBalance;

    public CashRegister(double initialCashBalance) {
        this.cashBalance = initialCashBalance;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void processCashPayment(double amountPaid, double totalCost) {
        if (amountPaid >= totalCost && cashBalance >= totalCost) {
            double change = amountPaid - totalCost;
            System.out.println("Cash payment received. Change: $" + change);
            cashBalance -= totalCost; // Subtract the total cost from the cash register
        } else {
            System.out.println("Insufficient cash payment. Transaction canceled.");
        }
    }

    public void reconcileCash() {
        System.out.println("Cash reconciliation completed.");
        System.out.println("Cash balance at the end of the day: $" + cashBalance);
    }
}

class StoreLocation {
    private String storeName;
    private String address;
    private String openingHours;
    private Map<Product, Integer> availableProducts;

    public StoreLocation(String storeName, String address, String openingHours) {
        this.storeName = storeName;
        this.address = address;
        this.openingHours = openingHours;
        this.availableProducts = new HashMap<>();
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public Map<Product, Integer> getAvailableProducts() {
        return availableProducts;
    }

    public void addProduct(Product product, int quantity) {
        availableProducts.put(product, quantity);
    }
}

class Payment {
    public static boolean processPayment(double amount, String paymentMethod) {
        // Simulate payment processing logic here
        if (paymentMethod.equalsIgnoreCase("Credit/Debit Card")) {
            boolean cardDetailsEntered = collectCreditCardDetails();
            if (cardDetailsEntered) {
                // Process the payment
                System.out.println("Payment of $" + amount + " via " + paymentMethod + " was successful.");
                return true;
            } else {
                System.out.println("Credit/Debit Card payment details not provided. Payment failed.");
                return false;
            }
        } else if (paymentMethod.equalsIgnoreCase("PayPal")) {
            boolean paypalDetailsEntered = collectPayPalDetails();
            if (paypalDetailsEntered) {
                // Process the payment
                System.out.println("Payment of $" + amount + " via " + paymentMethod + " was successful.");
                return true;
            } else {
                System.out.println("PayPal payment details not provided. Payment failed.");
                return false;
            }
        } else if (paymentMethod.equalsIgnoreCase("Gift Card")) {
            boolean giftCardDetailsEntered = collectGiftCardDetails();
            if (giftCardDetailsEntered) {
                // Process the payment
                System.out.println("Payment of $" + amount + " via " + paymentMethod + " was successful.");
                return true;
            } else {
                System.out.println("Gift Card payment details not provided. Payment failed.");
                return false;
            }
        } else {
            System.out.println("Invalid payment method.");
            return false;
        }
    }

    private static boolean collectCreditCardDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Credit/Debit Card Details:");

        // Collect input
        System.out.print("1) Card number (16 digits): ");
        String cardNumber = scanner.nextLine();
        System.out.print("2) Cardholder name: ");
        String cardholderName = scanner.nextLine();
        System.out.print("3) Expiration date (MM/YYYY): ");
        String expirationDate = scanner.nextLine();
        System.out.print("4) CVV code (3-4 digits): ");
        String cvvCode = scanner.nextLine();
        System.out.print("5) Billing address: ");
        String billingAddress = scanner.nextLine();
        System.out.print("6) Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("7) Shipping address: ");
        String shippingAddress = scanner.nextLine();
        System.out.print("8) Social security number (optional): ");
        String socialSecurityNumber = scanner.nextLine();
        System.out.print("9) Date of birth (optional): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("10) Email address (optional): ");
        String emailAddress = scanner.nextLine();

        // Validate input
        if (cardNumber.isEmpty() || cardholderName.isEmpty() || expirationDate.isEmpty() || cvvCode.isEmpty() ||
                billingAddress.isEmpty() || phoneNumber.isEmpty() || shippingAddress.isEmpty()) {
            System.out.println("Invalid input. Please provide all required details.");
            return false;
        }

        if (!cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number format. It should be 16 digits.");
            return false;
        }

        if (!expirationDate.matches("\\d{2}/\\d{4}")) {
            System.out.println("Invalid expiration date format. It should be MM/YYYY.");
            return false;
        }

        if (!cvvCode.matches("\\d{3,4}")) {
            System.out.println("Invalid CVV code format. It should be 3 or 4 digits.");
            return false;
        }

        // Add more validation as needed for other fields

        return true;
    }

    // Similar validation can be applied to other collectXxxDetails methods

    private static boolean collectPayPalDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter PayPal Details:");
        System.out.print("1) Email address: ");
        String emailAddress = scanner.nextLine();
        System.out.print("2) Password: ");
        String password = scanner.nextLine();
        System.out.print("3) PayPal address: ");
        String paypalAddress = scanner.nextLine();
        System.out.print("4) PayPal phone number: ");
        String paypalPhoneNumber = scanner.nextLine();
        System.out.print("5) Billing address: ");
        String billingAddress = scanner.nextLine();
        System.out.print("6) Shipping address: ");
        String shippingAddress = scanner.nextLine();


        if (!isValidEmail(emailAddress) || !isValidPassword(password) || paypalAddress.isEmpty()
                || !isValidPhoneNumber(paypalPhoneNumber) || billingAddress.isEmpty() || shippingAddress.isEmpty()) {
            System.out.println("Invalid input. Please provide valid details for all required fields.");
            return false;
        }

        return true;

    }

    private static boolean isValidEmail(String email) {

        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private static boolean isValidPassword(String password) {

        return password.length() >= 8; // Example: Password must be at least 8 characters long
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {

        return phoneNumber.matches("^\\d{10}$"); // Example: 10-digit phone number
    }

    private static boolean collectGiftCardDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Gift Card Details:");

        System.out.print("1) Gift card number (16-19 digits): ");
        String giftCardNumber = scanner.nextLine();
        if (!isValidGiftCardNumber(giftCardNumber)) {
            System.out.println("Invalid gift card number. It should be 16-19 digits.");
            return false;
        }

        System.out.print("2) PIN (4 digits): ");
        String pin = scanner.nextLine();
        if (!isValidPin(pin)) {
            System.out.println("Invalid PIN. It should be 4 digits.");
            return false;
        }

        System.out.print("3) Name: ");
        String name = scanner.nextLine();

        System.out.print("4) Email address: ");
        String emailAddress = scanner.nextLine();

        // Other validations if needed

        return true;
    }

    // Validate the gift card number
    private static boolean isValidGiftCardNumber(String giftCardNumber) {
        return giftCardNumber.matches("\\d{16,19}");
    }

    // Validate the PIN
    private static boolean isValidPin(String pin) {
        return pin.matches("\\d{4}");
    }
}

class SimpleECommerce {

    private static double calculateSubtotal(ShoppingCart cart) {
        List<OrderItem> cartItems = cart.getCartItems();
        double subtotal = 0.0;

        for (OrderItem item : cartItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double itemCost = product.getPrice() * quantity;
            subtotal += itemCost;
        }

        return subtotal;
    }

    private static double calculateTotalTax(ShoppingCart cart, Order order) {
        List<OrderItem> cartItems = cart.getCartItems();
        double totalTax = 0.0;

        for (OrderItem item : cartItems) {
            totalTax += order.calculateTaxForOrderItem(item);
        }

        return totalTax;
    }

    public static void main(String[] args) {
        List<Order> onlineOrders = new ArrayList<>(); // List of online orders
        List<Order> offlineOrders = new ArrayList<>();
        CashRegister cashRegister = new CashRegister(100000.0); // Initialize with an initial cash balance
        Map<Product, Integer> productQuantityMap = new HashMap<>();
        // Create and populate the productQuantityMap
        productQuantityMap.put(new Product("1", "ProductA", 20.0, 500, "Electronics"), 500);
        productQuantityMap.put(new Product("2", "ProductB", 30.0, 700, "Clothing"), 700);
        productQuantityMap.put(new Product("3", "ProductC", 15.0, 200, "Books"), 200);
        productQuantityMap.put(new Product("4", "ProductD", 10.0, 54, "Spare Parts"), 54);

        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "NY")); // Set the location for the user
        users.add(new User("user2", "password2", "CA"));
        users.add(new User("user3", "password3", "TX"));
        users.add(new User("user4", "password4", "FL"));
        users.add(new User("user5", "password5", "AL"));
        users.add(new User("user6", "password6", "AK"));
        users.add(new User("user7", "password7", "AZ"));
        users.add(new User("user8", "password8", "AR"));
        users.add(new User("user9", "password9", "CO"));
        users.add(new User("user10", "password10", "CT"));
        users.add(new User("user11", "password11", "DE")); // Set the location for the user

        List<StoreLocation> storeLocations = new ArrayList<>();
        storeLocations.add(new StoreLocation("ElectroMart", "123 Main St, NY", "9:00 AM - 6:00 PM"));
        storeLocations.add(new StoreLocation("FashionFusion", "456 Elm St, CA", "10:00 AM - 7:00 PM"));
        storeLocations.add(new StoreLocation("GadgetHub", "789 Oak Ave, TX", "8:30 AM - 5:30 PM"));
        storeLocations.add(new StoreLocation("BookNook", "101 Maple St, FL", "9:00 AM - 6:00 PM"));
        storeLocations.add(new StoreLocation("TechTrends", "555 Pine St, AL", "10:00 AM - 7:00 PM"));
        storeLocations.add(new StoreLocation("OutdoorGear", "321 Birch Rd, AK", "8:00 AM - 6:00 PM"));
        storeLocations.add(new StoreLocation("SportsSupreme", "987 Cedar St, AZ", "9:30 AM - 6:30 PM"));
        storeLocations.add(new StoreLocation("HomeEssentials", "456 Elm St, AR", "10:00 AM - 7:00 PM"));
        storeLocations.add(new StoreLocation("BeautyBoutique", "222 Walnut St, CO", "9:00 AM - 5:30 PM"));
        storeLocations.add(new StoreLocation("FurnitureWorld", "777 Willow Ave, CT", "10:30 AM - 8:00 PM"));

        System.out.println("Welcome to the Simple Online Store!");

        Scanner scanner = new Scanner(System.in);

        // User authentication
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User currentUser = authenticateUser(users, username, password);

        if (currentUser != null) {
            if (currentUser.getAccessLevel() == UserAccessLevel.CUSTOMER) {
                System.out.println("Welcome, Customer!");
                // Customer's shopping logic (unchanged)
            } else if (currentUser.getAccessLevel() == UserAccessLevel.STAFF) {
                // Staff's POS system logic
                System.out.println("Authentication successful. Welcome, " + currentUser.getUsername() + "!");
                System.out.println("Access Level: " + currentUser.getAccessLevel());

                StoreLocation selectedStore = null;
                // Add staff-specific menu options and functionality here
                if (currentUser.getAccessLevel() == UserAccessLevel.STAFF) {
                    System.out.println("Staff Menu:");
                    System.out.println("1. Point of Sale (POS)");
                    System.out.println("2. View Store Information");
                    System.out.print("Enter your choice: ");
                    int staffChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (staffChoice == 1) {
                        System.out.println("Point of Sale (POS):");
                        ShoppingCart staffCart = new ShoppingCart();

                        while (true) {
                            displayProductList(productQuantityMap);
                            System.out.println("Enter the product ID to add to the sale (0 to finish sale):");
                            String productChoice = scanner.nextLine();

                            if (productChoice.equals("0")) {
                                displayCart(staffCart, null); // Display the sale details
                                System.out.println("Sale complete.");
                                break;
                            } else if (isValidProductId(productQuantityMap.keySet(), productChoice)) {
                                System.out.print("Enter the quantity: ");
                                int quantity = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character
                                Product selectedProduct = getProductById(productQuantityMap.keySet(), productChoice);

                                if (selectedProduct != null && productQuantityMap.containsKey(selectedProduct) &&
                                        productQuantityMap.get(selectedProduct) >= quantity) {
                                    OrderItem orderItem = new OrderItem(selectedProduct, quantity);
                                    staffCart.addItem(orderItem);
                                    System.out.println(selectedProduct.getName() + " added to the sale.");
                                    selectedProduct.reduceQuantity(quantity);
                                } else {
                                    System.out.println("Invalid quantity or product not available.");
                                }
                            } else {
                                System.out.println("Invalid product ID. Please enter a valid product ID.");
                            }
                        }
                    } else if (staffChoice == 2) {
                        // View store information
                        System.out.println("Select a store to view:");
                        for (int i = 0; i < storeLocations.size(); i++) {
                            System.out.println((i + 1) + ". " + storeLocations.get(i).getStoreName());
                        }
                        System.out.print("Enter the store number: ");
                        int storeChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (storeChoice >= 1 && storeChoice <= storeLocations.size()) {
                            selectedStore = storeLocations.get(storeChoice - 1); // Set selectedStore
                            System.out.println("You are viewing " + selectedStore.getStoreName());
                            System.out.println("Store Address: " + selectedStore.getAddress());
                            System.out.println("Opening Hours: " + selectedStore.getOpeningHours());
                        } else {
                            System.out.println("Invalid store selection.");
                        }
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
            }
        }

        if (currentUser != null) {
            System.out.println("Authentication successful. Welcome, " + currentUser.getUsername() + "!");
            ShoppingCart cart = new ShoppingCart();
            Order order = new Order(currentUser);

            System.out.println("Select shopping mode:");
            System.out.println("1. Online Shopping");
            System.out.println("2. Visit Physical Store");
            System.out.print("Enter your choice (1/2): ");
            int shoppingMode = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            double subtotal = 0.0;
            double totalTax = 0.0;
            double totalCost = 0.0;

            boolean checkoutSuccessful = false;
            if (shoppingMode == 1) {

                // Online shopping
                while (true) {
                    displayProductList(productQuantityMap);
                    System.out.println("Enter the product ID you want to add to your cart (0 to checkout):");
                    String choice = scanner.nextLine();

                    if (choice.equals("0")) {
                        displayCart(cart, order);
                        break;
                    } else if (isValidProductId(productQuantityMap.keySet(), choice)) {
                        System.out.print("Enter the quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        Product selectedProduct = getProductById(productQuantityMap.keySet(), choice);
                        if (selectedProduct != null && selectedProduct.getQuantity() >= quantity) {
                            OrderItem orderItem = new OrderItem(selectedProduct, quantity);
                            cart.addItem(orderItem);
                            System.out.println(selectedProduct.getName() + " added to your cart.");
                            selectedProduct.reduceQuantity(quantity);
                        } else {
                            System.out.println("Invalid quantity or product not available.");
                        }
                    } else {
                        System.out.println("Invalid choice. Please enter a valid product ID.");
                    }
                    subtotal = calculateSubtotal(cart);
                    totalTax = calculateTotalTax(cart, order);
                    totalCost = calculateTotalCost(cart, order);

                    System.out.println("Select payment method:");
                    System.out.println("1. Credit/Debit Card");
                    System.out.println("2. PayPal");
                    System.out.println("3. Gift Card");
                    System.out.println("4. Cash");
                    System.out.print("Enter your choice (1/2/3/4): ");
                    int paymentChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    boolean paymentSuccessful = false;

                    switch (paymentChoice) {
                        case 1:
                            paymentSuccessful = Payment.processPayment(calculateTotalCost(cart, order),
                                    "Credit/Debit Card");
                            break;
                        case 2:
                            paymentSuccessful = Payment.processPayment(calculateTotalCost(cart, order), "PayPal");
                            break;
                        case 3:
                            paymentSuccessful = Payment.processPayment(calculateTotalCost(cart, order), "Gift Card");
                            break;
                        case 4:
                            // Cash payment
                            System.out.print("Enter the amount paid in cash: $");
                            double amountPaid = scanner.nextDouble();
                            scanner.nextLine(); // Consume the newline character
                            // Instead of creating a new CashRegister, use the existing one
                            cashRegister.processCashPayment(amountPaid, calculateTotalCost(cart, order));

                            paymentSuccessful = true; // Assume successful payment for cash transactions
                            break;
                        default:
                            System.out.println("Invalid payment choice.");
                    }

                    for (OrderItem item : cart.getCartItems()) {
                        totalTax += order.calculateTaxForOrderItem(item);
                    }

                    if (paymentSuccessful) {
                        // ... (previous code for processing order)

                        if (checkoutSuccessful) {

                            // Set the order status and ID
                            order.updateOrderStatus(OrderStatus.PAID); // Set the order status to "Completed"
                            order.generateOrderId(); // Generate a unique order ID

                            // Call the generateReceipt method for the order
                            order.generateReceipt();

                            // Display the receipt after successful checkout
                            displayCart(cart, order); // Display the shopping cart
                            // Display receipt information
                            System.out.println("Receipt for Order ID: " + order.getId());

                            System.out.println("Order Status: " + order.getStatus());

                            System.out.println("Thank you for shopping with us!");


                        }
                        cashRegister.reconcileCash();

                        checkoutSuccessful = true;
                        break;

                    }

                }

            } else if (shoppingMode == 2) {
                // Offline shopping
                System.out.println("Select a store to visit:");
                for (int i = 0; i < storeLocations.size(); i++) {
                    System.out.println((i + 1) + ". " + storeLocations.get(i).getStoreName());
                }
                System.out.print("Enter the store number: ");
                int storeChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (storeChoice >= 1 && storeChoice <= storeLocations.size()) {
                    StoreLocation selectedStore = storeLocations.get(storeChoice - 1);
                    System.out.println("You are visiting " + selectedStore.getStoreName());
                    System.out.println("Store Address: " + selectedStore.getAddress());
                    System.out.println("Opening Hours: " + selectedStore.getOpeningHours());

                    while (true) {
                        displayProductList(productQuantityMap);
                        System.out.println("Enter the product ID you want to purchase (0 to exit store):");
                        String choice = scanner.nextLine();

                        if (choice.equals("0")) {
                            break;
                        } else if (isValidProductId(productQuantityMap.keySet(), choice)) {
                            System.out.print("Enter the quantity: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            Product selectedProduct = getProductById(productQuantityMap.keySet(), choice);
                            if (selectedProduct != null && productQuantityMap.containsKey(selectedProduct) &&
                                    productQuantityMap.get(selectedProduct) >= quantity) {
                                OrderItem orderItem = new OrderItem(selectedProduct, quantity);
                                cart.addItem(orderItem);
                                System.out.println(selectedProduct.getName() + " added to your cart.");
                                selectedProduct.reduceQuantity(quantity);

                                double itemCost = selectedProduct.getPrice() * quantity;
                                subtotal += itemCost;
                            } else {
                                System.out.println("Invalid quantity or product not available.");
                            }
                        } else {
                            System.out.println("Invalid choice. Please enter a valid product ID.");
                        }
                    }

                } else {
                    System.out.println("Invalid store selection.");
                }
            }
            if (checkoutSuccessful) {
                // Print the receipt only if the checkout was successful
                System.out.println("Receipt for Order ID: " + order.getId());
                System.out.println("Order Date: " + order.getOrderDate());
                System.out.println("Customer: " + currentUser.getUsername());
                System.out.println("Order Status: " + order.getStatus());
                System.out.println("Ordered Items:");
                for (OrderItem item : order.getOrderItems()) {
                    System.out.println(item.getProduct().getName() + " x " + item.getQuantity());
                }

                System.out.println("Subtotal: $" + subtotal);
                System.out.println("Total Tax: $" + totalTax);
                System.out.println("Total Cost: $" + totalCost);
                System.out.println("Thank you for shopping with us!");
            }

        } else {
            System.out.println("Invalid choice. Please select 1 for online shopping or 2 for offline shopping.");
        }

        List<Order> orders = new ArrayList<>(); // Assuming you have a list of orders
        SalesRepository salesRepository = new SalesRepository();
        SalesReport.generateSalesSummaryReport(orders, salesRepository);
        SalesReport.generatePopularProductsReport(orders);
        SalesReport.generateSalesByLocationReport(orders, storeLocations);

    }

    private static User authenticateUser(List<User> users, String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static void displayProductList(Map<Product, Integer> products) {
        System.out.println("Available Products:");
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(
                    product.getId() + ". " + product.getName() + " - $" + product.getPrice() +
                            " (Available Quantity: " + quantity + ")");
        }
    }

    private static boolean isValidProductId(Collection<Product> products, String id) {
        String trimmedId = id.trim(); // Trim leading/trailing whitespaces
        for (Product product : products) {
            System.out.println("Checking product ID: [" + product.getId() + "], input ID: [" + trimmedId + "]");
            if (product.getId().equals(trimmedId)) {
                System.out.println("Valid choice: [" + trimmedId + "]");
                return true;
            }
        }
        System.out.println("Invalid choice: [" + trimmedId + "]");
        System.out.println("Available product IDs:");
        for (Product product : products) {
            System.out.println("[" + product.getId() + "] - " + product.getName());
        }
        return false;
    }

    private static Product getProductById(Collection<Product> products, String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    private static void displayCart(ShoppingCart cart, Order order) {
        List<OrderItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Shopping Cart:");
            double totalCost = 0.0;

            for (OrderItem item : cartItems) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();
                double itemCost = product.getPrice() * quantity;
                double itemTax = order.calculateTaxForOrderItem(item);
                totalCost += itemCost + itemTax;
                System.out.println(
                        product.getName() + " - $" + product.getPrice() + " x " + quantity +
                                " = $" + itemCost + " (Tax: $" + itemTax + ")");
            }

            System.out.println("Total: $" + totalCost);
        }
    }

    private static double calculateTotalCost(ShoppingCart cart, Order order) {
        List<OrderItem> cartItems = cart.getCartItems();
        double totalCost = 0.0;

        for (OrderItem item : cartItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double itemCost = product.getPrice() * quantity;
            double itemTax = order.calculateTaxForOrderItem(item);
            totalCost += itemCost + itemTax;
        }

        return totalCost;
    }

}

class SalesReport {
    public static double calculateTotalCost(ShoppingCart cart, Order order) {
        List<OrderItem> cartItems = cart.getCartItems();
        double totalCost = 0.0;

        for (OrderItem item : cartItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double itemCost = product.getPrice() * quantity;
            double itemTax = order.calculateTaxForOrderItem(item);
            totalCost += itemCost + itemTax;
        }

        return totalCost;
    }

    public static void generateSalesSummaryReport(List<Order> orders, SalesRepository salesRepository) {
        double totalSales = 0.0;
        int totalOrders = orders.size();

        for (Order order : orders) {
            totalSales += calculateTotalCost(order.getCart(), order);
        }

        double averageOrderValue = totalSales / totalOrders;

        salesRepository.updateSales(totalSales);

        // Print the sales summary report
        System.out.println("Sales Summary Report");
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Total Orders: " + totalOrders);
        System.out.println("Average Order Value: $" + averageOrderValue);
    }

    public static void generatePopularProductsReport(List<Order> orders) {
        Map<Product, Integer> productSalesCount = new HashMap<>();

        // Count the number of times each product is purchased
        for (Order order : orders) {
            for (OrderItem item : order.getCart().getCartItems()) {
                Product product = item.getProduct();
                productSalesCount.put(product, productSalesCount.getOrDefault(product, 0) + item.getQuantity());
            }
        }

        // Sort products by sales count in descending order
        List<Map.Entry<Product, Integer>> sortedProducts = productSalesCount.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        // Print the popular products report
        System.out.println("Popular Products Report");
        int rank = 1;
        for (Map.Entry<Product, Integer> entry : sortedProducts) {
            System.out.println(rank + ". " + entry.getKey().getName() + " - Sales Count: " + entry.getValue());
            rank++;
        }
    }

    public static void generateSalesByLocationReport(List<Order> orders, List<StoreLocation> storeLocations) {
        Map<String, Double> salesByLocation = new HashMap<>();

        // Initialize sales by location
        for (StoreLocation location : storeLocations) {
            salesByLocation.put(location.getStoreName(), 0.0);
        }

        // Calculate sales for each store location
        for (Order order : orders) {
            String storeName = order.getCustomer().getLocation();
            double orderTotal = calculateTotalCost(order.getCart(), order);
            salesByLocation.put(storeName, salesByLocation.get(storeName) + orderTotal);
        }

        // Print the sales by location report
        System.out.println("Sales by Location Report");
        for (Map.Entry<String, Double> entry : salesByLocation.entrySet()) {
            System.out.println(entry.getKey() + " - Total Sales: $" + entry.getValue());
        }
    }

}

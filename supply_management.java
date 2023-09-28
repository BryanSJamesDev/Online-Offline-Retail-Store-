import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.*;

class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String productType;
    private Map<StoreLocation, Integer> storeAvailability;
    private List<OrderItem> orderItems;
    private int totalSales;

    public Product(String id, String name, double price, int quantity, String productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productType = productType;
        this.storeAvailability = new HashMap<>();
        this.totalSales = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductType() {
        return productType;
    }

    public Map<StoreLocation, Integer> getStoreAvailability() {
        return storeAvailability;
    }

    public void setStoreAvailability(StoreLocation storeLocation, int quantity) {
        storeAvailability.put(storeLocation, quantity);
    }

    // Modify the 'addItemToOrder' method in the Order class
    public void addItemToOrder(Product product, int quantity) {
        // Check product availability and reduce quantity from inventory
        if (product != null && product.getQuantity() >= quantity) {
            OrderItem orderItem = new OrderItem(product, quantity);

            orderItems.add(orderItem);
            product.reduceQuantity(quantity); // Deduct quantity from inventory
            product.updateSales(quantity); // Update product sales
        }
    }

    public void reduceQuantity(int amount) {
        if (amount <= quantity) {
            quantity -= amount;
        }
    }

    public void updateSales(int quantitySold) {
        totalSales += quantitySold; // Update total sales
    }

    public int getTotalSales() {
        return totalSales;
    }

    public double calculateTaxForOrderItem(OrderItem orderItem) {
        return 0;
    }

    public double calculateTax(double d, String customerLocation, String productType2) {
        return 0;
    }
}

enum OrderStatus {
    PENDING, PAID, CANCELLED,
}

class Order {
    private String id;
    private String orderId;
    private User customer;
    private Date orderDate;
    private ShoppingCart cart;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private static int orderIdCounter = 1;

    public Order(User customer) {
        generateOrderId();
        this.customer = customer;
        this.orderDate = new Date();
        this.orderItems = new ArrayList<>();
        this.orderStatus = OrderStatus.PAID;
    }

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getId() {
        return id;
    }

    public String getOrderIdString() {
        return orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Order(ShoppingCart cart) {
        this.cart = cart;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void generateOrderId() {
        // Generate a unique order ID based on the counter and set it to the 'id' field
        this.id = "ORD" + String.format("%04d", orderIdCounter); // Example format: ORD0001
        orderIdCounter++; // Increment the counter for the next order
    }

    public void addItemToOrder(Product product, int quantity) {
        // Check product availability and reduce quantity from inventory
        if (product != null && product.getQuantity() >= quantity) {
            OrderItem orderItem = new OrderItem(product, quantity);
            orderItems.add(new OrderItem(product, quantity));
            product.reduceQuantity(quantity);
            product.updateSales(quantity);
        }
    }

    public void updateOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    public void processPayment(boolean paymentSuccessful) {

        if (paymentSuccessful) {
            updateOrderStatus(OrderStatus.PAID);
        } else {
            updateOrderStatus(OrderStatus.CANCELLED); // Update the status to CANCELLED on payment failure
        }
    }

    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (OrderItem item : orderItems) {
            subtotal += item.calculateItemCost();
        }
        return subtotal;
    }

    // Define a method to calculate the total tax for the order
    public double calculateTotalTax() {
        double totalTax = 0.0;
        for (OrderItem item : orderItems) {
            totalTax += item.calculateItemTax();
        }
        return totalTax;
    }

    // Define a method to calculate the total cost for the order (subtotal + total
    // tax)
    public double calculateTotalCost() {
        double subtotal = calculateSubtotal();
        double totalTax = calculateTotalTax();
        double totalCost = subtotal + totalTax;
        return totalCost;
    }

    // Implement methods for retrieving order history, generating shipping labels,
    // and more
    // Add this method to the Order class
    public void generateReceipt() {

        // Create a receipt object or print the details directly
        System.out.println("Receipt for Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Customer: " + customer.getUsername());
        System.out.println("Order Status: " + orderStatus);
        System.out.println("Ordered Items:");
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double itemCost = product.getPrice() * quantity;
            double itemTax = item.calculateItemTax(customer.getLocation());
            System.out.println(
                    product.getName() + " - $" + product.getPrice() + " x " + quantity +
                            " = $" + itemCost + " (Tax: $" + itemTax + ")");
        }
        double subtotal = calculateSubtotal();
        double totalTax = calculateTotalTax();
        double totalCost = calculateTotalCost();
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Total Tax: $" + totalTax);
        System.out.println("Total Cost: $" + totalCost);
        System.out.println("Thank you for shopping with us!");
    }

    // Method to calculate tax based on the order's subtotal, customer location, and
    // product type
    public double calculateTax(double subtotal, String customerLocation, String productType) {
        // Retrieve the applicable tax rate based on the customer's location and product
        // type
        double taxRate = getTaxRateByLocationAndProductType(customerLocation, productType);

        // Calculate tax amount
        double taxAmount = subtotal * (taxRate / 100.0);

        // You can store the tax amount in the Order class or return it
        return taxAmount;
    }

    private double getTaxRateByLocationAndProductType(String customerLocation, String productType) {
        // Implement logic to determine the tax rate based on customer location and
        // product type
        // This could involve looking up tax rates from a database or using complex
        // rules.
        // Replace this with your actual tax rate retrieval logic.

        // Example: Different tax rates for different regions and product categories
        if (customerLocation.equals("NY")) {
            if (productType.equals("Electronics")) {
                return 8.0; // 8% tax for electronics in New York
            } else if (productType.equals("Clothing")) {
                return 7.0; // 7% tax for clothing in New York
            } else {
                return 7.5; // 7.5% general tax in New York for other products
            }
        } else if (customerLocation.equals("CA")) {
            if (productType.equals("Electronics")) {
                return 9.0; // 9% tax for electronics in California
            } else if (productType.equals("Clothing")) {
                return 5.0; // 5% tax for clothing in California
            } else {
                return 8.5; // 8.5% general tax in California for other products
            }
        } else if (customerLocation.equals("TX")) {
            return 6.25; // 6.25% general tax in Texas for all products
        } else if (customerLocation.equals("FL")) {
            if (productType.equals("Books")) {
                return 0.0; // No tax for books in Florida
            } else {
                return 7.0; // 7% general tax in Florida for other products
            }
        } else if (customerLocation.equals("AL")) {
            return 8.5; // Example tax rate for Alabama
        } else if (customerLocation.equals("AK")) {
            return 7.0; // Example tax rate for Alaska
        } else if (customerLocation.equals("AZ")) {
            return 6.75; // Example tax rate for Arizona
        } else if (customerLocation.equals("AR")) {
            return 7.25; // Example tax rate for Arkansas
        } else if (customerLocation.equals("CO")) {
            return 7.5; // Example tax rate for Colorado
        } else if (customerLocation.equals("CT")) {
            return 6.35; // Example tax rate for Connecticut
        } else if (customerLocation.equals("DE")) {
            return 6.0; // Example tax rate for Delaware
        } else {
            return 7.0; // Default tax rate for other locations and product types
        }
    }

    public double calculateTaxForOrderItem(OrderItem orderItem) {
        double subtotal = orderItem.getProduct().getPrice() * orderItem.getQuantity();
        String customerLocation = customer.getLocation(); // Assuming User class has a getLocation method
        String productType = orderItem.getProductType();
        return calculateTax(subtotal, customerLocation, productType);
    }

    public String getStatus() {
        return orderStatus.toString();
    }

    public void setSubtotal(double subtotal) {
    }

    public void setTotalTax(double totalTax) {
    }

    public void setTotalCost(double totalCost) {
    }

    public void setStatus(String string) {
    }

    public String getSubtotal() {
        return null;
    }

    public String getTotalTax() {
        return null;
    }
}

class OrderItem {
    private Product product;
    private int quantity;
    private Order order;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    public double calculateItemTax() {
        return 0;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double calculateItemCost() {
        // Calculate the cost based on the product's price and the quantity ordered
        return product.getPrice() * quantity;
    }

    public double calculateItemTax(String customerLocation) {
        // Calculate the tax for the item based on the product's price, quantity, and
        // customer location
        double itemTax = product.calculateTax(product.getPrice() * quantity, customerLocation,
                product.getProductType());
        return itemTax * quantity;
    }

    public String getProductType() {
        return product.getProductType();
    }

    public Order getOrder() {
        return order;
    }
}

enum UserAccessLevel {
    CUSTOMER, STAFF
}

class User {
    private String username;
    private String password;
    private String location;
    private List<Order> orderHistory;
    private UserAccessLevel accessLevel;

    public User(String username, String password, String location) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.orderHistory = new ArrayList<>();
        this.accessLevel = UserAccessLevel.CUSTOMER;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location; // Getter method to retrieve the user's location
    }

    public UserAccessLevel getAccessLevel() {
        return accessLevel;
    }
}

class ShoppingCart {
    private List<OrderItem> cartItems;

    public ShoppingCart() {
        cartItems = new ArrayList<>();
    }

    public void addItem(OrderItem orderItem) {
        cartItems.add(orderItem);
    }

    public List<OrderItem> getCartItems() {
        return cartItems;
    }
}
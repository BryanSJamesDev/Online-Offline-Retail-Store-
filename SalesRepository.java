public class SalesRepository {
    // Add fields and methods to store and retrieve sales data
    private double totalSales;

    public void addSale(double saleAmount) {
        totalSales += saleAmount;
    }

    public double getTotalSales() {
        return totalSales;
    }

    // Define an updateSales method to update the total sales
    public void updateSales(double newTotalSales) {
        totalSales = newTotalSales;
    }
}

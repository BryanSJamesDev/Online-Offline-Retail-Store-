public class SalesReportService {

    private SalesRepository salesRepository;

    public SalesReportService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public void generateSalesSummaryReport() {
        // Get the total sales from the SalesRepository
        double totalSales = salesRepository.getTotalSales();

        // Print the sales summary report
        System.out.println("Total sales: $" + totalSales);
    }
}

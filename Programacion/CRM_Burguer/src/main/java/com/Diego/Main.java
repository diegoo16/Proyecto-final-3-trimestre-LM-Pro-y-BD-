import com.Diego.controller.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CustomerController customer = new CustomerController();
        EmployeeController employee = new EmployeeController();
        ProductController product = new ProductController();
        OrderController order = new OrderController();
        OrderDetailController detail = new OrderDetailController();

        int option;

        do{
            System.out.println("\n=== CRM_Burguer ===");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Employee");
            System.out.println("3. Add Product");
            System.out.println("4. Add Order");
            System.out.println("5. Add Order Detail");
            System.out.println("6. Exit");

            option = sc.nextInt();
            sc.nextLine();

            switch(option){
                case 1: customer.createCustomer(); break;
                case 2: employee.createEmployee(); break;
                case 3: product.createProduct(); break;
                case 4: order.createOrder(); break;
                case 5: detail.createOrderDetail(); break;
            }

        }while(option != 6);
    }
}
package com.Diego.util;

import com.Diego.model.Customer;

import java.io.FileWriter;
import java.util.List;

public class CsvExporter {

    public static void exportCustomers(List<Customer> customers) {

        try(FileWriter writer =
                    new FileWriter("customers.csv")) {

            writer.write("ID,Name,Phone,Email\n");

            for(Customer c : customers){
                writer.write(
                        c.getId() + "," +
                                c.getName() + "," +
                                c.getPhone() + "," +
                                c.getEmail() + "\n"
                );
            }

            System.out.println("CSV exported");

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
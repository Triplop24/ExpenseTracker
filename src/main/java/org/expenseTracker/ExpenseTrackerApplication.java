package org.expenseTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ExpenseTrackerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ExpenseTrackerApplication.class, args);
        ExpenseService service = applicationContext.getBean(ExpenseService.class);

        System.out.println(service);
    }
}
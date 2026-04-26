package app.action;

import app.model.Customer;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/customer_lists")
public class CustomerList extends BaseActionList<Customer> {
}
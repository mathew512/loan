package app.model;

import app.framework.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "loan")

@LoanForm(
        label = "Loan Registration",
        actionUrl = "./register_loan"
)
@LoanTable(
        label = "Loans",
        tableUrl = "./loan_lists",
        registerUrl = "./register_loan",
        formUrl = "./register_loan"
)
@LoanMenu(
        label = "Loans",
        url = "loan_lists"
)
@DbTable(name = "loan")
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DbColumn(name = "id", type = "INT", primaryKey = true, autoIncrement = true)
    private int id;

    @DbColumn(name = "customer_name", type = "VARCHAR(255)")
    @LoanFormField(
            label = "Customer Name",
            placeholder = "Enter name"
    )
    @LoanTableCol
    private String customerName;

    @DbColumn(name = "amount", type = "DECIMAL(10,2)")
    @LoanFormField(
            label = "Amount",
            placeholder = "Enter amount"
    )
    @LoanTableCol
    private BigDecimal amount;

    @DbColumn(name = "interest_rate", type = "DOUBLE")
    @LoanFormField(
            label = "Interest Rate",
            placeholder = "%"
    )
    @LoanTableCol
    private double interestRate;

    @DbColumn(name = "start_date", type = "DATE")
    @LoanFormField(
            label = "Date",
            placeholder = "yyyy-mm-dd"
    )
    @LoanTableCol
    private Date startDate;

    @DbColumn(name = "status", type = "VARCHAR(50)")
    @LoanFormField(
            label = "Status",
            placeholder = "Pending"
    )
    @LoanTableCol
    private String status;

    // ================= CONSTRUCTOR =================
    public Loan() {}

    public Loan(String customerName, BigDecimal amount, double interestRate, Date startDate, String status) {
        this.customerName = customerName;
        this.amount = amount;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.status = status;
    }

    // ================= GETTERS & SETTERS =================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
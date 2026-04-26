package app.model;

import app.framework.*;

import java.io.Serializable;

@PageMenuItem(label = "Registered Customers", url = "/customer_lists")

@DbTable(name = "customers")

@LoanForm(
        label = "Customer Registration",
        actionUrl = "/register_customer"
)

@LoanTable(
        label = "Customers",
        tableUrl = "/customer_lists",
        registerUrl = "/register_customer",
        formUrl = "/register_customer"
)

@LoanMenu(
        label = "Customers",
        url = "/customer_lists"
)

public class Customer implements Serializable {

    @DbColumn(name = "id", type = "INT", primaryKey = true, autoIncrement = true)
    private int id;

    @DbColumn(name = "full_name", type = "VARCHAR(255)")
    @LoanFormField(
            label = "Full Name",
            placeholder = "Enter full name"
    )
    @LoanTableCol
    private String fullName;

    @DbColumn(name = "phone", type = "VARCHAR(50)")
    @LoanFormField(
            label = "Phone",
            placeholder = "07XXXXXXXX"
    )
    @LoanTableCol
    private String phone;

    @DbColumn(name = "national_id", type = "VARCHAR(100)")
    @LoanFormField(
            label = "National ID",
            placeholder = "Enter ID number"
    )
    @LoanTableCol
    private String nationalId;

    // ================= CONSTRUCTORS =================
    public Customer() {}

    public Customer(String fullName, String phone, String nationalId) {
        this.fullName = fullName;
        this.phone = phone;
        this.nationalId = nationalId;
    }

    // ================= GETTERS & SETTERS =================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
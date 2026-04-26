package app.model;

import app.framework.*;

import java.io.Serializable;
import java.math.BigDecimal;

@LoanForm(
        label = "Loan Type",
        actionUrl = "/register_loan_type"
)
@LoanTable(
        label = "Loan Types",
        tableUrl = "/loan_type_lists",
        registerUrl = "/register_loan_type",
        formUrl = "/register_loan_type"
)
@LoanMenu(
        label = "Loan Types",
        url = "/loan_type_lists"
)
@DbTable(name = "loan_type")
public class LoanType implements Serializable {

    @DbColumn(name = "id", type = "INT", primaryKey = true, autoIncrement = true)
    private int id;

    @DbColumn(name = "name", type = "VARCHAR(255)")
    @LoanFormField(
            label = "Type Name",
            placeholder = "e.g Business, Personal"
    )
    @LoanTableCol
    private String name;

    @DbColumn(name = "max_amount", type = "DECIMAL(10,2)")
    @LoanFormField(
            label = "Max Amount",
            placeholder = "Enter maximum loan amount"
    )
    @LoanTableCol
    private BigDecimal maxAmount;

    public LoanType() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }
}
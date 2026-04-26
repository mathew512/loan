package app.model;

import app.framework.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@LoanForm(
        label = "Loan Repayment",
        actionUrl = "/register_repayment"
)
@LoanTable(
        label = "Repayments",
        tableUrl = "/repayment_lists",
        registerUrl = "/register_repayment",
        formUrl = "/register_repayment"
)
@LoanMenu(
        label = "Repayments",
        url = "/repayment_lists"
)
@DbTable(name = "repayment")
public class Repayment implements Serializable {

    @DbColumn(name = "id", type = "INT", primaryKey = true, autoIncrement = true)
    private int id;

    @DbColumn(name = "loan_id", type = "INT")
    @LoanFormField(
            label = "Loan ID",
            placeholder = "Select Loan ID"
    )
    @LoanTableCol
    private int loanId;

    @DbColumn(name = "amount_paid", type = "DECIMAL(10,2)")
    @LoanFormField(
            label = "Amount Paid",
            placeholder = "Enter amount"
    )
    @LoanTableCol
    private BigDecimal amountPaid;

    @DbColumn(name = "payment_date", type = "DATE")
    @LoanFormField(
            label = "Payment Date",
            placeholder = "yyyy-mm-dd"
    )
    @LoanTableCol
    private Date paymentDate;

    public Repayment() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
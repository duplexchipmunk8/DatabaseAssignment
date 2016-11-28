import java.util.Date;

public class Rental {
    private java.util.Date dateFrom;
    private java.util.Date dateTo;
    private Customer customer;

    public Rental(Date utilDateFrom, Date utilDateTo, Customer customer) {
        this.dateFrom = utilDateFrom;
        this.dateTo = utilDateTo;
        this.customer = customer;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Customer getCustomer() {
        return customer;
    }
}

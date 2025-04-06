public class Customer {
    private int customerId;
    private String name;

    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public int getCustomerId() {
    	return customerId;
    }

    public String getCustomerName() {
    	return name;
    }

    @Override
    public String toString() {
        return customerId + " | " + name;
    }

    public String toSaveString() {
        return customerId + " | " + name;
    }
}
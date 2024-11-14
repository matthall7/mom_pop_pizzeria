public class Customer {
    private String phoneNumber;
    private String name;
    private String street;
    private String city;
    private String zipcode;
    private String cardNum;
    private String expDate;
    private String cvv;

    Customer(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    Customer(String phoneNumber, String name, String street, String city, String zipcode, String cardNum, String expDate, String cvv) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.cardNum = cardNum;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}

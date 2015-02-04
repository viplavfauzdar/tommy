<%@page import="com.owlike.genson.Genson"%>
<%@page import="com.balancedpayments.Customer"%>
<%@page import="java.util.Map"%>
<%@page import="com.balancedpayments.BankAccount"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.balancedpayments.Marketplace"%>
<%@page import="com.balancedpayments.Balanced"%>
<%
    Balanced.configure("ak-test-25r0ywB4cv5vz4bwD5oBnXVzkzfy7b89M");
    Marketplace mp = Marketplace.mine();
    out.print(mp.name + " - " + mp.domain_url);
    
    //create customer
//    Map<String, Object> payload_customer = new HashMap<String, Object>();
//    payload_customer = new HashMap<String, Object>();
//    payload_customer.put("name", "Viplav Fauzdar");
//    payload_customer.put("dob_month", 7);
//    payload_customer.put("dob_year", 1978);
//    payload_customer.put("ssn_last4", "5432");
//    
//    Map<String, Object> payload_customer_address = new HashMap<String, Object>();
//    payload_customer_address.put("city", "Decatur");
//    payload_customer_address.put("state","GA");
//    payload_customer_address.put("postal_code","12345");
//    payload_customer.put("address", payload_customer_address);
//    
//    Customer customer = new Customer(payload_customer);
//    customer.save();      
    
    //create bank account
//    Map<String, Object> payload_bank_account = new HashMap<String, Object>();
//    payload_bank_account.put("account_number", "9900000121");
//    payload_bank_account.put("name", "John Doe");
//    payload_bank_account.put("routing_number", "121000358");
//    payload_bank_account.put("account_type", "checking");
//
//    BankAccount bankAccount = new BankAccount(payload_bank_account);
//    bankAccount.save();
    
    BankAccount bankAccount = new BankAccount("/bank_accounts/BA28tvuto7HVD5nkSRWVJX3s");
    //Genson genson = new Genson();
    //out.println(genson.serialize(bankAccount));
    //associate bank account to customer
    //bankAccount.associateToCustomer(customer);

%>
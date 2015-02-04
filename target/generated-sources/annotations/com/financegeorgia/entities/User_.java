package com.financegeorgia.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-01-30T12:17:47")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> password1;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, Date> dob;
    public static volatile SingularAttribute<User, Boolean> pptosnda;
    public static volatile SingularAttribute<User, Short> locked;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> mi;
    public static volatile SingularAttribute<User, String> aboutMe;
    public static volatile SingularAttribute<User, String> passresetKey;
    public static volatile SingularAttribute<User, String> userType;

}
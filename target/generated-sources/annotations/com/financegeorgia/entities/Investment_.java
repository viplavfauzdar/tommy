package com.financegeorgia.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-03-24T16:28:35")
@StaticMetamodel(Investment.class)
public class Investment_ { 

    public static volatile SingularAttribute<Investment, Integer> id;
    public static volatile SingularAttribute<Investment, Date> investmentDate;
    public static volatile SingularAttribute<Investment, Integer> equityObtained;
    public static volatile SingularAttribute<Investment, Integer> userId;
    public static volatile SingularAttribute<Investment, Integer> commissionTaken;
    public static volatile SingularAttribute<Investment, Integer> businessId;
    public static volatile SingularAttribute<Investment, Integer> amountInvested;

}
package com.financegeorgia.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-01-15T16:41:06")
@StaticMetamodel(Audit.class)
public class Audit_ { 

    public static volatile SingularAttribute<Audit, Integer> id;
    public static volatile SingularAttribute<Audit, Integer> tableKey;
    public static volatile SingularAttribute<Audit, Integer> updateUid;
    public static volatile SingularAttribute<Audit, String> tableName;
    public static volatile SingularAttribute<Audit, Date> createTs;
    public static volatile SingularAttribute<Audit, Date> updateTs;
    public static volatile SingularAttribute<Audit, String> createIp;
    public static volatile SingularAttribute<Audit, String> updateIp;
    public static volatile SingularAttribute<Audit, Integer> createUid;
    public static volatile SingularAttribute<Audit, Short> deletedInd;
    public static volatile SingularAttribute<Audit, String> comments;

}
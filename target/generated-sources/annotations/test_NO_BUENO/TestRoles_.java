package test_NO_BUENO;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import test_NO_BUENO.TestUsers;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-01-20T11:32:59")
@StaticMetamodel(TestRoles.class)
public class TestRoles_ { 

    public static volatile SingularAttribute<TestRoles, Integer> id;
    public static volatile SingularAttribute<TestRoles, TestUsers> username;
    public static volatile SingularAttribute<TestRoles, String> role;

}
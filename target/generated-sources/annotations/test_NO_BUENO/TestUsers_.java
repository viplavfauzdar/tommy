package test_NO_BUENO;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import test_NO_BUENO.TestRoles;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-01-13T14:55:13")
@StaticMetamodel(TestUsers.class)
public class TestUsers_ { 

    public static volatile SingularAttribute<TestUsers, Integer> id;
    public static volatile SingularAttribute<TestUsers, String> username;
    public static volatile CollectionAttribute<TestUsers, TestRoles> testRolesCollection;

}
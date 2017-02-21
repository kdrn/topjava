package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

//    @Autowired
//    private static Environment env;
//
//    @BeforeClass
//    public static void check() throws Exception {
//        Assume.assumeTrue(isJdbc());
//    }
//
//    private static boolean isJdbc() {
//        return env.acceptsProfiles(Profiles.JDBC);
//    }
}
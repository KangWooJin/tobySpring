package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserDaoTest {

    @Autowired
    UserDao dao;
    @Autowired
    DataSource dataSource;

    private User user1;
    private User user2;
    private User user3;


    @Before
    public void setUp(){

//        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC",
//                                                               "root", "1234",true);
//        dao.setDataSource(dataSource);

        this.user1 = new User("gyumee", "박성철", "springgno1", Level.BASIC, 1, 0,"springTest@naver.com");
        this.user2 = new User("leegw700","이길원","springgno2", Level.SILVER, 55, 10,"springbook@naver.com");
        this.user3 = new User("bumjin","박범진","springgno3", Level.GOLD, 100, 40,"springbook2@naver.com");
    }
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));


        User userget1 = dao.get(user1.getId());
        checkSameUser(userget1, user1);

        User userget2 = dao.get(user2.getId());
        checkSameUser(userget2, user2);
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));
        dao.add(user2);
        assertThat(dao.getCount(), is(2));
        dao.add(user3);
        assertThat(dao.getCount(), is(3));

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("woojin");
    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        dao.deleteAll();

        List<User> user0 = dao.getAll();
        assertThat(user0.size(), is(0));
        dao.add(user1);
        List<User> user1 = dao.getAll();
        assertThat(user1.size(), is(1));
        checkSameUser(this.user1, user1.get(0));

        dao.add(user2);
        List<User> user2 = dao.getAll();
        assertThat(user2.size(), is(2));
        checkSameUser(this.user1, user2.get(0));
        checkSameUser(this.user2, user2.get(1));

        dao.add(user3);
        List<User> user3 = dao.getAll();
        assertThat(user3.size(), is(3));
        checkSameUser(this.user3, user3.get(0));
        checkSameUser(this.user1, user3.get(1));
        checkSameUser(this.user2, user3.get(2));

    }

    @Test(expected = DuplicateKeyException.class)
    public void duplciateKey() {
        dao.deleteAll();;

        dao.add(user1);
        dao.add(user1);
    }

    @Test
    public void sqlExceptionTranslate() {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch(DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(set.translate(null, null, sqlEx), is(DataAccessException.class));
        }
    }
    @Test
    public void update() {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);

        user1.setName("오민규");
        user1.setPassword("springno6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        user1.setEmail("test@naver.com");
        dao.update(user1);

        User user1update = dao.get(user1.getId());
        checkSameUser(user1, user1update);
        User user2same = dao.get(user2.getId());
        checkSameUser(user2same, user2);
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));

    }
}

package ejbs;

import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "UserEJB")
public class UserBean {

    @PersistenceContext
    EntityManager em;

    public User authenticate(final String email, final String password) throws
            Exception {
        System.out.println("USERBEAN --------------"+email);
        User user = em.find(User.class, email);
        if (user != null &&
                user.getPassword().equals(User.hashPassword(password))) {
            return user;
        }
        throw new Exception("Failed logging in with email: '" + email + "': unknown email or wrong password");
    }
}

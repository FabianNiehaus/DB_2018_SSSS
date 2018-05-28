public class LoginValidator {

    public static User checkUser(String email,String pass)
    {
        String mailCompare = "test";
        String passCompare = "pass";

        if (email.equals(mailCompare)){
            if (pass.equals(passCompare)){
                return new User("test", "pass");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}

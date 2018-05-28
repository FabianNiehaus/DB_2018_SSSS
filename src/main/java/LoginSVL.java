import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginSVL")
public class LoginSVL extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        HttpSession session = request.getSession();
        String userKey = "loggedInUser";
        User user = (User) session.getAttribute(userKey);

        if(user == null) {

            user = LoginValidator.checkUser(email, pass);

            if (user != null) {
                session.setAttribute(userKey, user);

                RequestDispatcher rs = request.getRequestDispatcher("welcome");
                rs.forward(request, response);
            } else {
                out.println("Username or Password incorrect");
                RequestDispatcher rs = request.getRequestDispatcher("");
                rs.include(request, response);
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet " + this.getServletName() + " has started");
    }
    @Override
    public void destroy() {
        System.out.println("Servlet " + this.getServletName() + " has stopped");
    }
}

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

@WebListener
public class AppSessionIdListener implements HttpSessionIdListener {

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        System.out.println("oldSessionId: " + oldSessionId);
        System.out.println("newSessionId: " + event.getSession().getId());

    }
}
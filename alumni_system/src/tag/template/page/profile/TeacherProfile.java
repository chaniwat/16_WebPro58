package tag.template.page.profile;

import model.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by meranote on 4/10/2016 AD.
 */
public class TeacherProfile extends SimpleTagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // TODO teacher profile form edit page
    }

}

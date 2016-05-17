package com.alumnisystem.page;

import com.alumnisystem.utility.RouteHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Style setting tag
 */
public class StyleTag extends BodyTagSupport {

    private JspWriter out;

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();
        PageTag pageTag = (PageTag) TagSupport.findAncestorWithClass(this, PageTag.class);

        if(pageTag == null) {
            throw new RuntimeException("<template:style> tag not in <template:page> tag");
        }

        try {
            out.println(
                    "<link href='" + RouteHelper.generateURL("assets/css/bootstrap.min.theme.css") + "' rel='stylesheet' />\n" +
                    (pageTag.isAdminPage() ? "<link href='https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.3.3/css/AdminLTE.min.css' rel='stylesheet' />\n" : "") +
                    "<link href='https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css' rel='stylesheet' />\n" +
                    "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\">\n"

            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            if(bodyContent != null) pageContext.getOut().print(bodyContent.getString());

            out.println(
                    "<link href='" + RouteHelper.generateURL("assets/fonts/ahtiti.css") + "' rel='stylesheet' />\n" +
                    "<link href='" + RouteHelper.generateURL("assets/css/style.css") + "' rel='stylesheet' />"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }
}

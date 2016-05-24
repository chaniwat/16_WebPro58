package com.alumnisystem.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Normal Page Template
 */
public class PageTag extends BodyTagSupport {

    private JspWriter out;

    private boolean adminPage = false;

    public boolean isAdminPage() {
        return adminPage;
    }

    public void setAdminPage(boolean adminPage) {
        this.adminPage = adminPage;
    }

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();

        try {
            out.println(
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n"
            );
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().print(bodyContent.getString());

            out.print(
                "</html>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }

}

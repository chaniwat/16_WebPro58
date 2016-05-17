package com.alumnisystem.page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Head HTML Tag
 */
public class HeadTag extends BodyTagSupport {

    private JspWriter out;

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();

        try {
            out.println(
                    "<head>\n" +
                        "<title>" + title + "</title>\n" +

                        "<meta charset='UTF-8' />\n" +
                        "<meta http-equiv='X-UA-Compatible' content='IE=edge' />\n" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1' />\n"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().print(bodyContent.getString());

            out.print(
                    "</head>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }
}

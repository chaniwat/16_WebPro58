package com.alumnisystem.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Body HTML Tag
 */
public class BodyTag extends BodyTagSupport {

    private JspWriter out;

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        PageTag pageTag = (PageTag) TagSupport.findAncestorWithClass(this, PageTag.class);

        if(pageTag == null) {
            throw new RuntimeException("<template:style> tag not in <template:page> tag");
        }

        try {
            out.println(
                    "<body class='fixed-navbar" + (pageTag.isAdminPage() ? " admin-page" : "") + "'>\n" +
                    "\n" +
                    "<sitedata contextPath=\"" + request.getContextPath() + "\"></sitedata>"
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

            out.println(
                    "</body>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }
}
package com.alumnisystem.tag;

import com.alumnisystem.utility.RouteHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Script tag
 */
public class ScriptTag extends BodyTagSupport {

    private JspWriter out;

    private boolean skipBootstrap = false;

    public boolean isSkipBootstrap() {
        return skipBootstrap;
    }

    public void setSkipBootstrap(boolean skipBootstrap) {
        this.skipBootstrap = skipBootstrap;
    }

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();

        PageTag pageTag = (PageTag) TagSupport.findAncestorWithClass(this, PageTag.class);

        if(pageTag == null) {
            throw new RuntimeException("<template:script> tag not in <template:page> tag");
        }

        try {
            out.println(
                    "<script src='https://code.jquery.com/jquery-2.2.0.min.js'></script>\n" +
                    (!skipBootstrap ? "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script>\n" +
                    (pageTag.isAdminPage() ? "<script src='https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.3.3/js/app.min.js'></script>\n" : "") : "")
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
                    "<script src='" + RouteHelper.generateURL("assets/js/script.js") + "'></script>\n"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }
}
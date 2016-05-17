package com.alumnisystem.page;

import com.alumnisystem.utility.RouteHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Admin Page Template
 */
public class PageAdmin extends BodyTagSupport {

    private JspWriter out;
    private HttpServletRequest request;

    private String title;
    private boolean skipBootstrapJsLoad = false;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSkipBootstrapJsLoad(boolean skipBootstrapJsLoad) {
        this.skipBootstrapJsLoad = skipBootstrapJsLoad;
    }

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();
        request = (HttpServletRequest) pageContext.getRequest();

        try {
            out.println(
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>" + title + "</title>\n" +
                    "<meta charset='UTF-8' />\n" +
                    "<meta http-equiv='X-UA-Compatible' content='IE=edge' />\n" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1' />\n" +
                    "<link href='" + RouteHelper.generateURL("assets/css/bootstrap.min.theme.css") + "' rel='stylesheet' />\n" +
                    "<link href='https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.3.3/css/AdminLTE.min.css' rel='stylesheet' />\n" +
                    "<link href='https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css' rel='stylesheet' />\n" +
                    "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\">\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.css\"/>\n" +
                    "<link href='" + RouteHelper.generateURL("assets/fonts/ahtiti.css") + "' rel='stylesheet' />\n" +
                    "<link href='" + RouteHelper.generateURL("assets/css/style.css") + "' rel='stylesheet' />\n" +
                    "</head>\n" +
                    "\n" +
                    "<body class='fixed-navbar admin-page'>\n" +
                    "\n" +
                    "<sitedata contextPath=\"" + request.getContextPath() + "\"></sitedata>"
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

            out.println("<script src='https://code.jquery.com/jquery-2.2.0.min.js'></script>");
            if(!skipBootstrapJsLoad) {
                out.println("" +
                        "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script>\n" +
                        "<script src='https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.3.3/js/app.min.js'></script>"
                );
            }
            out.print(
                    "<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.0.2/Chart.min.js'></script>\n" +
                    "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/holder/2.9.3/holder.min.js\"></script>\n" +
                    "<script type=\"text/javascript\" src=\"https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.js\"></script>\n" +
                    "<script src='" + RouteHelper.generateURL("assets/js/script.js") + "'></script>\n" +
                    "</body>\n" +
                    "</html>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }

}

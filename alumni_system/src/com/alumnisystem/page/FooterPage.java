package com.alumnisystem.page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by meranote on 4/19/2016 AD.
 */
public class FooterPage extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

        out.println(
                "<hr />\n" +
                "\n" +
                "        <footers>\n" +
                "            <p>คณะเทคโนโลยีสารสนเทศ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง<br>\n" +
                "                เลขที่ 1 ซอยฉลองกรุง 1 แขวงลาดกระบัง เขตลาดกระบัง กรุงเทพมหานคร 10520<br>\n" +
                "                โทรศัพท์ +66 (0) 2723 4900 โทรสาร +66 (0) 2723 4910</p>\n" +
                "        </footers>"
        );
    }

}

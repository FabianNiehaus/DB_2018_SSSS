package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class game_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      response.setHeader("X-Powered-By", "JSP/2.3");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<html>\n");
      out.write("<!-- See this live on http://jsfiddle.net/FloydPink/KHLtw/ -->\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\"/>\n");
      out.write("    <title>Buzzword Bingo</title>\n");
      out.write("    <link rel=stylesheet type=\"text/css\" href=\"css/stylesheet.css\">\n");
      out.write("    <script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.7.js\"></script>\n");
      out.write("    <script src=\"js/main.js\" type=\"text/javascript\"></script>\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<header>\n");
      out.write("    <h1 class=\"h1\">Buzzword Bingo</h1>\n");
      out.write("</header>\n");
      out.write("\n");
      out.write("<div class=\"container\">\n");
      out.write("    <div class=\"bigNumberDisplay\">\n");
      out.write("        <span>0</span>\n");
      out.write("    </div>\n");
      out.write("    <div>\n");
      out.write("        <input id=\"btnGenerate\" type=\"button\" value=\"Generate\"/>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <br/>\n");
      out.write("\n");
      out.write("    <button type=\"submit\" onclick=\"connect()\" style=\"color: green\" id=\"connect\">verbinden</button>\n");
      out.write("    <button type=\"submit\" onclick=\"disconnect()\" style=\"color: red\" id=\"disconnect\" disabled>trennen</button>\n");
      out.write("\n");
      out.write("    <hr/>\n");
      out.write("\n");
      out.write("    <br/>\n");
      out.write("    <textarea cols=\"50\" rows=\"10\" disabled id=\"out\"></textarea>\n");
      out.write("\n");
      out.write("    <br/>\n");
      out.write("    <div class=\"playfield\">\n");
      out.write("\n");
      out.write("        <table class=\"table-grid\">\n");
      out.write("\n");
      out.write("            <table class=\"left\" style=\"float:left;width:25%;\">\n");
      out.write("                <tr>\n");
      out.write("                    <td>LEFT</td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("\n");
      out.write("            <table class=\"middle\">\n");
      out.write("\n");
      out.write("                <div class=\"numbersTable\">\n");
      out.write("                    <table>\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"table 11\">A</td>\n");
      out.write("                            <td class=\"table 12\">A</td>\n");
      out.write("                            <td class=\"table 13\">A</td>\n");
      out.write("                            <td class=\"table 14\">A</td>\n");
      out.write("                            <td class=\"table 15\">A</td>\n");
      out.write("\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"table 21\">B</td>\n");
      out.write("                            <td class=\"table 22\">B</td>\n");
      out.write("                            <td class=\"table 23\">B</td>\n");
      out.write("                            <td class=\"table 24\">B</td>\n");
      out.write("                            <td class=\"table 25\">B</td>\n");
      out.write("\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"table 31\">c</td>\n");
      out.write("                            <td class=\"table 32\">c</td>\n");
      out.write("                            <td class=\"table 33\">c</td>\n");
      out.write("                            <td class=\"table 34\">c</td>\n");
      out.write("                            <td class=\"table 35\">c</td>\n");
      out.write("\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"table 41\">D</td>\n");
      out.write("                            <td class=\"table 42\">D</td>\n");
      out.write("                            <td class=\"table 43\">D</td>\n");
      out.write("                            <td class=\"table 44\">D</td>\n");
      out.write("                            <td class=\"table 45\">D</td>\n");
      out.write("\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"table 51\">E</td>\n");
      out.write("                            <td class=\"table 52\">E</td>\n");
      out.write("                            <td class=\"table 53\">E</td>\n");
      out.write("                            <td class=\"table 54\">E</td>\n");
      out.write("                            <td class=\"table 55\">E</td>\n");
      out.write("\n");
      out.write("                        </tr>\n");
      out.write("\n");
      out.write("                    </table>\n");
      out.write("\n");
      out.write("                    <table class=\"right\" style=\"float:right;width:25%;\">\n");
      out.write("                        <tr>\n");
      out.write("                            <td>RIGHT</td>\n");
      out.write("                        </tr>\n");
      out.write("                    </table>\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("                <table style=\"float:right;width:25%;\" border=\"1\">\n");
      out.write("                </table>\n");
      out.write("            </table>\n");
      out.write("        </table>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

package servlets;

import customer.Customer;
import customer.CustomerUtility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Meranote on 1/20/2016.
 */
@WebServlet(name = "CustomerServlet", urlPatterns ="/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String function = request.getParameter("function");
        switch (function) {
            case "addCustomer": addCustomer(request, response); break;
            case "findCustomerByID": findCustomerByID(request, response); break;
            case "findCustomerByName": findCustomerByName(request, response); break;
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/showCustomer.jsp");
        dispatcher.forward(request, response);
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int customerId = CustomerUtility.addCustomer(new Customer(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("company"),
                request.getParameter("mobile"),
                request.getParameter("email"),
                request.getParameter("addr")
        ));

        request.setAttribute("newcustomer", CustomerUtility.getCustomer(customerId));
    }

    private void findCustomerByID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("newcustomer", CustomerUtility.getCustomer(
                Integer.parseInt(request.getParameter("id"))
        ));
    }

    private void findCustomerByName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("newcustomer", CustomerUtility.getCustomer(
                request.getParameter("firstName"),
                request.getParameter("lastName")
        ));
    }
}

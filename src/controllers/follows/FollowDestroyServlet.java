package controllers.follows;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follows/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            Follow f = new Follow();

            f =em.createNamedQuery("getFollowData", Follow.class)
                .setParameter("employee" ,Integer.parseInt(request.getParameter("emp_id")))
                .setParameter("logid", login_employee.getId())
                .getSingleResult();

            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/reports/index");
            }
    }
}
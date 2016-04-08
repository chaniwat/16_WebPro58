package controller;

import model.Address;
import model.Alumni;
import model.User;
import model.auth.Authorization;
import model.utility.ResponseCodeUtils;
import model.utility.RouteUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by meranote on 4/7/2016 AD.
 */
@WebServlet(name = "EditProfileServlet", urlPatterns = {"/profile/edit"})
public class EditProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User.UserType type = User.UserType.valueOf(request.getParameter("usertype"));
        switch (type) {
            case ALUMNI: doUpdateAlumni(request, response); return;
        }
    }

    private void doUpdateAlumni(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> params = RouteUtils.convertParamsToHashMap(request, "alumni-form-");

        if(params.get("alumni-form-stuid") == null || params.get("alumni-form-pnameth") == null ||
                params.get("alumni-form-fnameth") == null || params.get("alumni-form-lnameth") == null) {
            redirectToProfilePage(request, response, ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE);
            return;
        }

        // Check permission (prevent another alumni or teacher to edit another alumnis' profile)
        Authorization auth = Authorization.getAuthInstance(request.getSession());
        User user = auth.getCurrentUser();

        int student_id = Integer.parseInt(params.get("alumni-form-stuid"));

        if((user.getType() == User.UserType.TEACHER) ||
                (user.getType() == User.UserType.ALUMNI && Alumni.getAlumniByUserId(user.getId()).getStudent_id() != student_id)) {
            redirectToProfilePage(request, response, ResponseCodeUtils.NOT_ENOUGH_PERMISSION);
            return;
        }

        Alumni alumni = new Alumni();
        alumni.setStudent_id(student_id);
        alumni.setPname_th(params.get("alumni-form-pnameth"));
        alumni.setFname_th(params.get("alumni-form-fnameth"));
        alumni.setLname_th(params.get("alumni-form-lnameth"));
        alumni.setPname_en(params.get("alumni-form-pnameen"));
        alumni.setFname_en(params.get("alumni-form-fnameen"));
        alumni.setLname_en(params.get("alumni-form-lnameen"));
        alumni.setNickname(params.get("alumni-form-nickname"));

        if(params.get("alumni-form-birthdate-year") != null &&
                params.get("alumni-form-birthdate-month") != null &&
                params.get("alumni-form-birthdate-day") != null) {

            String birthdate = String.format("%04d", Integer.parseInt(params.get("alumni-form-birthdate-year"))) + "-"
                    + String.format("%02d", Integer.parseInt(params.get("alumni-form-birthdate-month"))) + "-"
                    + String.format("%02d", Integer.parseInt(params.get("alumni-form-birthdate-day")));
            try {
                alumni.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(birthdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            alumni.setBirthdate(null);
        }

        alumni.setEmail(params.get("alumni-form-email"));
        alumni.setPhone(params.get("alumni-form-phone"));
        alumni.setOccupation(params.get("alumni-form-occupation"));
        alumni.setWork_name(params.get("alumni-form-workname"));

        alumni.getAddress().setAddress(params.get("alumni-form-address"));
        alumni.getAddress().setDistrict(params.get("alumni-form-district"));
        alumni.getAddress().setAmphure(params.get("alumni-form-district"));

        if(params.get("alumni-form-province") != null) {
            alumni.getAddress().setProvince(Address.Province.getProvinceByProvinceId(Integer.parseInt(params.get("alumni-form-province"))));
        } else {
            alumni.getAddress().setProvince(null);
        }

        alumni.getAddress().setZipcode(params.get("alumni-form-zipcode"));

        Alumni.updateAlumni(alumni);

        redirectToProfilePage(request, response, ResponseCodeUtils.PROFILE_UPDATED_COMPLETE);
        return;
    }

    private void redirectToProfilePage(HttpServletRequest request, HttpServletResponse response, int ErrorNumber) {
        try {
            ResponseCodeUtils.pushSessionCode(request.getSession(), ErrorNumber);
            response.sendRedirect(RouteUtils.generateURL(request, "profile"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

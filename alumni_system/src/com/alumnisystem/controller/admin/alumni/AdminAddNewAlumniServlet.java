package com.alumnisystem.controller.admin.alumni;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.exception.AlumniNotFound;
import com.alumnisystem.factory.AlumniFactory;
import com.alumnisystem.factory.JobFactory;
import com.alumnisystem.factory.JobTypeFactory;
import com.alumnisystem.factory.TrackFactory;
import com.alumnisystem.model.Alumni;
import com.alumnisystem.model.Job;
import com.alumnisystem.model.JobType;
import com.alumnisystem.model.Track;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@WebServlet(name = "AdminAddNewAlumniServlet", urlPatterns = {"/admin/alumni/add", "/admin/alumni/add/"})
@AuthGuard
public class AdminAddNewAlumniServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(request.getParameter("mode").equals("FIND")) {
            HashMap<String, String> params = RouteHelper.convertParamsToHashMap("alumni-find-form-");

            if(params.get("alumni-find-form-pnameth") == null || params.get("alumni-find-form-fnameth") == null || params.get("alumni-find-form-lnameth") == null) {
                ResponseHelper.pushRequestCode(ResponseHelper.FORM_INPUT_NOT_COMPLETE);
                request.getRequestDispatcher("/WEB-INF/admin/alumni/formnewfindalumni.jsp").forward(request, response);
                return;
            }
            try {
                Alumni alumni = new AlumniFactory().findByNameTH(
                        params.get("alumni-find-form-pnameth"),
                        params.get("alumni-find-form-fnameth"),
                        params.get("alumni-find-form-lnameth"));

                request.setAttribute("alumni", alumni);
                request.getRequestDispatcher("/WEB-INF/admin/alumni/formnewtrack.jsp").forward(request, response);
            } catch (AlumniNotFound ignored) {
                Alumni alumni = new Alumni();

                alumni.setPname_th(params.get("alumni-find-form-pnameth"));
                alumni.setFname_th(params.get("alumni-find-form-fnameth"));
                alumni.setLname_th(params.get("alumni-find-form-lnameth"));

                request.getSession().setAttribute("admin.alumni.new", alumni);
                request.getRequestDispatcher("/WEB-INF/admin/alumni/formnewalumni.jsp").forward(request, response);
            }
        } else if(request.getParameter("mode").equals("CREATE")) {
            HashMap<String, String> params = RouteHelper.convertParamsToHashMap("alumni-form-");

            Alumni alumni = (Alumni) request.getSession().getAttribute("admin.alumni.new");

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

            request.getSession().setAttribute("admin.alumni.jobtype", params.get("alumni-form-jobtype"));
            request.getSession().setAttribute("admin.alumni.jobname", params.get("alumni-form-jobname"));
            request.getSession().setAttribute("admin.alumni.jobtypeother", params.get("alumni-form-jobtypeother"));
            request.getSession().setAttribute("admin.alumni.jobnameother", params.get("alumni-form-jobnameother"));

            alumni.setWork_name(params.get("alumni-form-workname"));

            alumni.setAddress(params.get("alumni-form-address"));
            alumni.setDistrict(params.get("alumni-form-district"));
            alumni.setAmphure(params.get("alumni-form-amphure"));
            alumni.setProvince(params.get("alumni-form-province"));
            alumni.setZipcode(params.get("alumni-form-zipcode"));

            request.setAttribute("alumni", alumni);
            request.getRequestDispatcher("/WEB-INF/admin/alumni/formnewtrack.jsp").forward(request, response);
        } else if(request.getParameter("mode").equals("FINISH")) {
            HashMap<String, String> params = RouteHelper.convertParamsToHashMap("alumni-track-");

            Alumni alumni;
            if(request.getParameter("alumni-id").equals("0")) {
                alumni = (Alumni) request.getSession().getAttribute("admin.alumni.new");

                JobTypeFactory jobTypeFactory = new JobTypeFactory();
                JobFactory jobFactory = new JobFactory();

                String jobtype = (String) request.getSession().getAttribute("admin.alumni.jobtype"),
                        jobname = (String) request.getSession().getAttribute("admin.alumni.jobname"),
                        jobtypeother = (String) request.getSession().getAttribute("admin.alumni.jobtypeother"),
                        jobnameother = (String) request.getSession().getAttribute("admin.alumni.jobnameother");

                request.getSession().setAttribute("admin.alumni.jobtype", null);
                request.getSession().setAttribute("admin.alumni.jobname", null);
                request.getSession().setAttribute("admin.alumni.jobtypeother", null);
                request.getSession().setAttribute("admin.alumni.jobnameother", null);

                Job job = null;
                if(jobname != null) {
                    if(jobname.equals("0")) {
                        JobType jobType;
                        if(jobtype.equals("0")) {
                            jobType = new JobType();
                            jobType.setName_th(jobtypeother);

                            jobType = jobTypeFactory.create(jobType);
                        } else {
                            jobType = jobTypeFactory.find(Integer.parseInt(jobtype));
                        }

                        job = new Job();
                        job.setName_th(jobnameother);
                        job.setJobType(jobType);

                        jobFactory.create(job);
                    } else {
                        job = jobFactory.find(Integer.parseInt(jobname));
                    }
                }
                alumni.setJob(job);

                alumni.getTracks().add(fetchAlumniTrack(params));
                new AlumniFactory().create(alumni);

                request.getSession().setAttribute("admin.alumni.new", null);
                ResponseHelper.pushRequestCode(ResponseHelper.ADD_NEW_ALUMNI_COMPLETE);
            } else {
                alumni = new AlumniFactory().find(Integer.parseInt(request.getParameter("alumni-id")));
                new AlumniFactory().addTrack(alumni, fetchAlumniTrack(params));
                ResponseHelper.pushRequestCode(ResponseHelper.ADD_NEW_ALUMNITRACK_COMPLETE);
            }

            request.getRequestDispatcher("/WEB-INF/admin/alumni/formnewfindalumni.jsp").forward(request, response);
        }
    }

    private Track fetchAlumniTrack(HashMap<String, String> params) {
        Track track = new TrackFactory().find(Integer.parseInt(params.get("alumni-track-trackid")));
        track.setStudent_id(Integer.parseInt(params.get("alumni-track-student_id")));
        track.setGeneration(Integer.parseInt(params.get("alumni-track-generation")));
        track.setStarteduyear(Integer.parseInt(params.get("alumni-track-starteduyear")));
        track.setEndeduyear(Integer.parseInt(params.get("alumni-track-endeduyear")));

        return track;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/admin/alumni/formnewfindalumni.jsp").forward(request, response);
    }
}

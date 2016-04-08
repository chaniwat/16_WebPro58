package model.utility.tester;

import annotation.test;
import model.Address;
import model.Alumni;
import model.Track;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meranote on 4/6/2016 AD.
 */
public class AlumniModelTester {

    public static void main(String[] args) {
        testAddAlumni();
    }

    @test
    public static void testAddAlumni() {
        Alumni alumni = new Alumni();

        alumni.setStudent_id(57070029);
        alumni.setPname_th("นาย");
        alumni.setFname_th("ชานิวัฒน์");
        alumni.setLname_th("แสงไชย");

        Track track = Track.getTrack(4);
        track.setStarteduyear(2014);
        track.setEndeduyear(2019);

        Address address = new Address();
        address.setProvince(Address.Province.getProvinceByProvinceId(1));
        alumni.setAddress(address);

        alumni.getTracks().add(track);

        Alumni.addNewAlumni(alumni);
    }

    @test
    public static void testPrintAllAlumni() {
        ArrayList<Alumni> alumnis = Alumni.getAllAlumni();

        for(Alumni alumni : alumnis) {
            System.out.println(alumni.getPname_th() + " " + alumni.getFname_th() + " " + alumni.getLname_th());
            System.out.println(alumni.getAddress().getAmphure());
            for(Track track : alumni.getTracks()) {
                System.out.println(track.getName_th() + " " + track.getStarteduyear());
            }
        }
    }

    @test
    public static void testUpdateAlumni() {
        Alumni alumni = Alumni.getAlumniByStudentId(57070001);



        Alumni.updateAlumni(alumni);
    }

    @test
    public static void testUpdateBirthdateAlumni() {
        Alumni alumni = Alumni.getAlumniByStudentId(57070001);

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d = ft.parse("1995-04-01");
            alumni.setBirthdate(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Alumni.updateAlumni(alumni);
    }

}

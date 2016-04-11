package tester;

import annotation.test;
import model.Alumni;
import model.Province;
import model.Track;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meranote on 4/6/2016 AD.
 */
public class AlumniModelTester {

    @test
    public static void main(String[] args) {
        testAddAlumni();
    }

    @test
    public static void testAddAlumni() {
        Alumni alumni = new Alumni();

        alumni.setPname_th("นาย");
        alumni.setFname_th("ชานิวัฒน์");
        alumni.setLname_th("แสงไชย");

        Alumni.Address address = new Alumni.Address();
        address.setProvince(Province.getProvinceByProvinceId(1));
        alumni.setAddress(address);

        Alumni.Track track1 = new Alumni.Track();
        track1.setStudent_id(57070029);
        track1.setGeneration(12);
        track1.setTrack(Track.getTrack(1));
        track1.setStarteduyear(2014);
        track1.setEndeduyear(2019);
        alumni.getTracks().add(track1);

//        Alumni.Track track2 = new Alumni.Track();
//        track2.setStudent_id(60607029);
//        track2.setGeneration(7);
//        track2.setTrack(Track.getTrack(1));
//        track2.setStarteduyear(2018);
//        track2.setEndeduyear(2020);
//        alumni.getTracks().add(track2);

        Alumni.addAlumni(alumni);
    }

    @test
    public static void testPrintAlumni() {
        Alumni alumni = Alumni.getAlumniByStudentId(57070029);

        System.out.println(alumni.getPname_th() + alumni.getFname_th() + " " + alumni.getLname_th());

        for(Alumni.Track track : alumni.getTracks()) {
            System.out.println(track.getStudent_id() + " " + track.getGeneration() + " " + track.getTrack().getName_th() + " " + track.getTrack().getCurriculum().getName_th());
        }
    }

    @test
    public static void testPrintAllAlumni() {
        ArrayList<Alumni> alumnis = Alumni.getAllAlumni();

        for(Alumni alumni : alumnis) {
            System.out.println(alumni.getPname_th() + " " + alumni.getFname_th() + " " + alumni.getLname_th());
            System.out.println(alumni.getAddress().getAmphure());
            for(Alumni.Track track : alumni.getTracks()) {
                System.out.println(track.getTrack().getName_th() + " " + track.getStarteduyear());
            }
        }
    }

    @test
    public static void testRemoveAlumni() {
        Alumni.removeAlumniByStudentId(57070029);
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

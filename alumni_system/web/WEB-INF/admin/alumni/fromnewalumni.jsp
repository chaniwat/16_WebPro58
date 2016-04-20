<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

<template:pageadmin>

    <div class="container-admin">

        <div class="page-header">
            <h1>เพิ่มศิษย์เก่า</h1>
        </div>

        <div class="container">

            <form action="" method="POST" id="alumni-form" class="form-horizontal" onsubmit="javascript:;">
                <input type="hidden" id="profilepage-usertype" name="profilepage-usertype" value="ALUMNI">
                <input type="hidden" id="alumni-form-id" name="alumni-form-id" value="0">
                <div class="form-group">
                    <label for="alumni-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)*</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-pnameth" id="alumni-form-pnameth" placeholder="Title (TH)" value="" data-empty="false" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)*</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-fnameth" id="alumni-form-fnameth" placeholder="Name (TH)" value="" data-empty="false" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)*</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-lnameth" id="alumni-form-lnameth" placeholder="Surname (TH)" value="" data-empty="false" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-pnameen" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-pnameen" id="alumni-form-pnameen" placeholder="Title (EN)" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-fnameen" class="col-md-3 control-label">ชื่อ (ภาษาอังกฤษ)</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-fnameen" id="alumni-form-fnameen" placeholder="Name (EN)" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-lnameen" class="col-md-3 control-label">นามสกุล (ภาษาอังกฤษ)</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-lnameen" id="alumni-form-lnameen" placeholder="Surname (EN)" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-nickname" class="col-md-3 control-label">ชื่อเล่น</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-nickname" id="alumni-form-nickname" placeholder="Nickname" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-birthdate" class="col-md-3 control-label">วันเกิด</label>
                    <div class="col-md-9" id="alumni-form-birthdate">
                        <div class="row">
                            <div class="col-md-3">
                                <select class="form-control" name="alumni-form-birthdate-year" id="alumni-form-birthdate-year" disabled="disabled">
                                    <option value="null">Year</option><option value="2016">2016</option><option value="2015">2015</option><option value="2014">2014</option><option value="2013">2013</option><option value="2012">2012</option><option value="2011">2011</option><option value="2010">2010</option><option value="2009">2009</option><option value="2008">2008</option><option value="2007">2007</option><option value="2006">2006</option><option value="2005">2005</option><option value="2004">2004</option><option value="2003">2003</option><option value="2002">2002</option><option value="2001">2001</option><option value="2000">2000</option><option value="1999">1999</option><option value="1998">1998</option><option value="1997">1997</option><option value="1996">1996</option><option value="1995">1995</option><option value="1994">1994</option><option value="1993">1993</option><option value="1992">1992</option><option value="1991">1991</option><option value="1990">1990</option><option value="1989">1989</option><option value="1988">1988</option><option value="1987">1987</option><option value="1986">1986</option><option value="1985">1985</option><option value="1984">1984</option><option value="1983">1983</option><option value="1982">1982</option><option value="1981">1981</option><option value="1980">1980</option><option value="1979">1979</option><option value="1978">1978</option><option value="1977">1977</option><option value="1976">1976</option><option value="1975">1975</option><option value="1974">1974</option><option value="1973">1973</option><option value="1972">1972</option><option value="1971">1971</option><option value="1970">1970</option><option value="1969">1969</option><option value="1968">1968</option><option value="1967">1967</option><option value="1966">1966</option><option value="1965">1965</option><option value="1964">1964</option><option value="1963">1963</option><option value="1962">1962</option><option value="1961">1961</option><option value="1960">1960</option><option value="1959">1959</option><option value="1958">1958</option><option value="1957">1957</option><option value="1956">1956</option><option value="1955">1955</option><option value="1954">1954</option><option value="1953">1953</option><option value="1952">1952</option><option value="1951">1951</option><option value="1950">1950</option><option value="1949">1949</option><option value="1948">1948</option><option value="1947">1947</option><option value="1946">1946</option><option value="1945">1945</option><option value="1944">1944</option><option value="1943">1943</option><option value="1942">1942</option><option value="1941">1941</option><option value="1940">1940</option><option value="1939">1939</option><option value="1938">1938</option><option value="1937">1937</option><option value="1936">1936</option><option value="1935">1935</option><option value="1934">1934</option><option value="1933">1933</option><option value="1932">1932</option><option value="1931">1931</option><option value="1930">1930</option><option value="1929">1929</option><option value="1928">1928</option><option value="1927">1927</option><option value="1926">1926</option><option value="1925">1925</option><option value="1924">1924</option><option value="1923">1923</option><option value="1922">1922</option><option value="1921">1921</option><option value="1920">1920</option><option value="1919">1919</option><option value="1918">1918</option><option value="1917">1917</option><option value="1916">1916</option><option value="1915">1915</option><option value="1914">1914</option><option value="1913">1913</option><option value="1912">1912</option><option value="1911">1911</option><option value="1910">1910</option><option value="1909">1909</option><option value="1908">1908</option><option value="1907">1907</option><option value="1906">1906</option><option value="1905">1905</option><option value="1904">1904</option><option value="1903">1903</option><option value="1902">1902</option><option value="1901">1901</option><option value="1900">1900</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <select class="form-control" name="alumni-form-birthdate-month" id="alumni-form-birthdate-month" disabled="disabled">
                                    <option value="null">Month</option><option value="1">มกราคม</option><option value="2">กุมภาพันธ์</option><option value="3">มีนาคม</option><option value="4">เมษายน</option><option value="5">พฤษภาคม</option><option value="6">มิถุนายน</option><option value="7">กรกฎาคม</option><option value="8">สิงหาคม</option><option value="9">กันยายน</option><option value="10">ตุลาคม</option><option value="11">พฤศจิกายน</option><option value="12">ธันวาคม</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <select class="form-control" name="alumni-form-birthdate-day" id="alumni-form-birthdate-day" disabled="disabled"><option value="null">Day</option></select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-address" class="col-md-3 control-label">ที่อยู่</label>
                    <div class="col-md-9">
                        <textarea class="form-control" name="alumni-form-address" id="alumni-form-address" placeholder="Address" readonly="readonly"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-district" class="col-md-3 control-label">ตำบล/แขวง</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-district" id="alumni-form-district" placeholder="District" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-amphure" class="col-md-3 control-label">อำเภอ/เขต</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-amphure" id="alumni-form-amphure" placeholder="Amphure" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-province" class="col-md-3 control-label">จังหวัด</label>
                    <div class="col-md-9">
                        <select class="form-control" name="alumni-form-province" id="alumni-form-province" data-default="2" disabled="disabled">
                            <option value="null">Province</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-zipcode" class="col-md-3 control-label">รหัสไปรษณีย์</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-zipcode" id="alumni-form-zipcode" placeholder="Zipcode" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-email" class="col-md-3 control-label">อีเมลล์</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-email" id="alumni-form-email" placeholder="Email" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-phone" class="col-md-3 control-label">เบอร์โทรศัพท์</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-phone" id="alumni-form-phone" placeholder="Phone number" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-occupation" class="col-md-3 control-label">อาชีพ</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-occupation" id="alumni-form-occupation" placeholder="Occupation" value="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label for="alumni-form-workname" class="col-md-3 control-label">ชื่อบริษัท</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" name="alumni-form-workname" id="alumni-form-workname" placeholder="Company name" value="" b="" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-9">
                        <button type="button" id="alumni-form-btn" class="btn btn-primary">ขั้นตอนต่อไป »</button>
                    </div>
                </div>
            </form>

        </div>

    </div>

</template:pageadmin>

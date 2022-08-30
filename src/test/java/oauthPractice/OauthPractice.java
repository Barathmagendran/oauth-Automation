package oauthPractice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static  io.restassured.RestAssured.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

public class OauthPractice {
	static 	String[] s1 ;
	static String token;
		@Test(priority=1)
	public void code() {
	String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qi8Jej3JPSmtwFFOcBjGuXAycUxGtTC47Z4xqkMSA4mez5kf0CcpbKDlGInIxU_KQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
	
	String[] s = url.split("code=");
	 s1 = s[1].split("&scope=");
		}
		@Test(priority=2)
public void accessToken() {
	//access token
	String tokenAccess = given().log().all().queryParam("code",s1[0])
	.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
	.queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
	.queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
	.queryParam("grant_type","authorization_code").urlEncodingEnabled(false)
	.when().post("https://www.googleapis.com/oauth2/v4/token")
	.then().extract().response().asString();
	JsonPath j=new JsonPath(tokenAccess);
	 token = j.get("access_token");
	 System.out.println(token);
}
		@Test(priority=3)
public void getCourses() {
String courses=given().log().all().queryParam("access_token",token).headers("Content-Type","application/json")
.when().get("https://rahulshettyacademy.com/getCourse.php")
.then().assertThat().statusCode(200).extract().response().asString();
System.out.println(courses);
JsonPath j=new JsonPath(courses);
int len = j.get("courses.webAutomation.size()");
for(int i=0;i<len;i++) {
String web = j.get("courses.webAutomation["+i+"].courseTitle");
System.out.println("webAutomation Courses");
System.out.println(web);
}
int len1 = j.get("courses.api.size()");
for(int k=0;k<len1;k++) {
String api = j.get("courses.api["+k+"].courseTitle");
System.out.println("Api Courses");
System.out.println(api);
}
int len2 = j.get("courses.mobile.size()");
for(int m=0;m<len2;m++) {
String mobile = j.get("courses.mobile["+m+"].courseTitle");
System.out.println("Mobile courses");
System.out.println(mobile);
}






	}

}

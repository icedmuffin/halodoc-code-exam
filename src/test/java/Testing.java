import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Testing {

    //check Create booking test data
    String url = "https://restful-booker.herokuapp.com/booking";
    String firstname = "naufal";
    String lastname = "sunandar";
    int totalPrice = 111;
    boolean isDepositPaid = true;


    public Response postBooking(
            String firstname,
            String lastname,
            int totalprice,
            boolean depositpaid
    ){

        Response result = given()
                .contentType("application/json")
                .body("{\"firstname\" : \""+firstname+"\",\"lastname\" : \""+lastname+"\",\"totalprice\" : "+totalprice+",\"depositpaid\" : "+depositpaid+",\"bookingdates\" : {\"checkin\" : \"2018-01-01\",\n" +
                        "\"checkout\" : \"2019-01-01\"},\"additionalneeds\" : \"Breakfast\"}")
                .post(url);

        System.out.println(result.asPrettyString());

        return result;
    }



    public Response getBookingData(String id){
        Response result = given()
                .get(url+"/"+id);
        System.out.println(result.asPrettyString());

        return result;

    }

    @Test
    public void checkCreateBooking(){
        System.out.println("Starting test checkCreateBooking ....");
        Response result = postBooking(firstname, lastname, 111, isDepositPaid);
        Assert.assertEquals(200, result.getStatusCode());

        int bookingId = result.body().jsonPath().get("bookingid");
        System.out.println("-----test----");
        System.out.println(bookingId);
        Response result2 = getBookingData(String.valueOf(bookingId));
        Assert.assertEquals(200, result2.getStatusCode());
        Assert.assertEquals(lastname,result2.body().jsonPath().get("lastname"));
    }
}

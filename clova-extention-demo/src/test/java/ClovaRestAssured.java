import org.junit.Test;

import static io.restassured.RestAssured.given;


// Api 테스트 코드
public class ClovaRestAssured {

    @Test
    public void naverShouldBeOK() {
        given().when().get("clova/v1/hello").then().statusCode(200);
    }

    private final String LaunchRequest = "LaunchRequest";
    private final String IntentRequest = "IntentRequest";
    private final String SessionEndedRequest = "SessionEndedRequest";
    private final String INTENT_NAME = "turnOnIntent";
    private final String SLOT_NAME = "homeSlotType";

    @Test
    public void extensionShouldReturnLaunchRequest() {
        given().header("Content-Type", "application/json")
                .body(getClovaJson(LaunchRequest, INTENT_NAME, SLOT_NAME))
                .when().post("/clova/extension")
                .then().body("response.outputSpeech.values.value", containsString("안녕"));
    }

    @Test
    public void extensionShouldReturnIntentRequest() {
        given().header("Content-Type", "application/json")
                .body(getClovaJson(IntentRequest, INTENT_NAME, SLOT_NAME))
                .when().post("/clova/extension")
                .then().body("response.outputSpee ch.values.value", containsString("전등"));
    }

    @Test

    public void extensionShouldReturnEndRequest() {
        given().header("Content-Type", "application/json")
                .body(getClovaJson(SessionEndedRequest, INTENT_NAME, SLOT_NAME))
                .when().post("/clova/extension")
                .then().body("response.outputSpeech.values.value", containsString("종료"));
    }

    private String getClovaJson(String type, String intentName, String slotName) {
        String jsonReq = "{\n" +
                "   \"version\": \"0.1.0\",\n" +
                "   \"session\": {\n" +
                "       \"sessionId\": \"ff5db3a3-0f67-4b24-830f-cfa785556a71\",\n" +
                "       \"user\": {\n" +
                "           \"userId\": \"azvjPlQkSCuV8LmItY2VrQ\",\n" +
                "           \"accessToken\": \"66e7e019-cda9-4826-acdd-b1671b551e97\"\n" +
                "       },\n" +
                "       \"new\": true\n" +
                "   },\n" +
                "   \"context\": {\n" +
                "       \"System\": {\n" +
                "           \"user\": {\n" +
                "               \"userId\": \"azvjPlQkSCuV8LmItY2VrQ\",\n" +
                "               \"accessToken\": \"66e7e019-cda9-4826-acdd-b1671b551e97\"\n" +
                "           },\n" +
                "           \"device\": {\n" +
                "               \"deviceId\": \"4a0307d4-0674-4cdb-890e-be29f049339d\"\n" +
                "           }\n" +
                "       }\n" +
                "   },\n" +
                "   \"request\": {\n" +
                "   \"type\": \"" + type + "\",\n" +
                "       \"intent\": {\n" +
                "           \"name\": \"" + intentName + "\",\n" +
                "           \"slots\": {\n" +
                "               \"" + slotName + "\": {\n" +
                "                   \"name\": \"" + slotName + "\",\n" +
                "                   \"value\": \"거실\"\n" +
                "               }\n" +
                "           }\n" +
                "       }\n" +
                "   }\n" +
                "}";

        return jsonReq;
    }
}

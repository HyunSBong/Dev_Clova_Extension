package com.example.clova.clovaextentiondemo.controller;

import com.example.clova.clovaextentiondemo.message.MyExtensionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clova")
public class ClovaExtentionDemoController {

    @ResponseBody
    @RequestMapping(value = "v1/hello", method = RequestMethod.GET, produces = "application/json")
    public String hello() {
        return "hello clova api";
    }

    @RequestMapping(value = "/extension", method= RequestMethod.POST, produces = "application/json" )
    @ResponseBody
    public ResponseEntity<MyExtensionMessage> weather (Map<String, Object> map) {
        Map m = (HashMap)map.get("request"); // 파싱해서 LaunchRequest 인지 IntentRequest 인지 SessionEndedRequest 인지 확인
        String type = (String) m.get("type");
        MyExtensionMessage mm = null;

        if(type.equals("LaunchRequest")) { // extension 시작
            mm = new MyExtensionMessage("turnOnIntent", "전등 도우미를 시작합니다. 제어하려는 전등을 말씀해주세요. ", false, "PlainText");
        }
        else if (type.equals("IntentRequest")) { // extension의 인텐트 시작
            Map intent = (HashMap) m.get("intent");
            String intentName = (String) intent.get("name");
            Map slots = (HashMap) intent.get("slots"); // slot
            String slotName = "";
            String slotValue = "";

            if (intentName.equals("turnOnIntent")) {
                if (slots != null) {
                    Map myslot = (HashMap) slots.get("homeSlotType");
                    slotName = (String) myslot.get("name");
                    slotValue = (String) myslot.get("value");
                    System.out.println("slotName===" + slotName);
                    System.out.println("slotValue===" + slotValue);
                }
                mm= new MyExtensionMessage("turnOnIntent", slotValue + "의 전등을 켰습니다. ", true, "PlainText");
                // Built-in Intent 처리
            }
            else if (intentName.equals("Clova.YesIntent")) { // 명령 확인
                mm = new MyExtensionMessage(intentName, "예 라고 하셨나요?", true, "PlainText");
            }
            else if (intentName.equals("Clova.NoIntent")) { // 명령 확인
                mm = new MyExtensionMessage(intentName, "노 라고 하셨나요?", true, "PlainText");
            }
            else if (intentName.equals("Clova.GuideIntent")) { // 가이드 알려주기
                mm = new MyExtensionMessage("hearTestIntent", "내 방 전등 켜줘라고 해보세요", false, "PlainText");
            } else if (intentName.equals("Clova.CancelIntent")) { // 잘못된 응답 취소
                mm = new MyExtensionMessage("hearTestIntent", "전등 도우미 실행을 취소합니다.", true, "PlainText");
            }
        } else if (type.equals("SessionEndedRequest")) { // extension 종료
            mm = new MyExtensionMessage("turnOnIntent", "짱구 박사를 종료합니다. ", false, "PlainText");
        }
        return new ResponseEntity<MyExtensionMessage>(mm, HttpStatus.OK); }
}

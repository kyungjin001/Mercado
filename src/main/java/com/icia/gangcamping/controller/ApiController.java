package com.icia.gangcamping.controller;


import com.icia.gangcamping.dto.CampingDetailSaveDTO;
import com.icia.gangcamping.dto.CampingSaveDTO;

import com.icia.gangcamping.dto.TestDTO;
import com.icia.gangcamping.entity.CampingDetailEntity;
import com.icia.gangcamping.entity.CampingEntity;
import com.icia.gangcamping.service.CampingService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ApiController {

    private final CampingService cs;


    private static String getTagValue(String tag, Element element) {
        NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node value = list.item(0);
        if (value == null) {
            return null;
        }
        return value.getNodeValue();
    }
    @GetMapping("/test")
    public String ad(){ return "test";}

    @GetMapping("/insertCampingDB")
    public @ResponseBody List apiTest() throws IOException, ParseException {

        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=5K22ERxYpx037uiwVJ9pScUhIGitPmhoLBa4gLyKG6dKQkPgt19o8hvQCF97fjdE0M%2FBrbX%2B559dIXy6dwXLCA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*?????? ???????????????*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*??? ????????? ?????? ???*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(?????????),AND(???????????????),WIN(????????????),ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*????????????=?????????*/
        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode("???", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") +"=json");
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader br;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line="";
        String result = "";
//        Map jsonMap = new HashMap<String, Object>();
//        List list = new ArrayList<>();
        JSONArray item = new JSONArray();
        List list = new ArrayList<>();
        while ((result = br.readLine()) != null) {

            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject)jsonParser.parse(result);
            System.out.println("result : "+object);
            JSONObject response = (JSONObject)object.get("response");
            System.out.println("response : "+response);
            JSONObject body = (JSONObject)response.get("body");
            System.out.println("body : "+body);
            JSONObject items = (JSONObject)body.get("items");
            System.out.println("items : "+items);
            item = (JSONArray) items.get("item");
            System.out.println("item : " + item);
            for(int i=0;i<item.size();i++) {
                JSONObject item1 = (JSONObject) item.get(i);
                System.out.println("items : " + item1);
                TestDTO dto = new TestDTO();
                dto.setAddr((String) item1.get("addr1"));
                dto.setTel((String) item1.get("tel"));
                dto.setName((String) item1.get("facltNm"));

                this.saveCampingDB(item1);
                list.add(dto);
            }

//            System.out.println(item.toString());
//            System.out.println("??????"+item.get("addr1"));
//            System.out.println("??????"+item.get("homepage"));
//            System.out.println("??????"+item.get("tel"));


//            list.add(line);
        }

            br.close();
            conn.disconnect();
        System.out.println(item.size()+"size");
       return list;

        }
    @GetMapping("/insertDetailDB")
    public @ResponseBody List apiTest1() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=5K22ERxYpx037uiwVJ9pScUhIGitPmhoLBa4gLyKG6dKQkPgt19o8hvQCF97fjdE0M%2FBrbX%2B559dIXy6dwXLCA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*?????? ???????????????*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*??? ????????? ?????? ???*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(?????????),AND(???????????????),WIN(????????????),ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*????????????=?????????*/
        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode("???", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") +"=json");
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader br;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line="";
        String result = "";
//        Map jsonMap = new HashMap<String, Object>();
//        List list = new ArrayList<>();
        JSONArray item = new JSONArray();
        List list = new ArrayList<>();
        while ((result = br.readLine()) != null) {

            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject)jsonParser.parse(result);
            System.out.println("result : "+object);
            JSONObject response = (JSONObject)object.get("response");
            System.out.println("response : "+response);
            JSONObject body = (JSONObject)response.get("body");
            System.out.println("body : "+body);
            JSONObject items = (JSONObject)body.get("items");
            System.out.println("items : "+items);
            item = (JSONArray) items.get("item");
            System.out.println("item : " + item);
            for(int i=0;i<item.size();i++) {
                JSONObject item1 = (JSONObject) item.get(i);
                System.out.println("items : " + item1);
                TestDTO dto = new TestDTO();
                dto.setAddr((String) item1.get("featureNm"));
                dto.setTel((String) item1.get("sbrsCl"));
                dto.setName((String) item1.get("posblFcltyCl"));
                this.saveCampingDetailDTO(item1, (long) i+1);
                list.add(dto);
            }

//            System.out.println(item.toString());
//            System.out.println("??????"+item.get("addr1"));
//            System.out.println("??????"+item.get("homepage"));
//            System.out.println("??????"+item.get("tel"));


//            list.add(line);
        }

        br.close();
        conn.disconnect();
        System.out.println(item.size()+"size");
        return list;

    }

       public void saveCampingDB(JSONObject item){
            CampingSaveDTO campingSaveDTO = new CampingSaveDTO();
            campingSaveDTO.setCampingAddr((String) item.get("addr1"));
           System.out.println((String) item.get("addr1")+item.get(("addr2")));
            campingSaveDTO.setCampingInfo((String) item.get("lineIntro"));
            String campingName = (String) item.get("facltNm");
            String name = campingName.replace(" ","");
            campingSaveDTO.setCampingName(name);
            campingSaveDTO.setCampingLikeCount(0);
            campingSaveDTO.setCampingFileName((String) item.get("firstImageUrl"));
            CampingEntity entity = new CampingEntity();
            entity = entity.dtoToEntity(campingSaveDTO);
            cs.save(entity);

        }
        public void saveCampingDetailDTO(JSONObject item,Long i){
            CampingDetailSaveDTO saveDTO = new CampingDetailSaveDTO();
            System.out.println(i+"??????????????????");
            saveDTO.setCampingEntity(cs.findById(i).get());
            saveDTO.setFacility1((String) item.get("posblFcltyCl"));
            String fuc2 = (String) item.get("featureNm");

            if(item.get("featureNm")==null){
                saveDTO.setFacility2(null);
            }
            else if(fuc2.length()>255){
                fuc2 = fuc2.substring(0,255);
                saveDTO.setFacility2(fuc2);
            }else{
                saveDTO.setFacility2((String) item.get("featureNm"));
            }

            saveDTO.setFacility3((String) item.get("posblFcltyEtc"));
            saveDTO.setFacility4((String) item.get("sbrsCl"));
            saveDTO.setFacility5((String) "???????????? ???????????? :"+item.get("animalCmgCl"));
            saveDTO.setFacility6((String) "???????????? ?????? :"+item.get("eqpmnLendCl"));
            saveDTO.setFacility7((String) "????????? ???????????? :"+item.get("glampInnerFclty"));
            saveDTO.setFacility8((String) "????????? ????????????: "+item.get("caravInnerFclty"));
            CampingDetailEntity entity = new CampingDetailEntity();
            entity = entity.dtoToEntity(saveDTO);
            cs.saveDetail(entity);

        }

    }




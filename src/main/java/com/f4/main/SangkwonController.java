package com.f4.main;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.f4.main.dto.SangkwonDTO;
import com.f4.main.service.RankingMapper;
import com.f4.main.service.SangkwonCode;
import com.f4.main.service.SangkwonData;
import com.f4.main.service.SangkwonMapper;
import com.f4.main.service.SangkwonName;
import com.f4.main.service.SearchService;

@Controller
public class SangkwonController {
	
	@Autowired
	private SearchService  searchService;

	   @Autowired
	   private SangkwonMapper sangkwonmapper;
	
	   @Autowired
	   private SangkwonData sangkwondata;
	   
	   @Autowired
	   private SangkwonCode sangkwoncode;

	   @Autowired
	   private SangkwonName sangkwonname;
	   
	   @Autowired
	   private RankingMapper rankingmapper;
	
	
	   @RequestMapping("/sangkwon.do")
	   public String sangkwon() {
	      return "sangkwon/sangkwon";
	   }

		@GetMapping("/brandData/{viewName}")
		public String fetchData(@PathVariable String viewName, String storeName, String img, Model model) {
	      try {
	          // 요청 변수 설정
	          String serviceKey = "P8k%2FYQ1EfxOWsGirQe3%2FL%2BJl9UQIFd7s3lVfnqPt5mpsX3mDO7wjpkQ0X%2Fm3Ot8O50eP3QTrvS%2BAgakMG0YgmQ%3D%3D";
	          String pageNo = "1";
	          String numOfRows = "10";

	          String jngIfrmpRgsno = sangkwoncode.find(storeName); // 여기에 적절한 값을 설정하세요.
	          String brandName = storeName;
	          
	          String yr = sangkwondata.year(storeName);
	          String yr1 = sangkwondata.year1(storeName);
	          String yr2 = sangkwondata.year(storeName);
	          
	          // URL 구성 인테리어 비용
	          String urlString = String.format(
	          		"https://apis.data.go.kr/1130000/FftcBrandFrcsIntInfoService/getBrandFrcsIntInfo?serviceKey=%s&pageNo=%s&numOfRows=%s&yr=%s&jngIfrmpRgsno=%s",
	          		serviceKey, pageNo, numOfRows, yr, jngIfrmpRgsno);
	          // 평균 매출액
	          String urlString1 = String.format(
	          		"https://apis.data.go.kr/1130000/FftcBrandFrcsUnitAvrSalInfoService/getBrandFrcsUnitAvrSalInfo?serviceKey=%s&pageNo=%s&numOfRows=%s&yr=%s&jngIfrmpRgsno=%s",
	          		serviceKey, pageNo, numOfRows, yr1, jngIfrmpRgsno);
	          // 창업비용
	          String urlString2 = String.format(
	          		"https://apis.data.go.kr/1130000/FftcBrandFrcsAlotmInfoService/getBrandFrcsAlotmInfo?serviceKey=%s&pageNo=%s&numOfRows=%s&yr=%s&jngIfrmpRgsno=%s",
	          		serviceKey, pageNo, numOfRows, yr2, jngIfrmpRgsno);
	          
	          URL url = new URL(urlString);
	          URL url1 = new URL(urlString1);
	          URL url2 = new URL(urlString2);

	          // HTTP 연결 설정 인테리어 비용
	          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	          HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
	          HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
	         
	          conn.setRequestMethod("GET");
	          conn.setRequestProperty("accept", "*/*");
	          
	          conn1.setRequestMethod("GET");
		       conn1.setRequestProperty("accept", "*/*");  
		        
		       conn2.setRequestMethod("GET");
	          conn2.setRequestProperty("accept", "*/*");         

	          // 응답 내용 읽기
	          BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
	          BufferedReader in1 = new BufferedReader(new InputStreamReader(conn1.getInputStream(), StandardCharsets.UTF_8));
	          BufferedReader in2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), StandardCharsets.UTF_8));
	          String inputLine;
	          String inputLine1;
	          String inputLine2;
	          StringBuffer response = new StringBuffer();
	          StringBuffer response1 = new StringBuffer();	            
	          StringBuffer response2 = new StringBuffer();	            

	          while ((inputLine = in.readLine()) != null) {
	              response.append(inputLine);
	          }
	          in.close();
	          
	          while ((inputLine1 = in1.readLine()) != null) {
		             response1.append(inputLine1);
		        }
		        in1.close();
		        while ((inputLine2 = in2.readLine()) != null) {
	              response2.append(inputLine2);
	          }
	          in2.close();


	          // 결과를 문자열 변수에 저장
	          String responseString = response.toString();
	          String responseString1 = response1.toString();
	          String responseString2 = response2.toString();

	          // XML 파서 초기화
	          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	          DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
	          DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
	          DocumentBuilder builder = factory.newDocumentBuilder();
	          DocumentBuilder builder1 = factory1.newDocumentBuilder();
	          DocumentBuilder builder2 = factory2.newDocumentBuilder();

	          // 문자열을 XML 문서로 파싱
	          Document doc = builder.parse(new InputSource(new StringReader(responseString)));
	          Document doc1 = builder1.parse(new InputSource(new StringReader(responseString1)));
	          Document doc2 = builder2.parse(new InputSource(new StringReader(responseString2)));
	          
	          NodeList itemList1 = doc1.getElementsByTagName("item");
	      

	          // 원하는 요소 추출
	          String interiorCost = doc.getElementsByTagName("intrrAmt").item(0).getTextContent();
	          String storeArea = doc.getElementsByTagName("storCrtraAr").item(0).getTextContent();
	          String squareInteriorCost = doc.getElementsByTagName("unitArIntrrAmt").item(0).getTextContent();
	          
	          String DepositAmount = doc2.getElementsByTagName("jngBzmnAssrncAmt").item(0).getTextContent();
		      String education = doc2.getElementsByTagName("jngBzmnEduAmt").item(0).getTextContent();
		      String OtherCosts = doc2.getElementsByTagName("jngBzmnEtcAmt").item(0).getTextContent();
		      String FranchiseAmount = doc2.getElementsByTagName("jngBzmnJngAmt").item(0).getTextContent();
		      String totalAmount = doc2.getElementsByTagName("smtnAmt").item(0).getTextContent();

	          
	          // 모델에 데이터 추가
	          model.addAttribute("brandName", brandName); 		// 브랜드명
	          model.addAttribute("interiorCost", interiorCost); // 인테리어 비용
	          model.addAttribute("storeArea", storeArea);		// 면적
	          model.addAttribute("squareInteriorCost", squareInteriorCost);	// 평당 인테리어비용
	          
	          	
	          model.addAttribute("DepositAmount", DepositAmount); 	// 보증금액	
	          model.addAttribute("education", education);			// 교육금액
	          model.addAttribute("OtherCosts", OtherCosts);			// 기타금액
	          model.addAttribute("FranchiseAmount", FranchiseAmount);	// 가맹금액
	          model.addAttribute("totalAmount", totalAmount);		// 합계
	          
	          

		         for (int i = 0; i < itemList1.getLength(); i++) {
		             Element item1 = (Element) itemList1.item(i);
		             String areaNm = item1.getElementsByTagName("areaNm").item(0).getTextContent();

		             if (areaNm.equals("서울")) {
		                
		                 String storeAreaSales = item1.getElementsByTagName("arUnitAvrgSlsAmt").item(0).getTextContent();
		                 String averageSales = item1.getElementsByTagName("avrgSlsAmt").item(0).getTextContent();

		    	         model.addAttribute("storeAreaSales", storeAreaSales); 	// 면적단위평균매출금액
		    	         model.addAttribute("averageSales", averageSales);		// 평균매출금액
		             }
		         }
		         
		         if (img != null) {
		        	 model.addAttribute("img", img);
		         }
		         		         
		         return viewName;
	          

	      } catch (Exception e) {
	          e.printStackTrace();
	          return "error"; // 오류 발생 시 뷰 이름 반환
	      }
		}
		
		@RequestMapping(value = "/updateSearchHistory.do", method = RequestMethod.POST)
		@ResponseBody
		public List<String> updateSearchHistory(@RequestParam("searchString") String searchString, HttpServletRequest request) {
		    HttpSession session = request.getSession();
	 	    List<String> searchHistory = (List<String>) session.getAttribute("searchHistory");

		    if (searchHistory == null) {
		        searchHistory = new ArrayList<>();
		    }

		    if (!searchHistory.contains(searchString.trim())) {
		        searchHistory.add(searchString.trim());
		    }

		    session.setAttribute("searchHistory", searchHistory);

		    return searchHistory;
		}
		
		
		
		
		
		
		 @GetMapping("/search.do")
		 @ResponseBody
		    public ResponseEntity<Map<String, Object>> search(@RequestParam String searchString, Model model) {
     		 Map<String, Integer> counts = new HashMap<>();
		     Map<String, Object> map = new HashMap<>(); 		// Map<브랜드명, Map<x좌표, y좌표>>
		     Map<String, String> img = new HashMap<>();
		     Map<Double, String> address = new HashMap<>();
			 String tableName = null;
			 String district = null;
            
		        tableName = sangkwondata.getTableName(searchString.replace(" ", "")); // 검색결과를 getTableName메서드에서 비교해서 업종명인지 비교
		        
		        //연관검색어 동으로 검색할떄 !!
		        if(searchString.contains("동")) {  //contains와 contentsequals랑 혼동했다
		        	//contains는 문자열안에 포함되는지  contentesequals는 문자열안에 해당문자가 있는지를 물어보는 메서드이다.
		        	
		    		for (String tablename : sangkwonname.getKeys()) { // StoreName서비스 클래스에서 부모 key값을 tableName에 저장
	    		        
	    		        for (String storeName : sangkwonname.getValues(tablename)) {
	    		            List<SangkwonDTO> list = sangkwonmapper.getCoordinate(tablename, searchString, storeName);
	    		            int count = sangkwonmapper.countByValueT(tablename, searchString, storeName); // 각 값에 대한 카운트를 가져옴
	    		           
	    		           
	    		            if (count != 0) {       //갯수가 0인경우 추가하지 않음 
	    		                Map<Double, Double> coordinate = new HashMap<>(); // 브랜드 이름과 해당하는 데이터의 좌표를 담을 맵
		    		            for (SangkwonDTO dto : list) {
		    		                coordinate.put(dto.getX(), dto.getY());
		    		                address.put(dto.getX(), dto.getStoreAddress());
		    		                // y좌표를 key값으로 저장하고 x좌표를 value값으로 저장
		    		            }        
		    		            String image = sangkwondata.image(storeName);
		    		            img.put(storeName, image);
		    		            map.put(storeName, coordinate);
		    		            counts.put(storeName,count);
	    		            }
	           
	    		        }
	    		    }
	        		if (searchString.equals("강")) {
	        			sangkwondata.XY("강남구", model);
	        		}else if (searchString.equals("성")) {
	        			sangkwondata.XY("성동구", model);

	        		}else {
	        			sangkwondata.XY(searchString, model);
	        		}
	        		Map<String, Object> response = new HashMap<>();
			        response.put("counts", counts);   
			        response.put("map", map);
			        response.put("img", img);
			        response.put("address", address);
			        response.put("x", model.getAttribute("x"));
			        response.put("y", model.getAttribute("y"));

			        return new ResponseEntity<>(response, HttpStatus.OK);
		        }
		        
		        //연관검색어 동으로 검색할떄 !! 
		        
		        
		        if (tableName == null) {	// 검색 결과가 업종명이 아닌경우
		        	district = sangkwondata.getDistrict(searchString.replace(" ", ""));	// 검색결과가 자치구인지 비교
		        	
		        	if (district == null) {	// 검색결과가 자치구가 아닌경우
		        		String storeName = sangkwondata.getStoreName(searchString.replace(" ", ""));
		        		if (storeName == null) {
		        			Map<String, Object> response = new HashMap<>();
					        response.put("result", null);
		        			return new ResponseEntity<>(null, HttpStatus.OK);
		        		}else {
		        			rankingmapper.updateRank(storeName);
		        			String tablename = sangkwonname.findKey(storeName);
			        			List<SangkwonDTO> list = sangkwonmapper.search1(tablename, storeName);
			        			
						         int count = sangkwonmapper.count(tablename, storeName); // 각 값에 대한 카운트를 가져옴

						         Map<Double, Double> coordinate = new HashMap<>();
						         
						         	for (SangkwonDTO dto : list) {
			    		                coordinate.put(dto.getX(), dto.getY());
			    		                address.put(dto.getX(), dto.getStoreAddress());
			    		                // y좌표를 key값으로 저장하고 x좌표를 value값으로 저장
			    		            }
			    		            
			    		            String image = sangkwondata.image(storeName);
			    		            img.put(storeName, image);
			    		            map.put(storeName, coordinate);
			    		            counts.put(storeName,count);
					    	
			        		Map<String, Object> response = new HashMap<>();
					        response.put("counts", counts);
					        response.put("map", map);
					        response.put("img", img);
					        response.put("address", address);
					        response.put("x", 126.990414851);
					        response.put("y", 37.572583105);

					        return new ResponseEntity<>(response, HttpStatus.OK);
		        		}
					         
		        		
		        	}else {		// 검색결과가 자치구 인경우

		        		for (String tablename : sangkwonname.getKeys()) { // StoreName서비스 클래스에서 부모 key값을 tableName에 저장
		    		        
		    		        for (String storeName : sangkwonname.getValues(tablename)) {
		    		            List<SangkwonDTO> list = sangkwonmapper.getCoordinate(tablename, district, storeName);
		    		            int count = sangkwonmapper.countByValueT(tablename, district, storeName); // 각 값에 대한 카운트를 가져옴
		    		            
		    		            Map<Double, Double> coordinate = new HashMap<>(); // 브랜드 이름과 해당하는 데이터의 좌표를 담을 맵
		    		            for (SangkwonDTO dto : list) {
		    		                coordinate.put(dto.getX(), dto.getY());
		    		                address.put(dto.getX(), dto.getStoreAddress());
		    		                // y좌표를 key값으로 저장하고 x좌표를 value값으로 저장
		    		            }
		    		            
		    		            String image = sangkwondata.image(storeName);
		    		            img.put(storeName, image);
		    		            map.put(storeName, coordinate);
		    		            counts.put(storeName,count);
		    		        }
		    		    }
		        		if (district.equals("강")) {
		        			sangkwondata.XY("강남구", model);
		        		}else if (district.equals("성")) {
		        			sangkwondata.XY("성동구", model);

		        		}else {
		        			sangkwondata.XY(district, model);
		        		}
		        		Map<String, Object> response = new HashMap<>();
				        response.put("counts", counts);
				        response.put("map", map);
				        response.put("img", img);
				        response.put("address", address);
				        response.put("x", model.getAttribute("x"));
				        response.put("y", model.getAttribute("y"));

				        return new ResponseEntity<>(response, HttpStatus.OK);
		        	}
		        } else {	// 검색결과가 업종명인 경우
		        	
			    	 for (String storeName : sangkwonname.getValues(tableName)) {
				         List<SangkwonDTO> list = sangkwonmapper.selectAll(tableName, storeName);
				         int count = sangkwonmapper.count(tableName, storeName); // 각 값에 대한 카운트를 가져옴
				         
				         Map<Double, Double> coordinate = new HashMap<>();
				         for (SangkwonDTO dto : list) {
				             coordinate.put(dto.getX(), dto.getY());
				             address.put(dto.getX(), dto.getStoreAddress());
				         }
				         String image = sangkwondata.image(storeName);
				         img.put(storeName, image);
				         map.put(storeName, coordinate);
				         counts.put(storeName,count);
			    	 }
			    	
			         // 여기에서 원하는 처리 수행
			    	Map<String, Object> response = new HashMap<>();
			    	response.put("counts", counts);
			        response.put("map", map);
			        response.put("img", img);
			        response.put("address", address);
			        response.put("x", 126.990414851);
			        response.put("y", 37.572583105);
			        
			        return new ResponseEntity<>(response, HttpStatus.OK);

		        }
		    }
 
	//연관 검색어
		 @GetMapping("/related_keywords.do")
		 @ResponseBody
		 public ResponseEntity<Map<String, Object>> related_keywords(HttpServletRequest req, @RequestParam String searchString) {
			 
		     Map<String, Object> suggestionsMap=null;
		     List<String>District=searchService.getDistrictsuggestionslist();
		     List <String>list=searchService.getCatelist();
		     Map<Object,Object>  autoCompleteSuggestions;
		     // LinkedHashMap을 사용  맵의 순서가 삽인된 순서로 보장 된다.(LinkedHashMap을 쓰면)
		         if ( searchString.equals("서울") || searchString.equals("서울시")|| searchString.equals("서울특별시")|| searchString.equals("서")) {
		        	 // ||는 or 연산자 &&는 and연산자  
		        	 if(searchString.equals("서")) {
		        		 autoCompleteSuggestions= new LinkedHashMap<>(); 
		        		 suggestionsMap = new HashMap<>();
		        		 Map<String, Map<String, String>> brandSuggestions1 = searchService.getAutoCompleteBrandSuggestions(searchString);
		        		 suggestionsMap.put("brandSuggestions1", brandSuggestions1);
		        		
		        		 List<String> autoCompleteSuggestionslist = searchService.getAutoCompleteSuggestions(searchString);
		        		 Map<String, Object> suggestion;
		        	 
		        	     for(String district : autoCompleteSuggestionslist) {
			            	 suggestion = new HashMap<>();
			            	 int count = searchService.getStoreAddressCount(district);    
			            	 suggestion.put("distric",district);
			            	 suggestion.put("count",count);		  
			            	 autoCompleteSuggestions.put(district, suggestion);
			             }
			             suggestionsMap.put("autoCompleteSuggestions", autoCompleteSuggestions);
		          }  
		        	 else {   //서가 아닐떄 (서울,서울특별시일 때) 처리
		        	  List<String> autoCompleteSuggestions1 = searchService.getAutoCompleteSuggestions(searchString);
		        	  suggestionsMap = new HashMap<>();
		        	   Map<Object,Object> autoCompleteSuggestions12= new LinkedHashMap<>(); 
		             for(String district : autoCompleteSuggestions1) {
		            	 Map<String, Object> suggestion = new HashMap<>();
		            	 int count = searchService.getStoreAddressCount(district);    
		            	 suggestion.put("distric",district);
		            	 suggestion.put("count",count);	      
		            	 autoCompleteSuggestions12.put(district, suggestion);
		                 }
		                  suggestionsMap.put("autoCompleteSuggestions12", autoCompleteSuggestions12);
		            }

                } else {
                	suggestionsMap = new HashMap<>();
		        	 for(String cate : list) {
		        		 if(cate.equals(searchString)) {   		//카테고리로 처리 할 때
		        			 Map<String, Map<String, Map<String,String>>> cateSuggestions = searchService.getAutoCompleteCateSuggestions(searchString);
		        		     suggestionsMap.put("cateSuggestions", cateSuggestions);
		        		 }
		        	 }		        	 
		        		 if(! suggestionsMap.containsKey("cateSuggestions")) {  //브랜드로 처리 할 때
		        		 Map<String, Map<String, String>> brandSuggestions = searchService.getAutoCompleteBrandSuggestions(searchString);
		        		 suggestionsMap.put("brandSuggestions", brandSuggestions);
		        	 }
		         	 
		        	  if(! suggestionsMap.containsKey("cateSuggestions")  && 
		        			  (searchService.checkcitynameSuggestionsgulist(searchString) || searchService.checkcitySuggestionsgulist(searchString)
		        			 || searchService.checkbyNBN(searchString) 
		        			 || searchService.checkNeighborhood(searchString))) {   		 		
		        		 
		        		  if(searchService.checkcitySuggestionsgulist(searchString)) { //구로 처리할 때
		        			 suggestionsMap = new HashMap<>();
		            		 Map<String,Integer>CityData=searchService.getNeighborhoodByCityname(searchString);
				                suggestionsMap.put("CityData", CityData);
		        		 }
		        		  else if(searchService.checkcitynameSuggestionsgulist(searchString)) {  //구의 이름으로 처리할때 
		        			 Map<String,Object>DBTD= searchService.getStoreNameByAddress(searchString);
				        	    suggestionsMap.put("DBTD", DBTD);	    
				        	    Map<String, Object> suggestion;
				        	          Map<Object,Object>  autoCompleteSuggestions2= new LinkedHashMap<>(); 
				        		      List<String> autoCompleteSuggestions1 = searchService.getAutoCompleteSuggestions(searchString);			
					                   for(String district : autoCompleteSuggestions1) {
					                    suggestion = new HashMap<>();
					            	   int count = searchService.getStoreAddressCount(district);    
					            	    suggestion.put("distric",district);
					            	   suggestion.put("count",count);
					                     //	 System.out.println(suggestion);
					            	    autoCompleteSuggestions2.put(district, suggestion);
					                    }
					                   
					         	      suggestionsMap.put("autoCompleteSuggestions2", autoCompleteSuggestions2);
				        	 } else if(searchService.checkbyNBN(searchString)) {
				        		  Map<String, Object>  suggestion = new HashMap<>();
				        		  Map<Object,Object>  autoCompleteSuggestions2= new LinkedHashMap<>(); 
				        		  Map<String, Integer>Neighborhoodbranddata=searchService.getNBDDSuggestions(searchString);
				        		 String district = Neighborhoodbranddata.keySet().iterator().next();
				        		 Integer count =Neighborhoodbranddata.get(district);
				        		    suggestion.put("distric",district);
					            	suggestion.put("count",count);
				        		   autoCompleteSuggestions2.put(district, suggestion);		      	   
				        		   suggestionsMap.put("autoCompleteSuggestions2", autoCompleteSuggestions2);
				        			// 맵에 키가 하나뿐이라면 KEYSET.ITERATOR.NEXT를 사용해 키값을 가져올수 있다.
				        		 Map<String,Object>DBTD= searchService.getStoreNameByAddress(district);
					        	    suggestionsMap.put("DBTD", DBTD);	     
				        	 }else if(searchService.checkNeighborhood(searchString)) {	 
				        			 suggestionsMap = new HashMap<>();
				            		 Map<String,Integer>CityData=searchService.getNBDDSuggestions(searchString);
						                suggestionsMap.put("Neighborhood", CityData);
				        	 }
		        		
		        		 }
		 
		        }
		     return new ResponseEntity<>(suggestionsMap, HttpStatus.OK);
		 }
		 
		 @GetMapping("/SelectRegion.do")
		 @ResponseBody
		 public ResponseEntity<Map<String, Object>> SelectRegion(HttpServletRequest req, @RequestParam String tableName, @RequestParam String Region, Model model) {

			 try {
	
		    	 String storeAddress =  Region;        

				    Map<String, Map<String,Integer>> counts = new HashMap<>(); 	// Map<업종, Map<브랜드명, 개수>>
				    Map<String, Object> map = new HashMap<>(); 		// Map<브랜드명, Map<x좌표, y좌표>>
				    Map<String, String> img = new HashMap<>();
				    Map<String, Integer> value = new HashMap<>(); // Map<브랜드명, 개수>
				    Map<Double, String> address = new HashMap<>();
				        
				        for (String storeName : sangkwonname.getValues(tableName)) {
				            List<SangkwonDTO> list = sangkwonmapper.getCoordinate(tableName, storeAddress, storeName);
				            int count = sangkwonmapper.countByValueT(tableName, storeAddress, storeName); // 각 값에 대한 카운트를 가져옴
				            Map<Double, Double> coordinate = new HashMap<>();
					         for (SangkwonDTO dto1 : list) {
					             coordinate.put(dto1.getX(), dto1.getY());
					             address.put(dto1.getX(), dto1.getStoreAddress());
					         }
				            
				            String image = sangkwondata.image(storeName);
				            img.put(storeName, image);
				            map.put(storeName, coordinate);
				            value.put(storeName, count);
				        }
				         
				        counts.put(tableName, value);
				        sangkwondata.XY(storeAddress, model);
				        Map<String, Object> response = new HashMap<>();
				        response.put("counts", counts);
				        response.put("map", map);
				        response.put("img", img);
				        response.put("address", address);
				        response.put("x", model.getAttribute("x"));
				        response.put("y", model.getAttribute("y"));

				        return new ResponseEntity<>(response, HttpStatus.OK);
		     } catch (Exception e) {
		         // 예외 처리
		         e.printStackTrace(); // 또는 로깅

		         throw new RuntimeException("검색 중 오류가 발생했습니다: " + e.getMessage());
		     }
		 }
		 
		 
		 @GetMapping("/cg_search.do")
		 @ResponseBody
		 public ResponseEntity<Map<String, Object>> cgSearch(HttpServletRequest req,  @RequestParam String tableName, Model model) {

		     try {
		    	 
		    	 Map<String,Map<Double, Double>> map = new HashMap<>(); 		// Map<브랜드명, Map<x좌표, y좌표>>
			     Map<String, Integer> counts = new HashMap<>();
			     Map<String, String> img = new HashMap<>();
			     Map<Double, String> address = new HashMap<>();
			     
		    	 for (String storeName : sangkwonname.getValues(tableName)) {
			         List<SangkwonDTO> list = sangkwonmapper.selectAll(tableName, storeName);
			         int count = sangkwonmapper.count(tableName, storeName); // 각 값에 대한 카운트를 가져옴
			         
			         Map<Double, Double> coordinate = new HashMap<>();
			         for (SangkwonDTO dto1 : list) {
			             coordinate.put(dto1.getX(), dto1.getY());
			             address.put(dto1.getX(), dto1.getStoreAddress());
			         }
			         String image = sangkwondata.image(storeName);
			         img.put(storeName, image);
			         map.put(storeName, coordinate);
			         counts.put(storeName,count);
		    	 }
		    	String storeAddress = "서울";
		         // 여기에서 원하는 처리 수행
		    	Map<String, Object> response = new HashMap<>();
		    	sangkwondata.XY(storeAddress, model);
		        response.put("counts", counts);
		        response.put("map", map);
		        response.put("img", img);
		        response.put("address", address);
		        response.put("x", model.getAttribute("x"));
		        response.put("y", model.getAttribute("y"));
		        
		        return new ResponseEntity<>(response, HttpStatus.OK);
		     }	 catch (Exception e) {
		         // 예외 처리
		         e.printStackTrace(); // 또는 로깅

		         throw new RuntimeException("검색 중 오류가 발생했습니다: " + e.getMessage());
		     }
		 }
		 
		 
			@RequestMapping(value = "/deleteRecord.do", method = RequestMethod.GET)
			public String deleteRecord(HttpServletRequest req, @RequestParam(name = "keyword", required = false) String keyword) {
			    HttpSession session = req.getSession();

			    List<String> searchHistory = (List<String>) session.getAttribute("searchHistory");

			    if (searchHistory != null) {
			        // 검색어 목록에서 해당 키워드를 제거
			        Iterator<String> iterator = searchHistory.iterator();
			        while (iterator.hasNext()) {
			            String history = iterator.next();
			            //공백 들어간것 삭제
			            if (history.trim().equalsIgnoreCase(keyword.trim())) {
			                iterator.remove();
			                break; // 키워드를 찾았으면 루프 종료
			            }
			        }
					for(String history : searchHistory ) {

					}
			        // 세션에 업데이트된 목록을 저장
			        session.setAttribute("searchHistory", searchHistory);
			    }

			    return "sangkwon/sangkwon";
			}
			
			@RequestMapping("/sangkwonAddress.do")
			public String sangkwonAddress(HttpServletRequest req, Model model) {
				String storeName = req.getParameter("storeName");
				String address = req.getParameter("address");
				
				model.addAttribute("storeName", storeName);
				model.addAttribute("address", address);
				
				return "sangkwon/sangkwonAddress";
			}
		 		  
		   
}

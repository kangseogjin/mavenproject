package com.f4.main.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
	
	  @Autowired
	   private SqlSession sqlSession;
	  SangkwonData sangkwongData = new SangkwonData();
	
	private List<String> suggestions;
	private List<String>Brand_suggestions;
	private List<String>cate_suggestions;
	private final Map<String,String> BrandData;
	private final Map<String,List> CateData;
	

    public SearchService() {
          //시,구  Arrays.asList()로 list를 생성하면 크기를 조정할 수없는 리스트로  된다.
        suggestions = Arrays.asList("서울특별시", "서울특별시 중구", "서울특별시 종로구", "서울특별시 용산구",
                                     "서울특별시 성동구", "서울특별시 광진구", "서울특별시 동대문구","서울특별시 중랑구","서울특별시 성북구","서울특별시 강북구","서울특별시 도봉구","서울특별시 노원구","서울특별시 은평구","서울특별시 서대문구","서울특별시 마포구","서울특별시 양천구","서울특별시 강서구","서울특별시 구로구","서울특별시 금천구","서울특별시 영등포구","서울특별시 동작구","서울특별시 관악구","서울특별시 서초구","서울특별시 강남구","서울특별시 송파구","서울특별시 강동구");
         //브랜드
        Brand_suggestions= Arrays.asList("BBQ", "네네치킨", "교촌치킨", "BHC",
                "푸라닭", "파파존스", "도미노","피자헛","피자스쿨","피자알볼로","원할머니","장충동","마왕족발","귀한족발","가장맛있는족발","쿠우쿠우","미소야","백소정","아비꼬","소노야","교동짬뽕","홍콩반점","경성양꼬치","소림마라","홍짜장","큰맘할매순대국","본죽","두찜","박가부대","팔각도","빕스","아웃백","애슐리","서가앤쿡","매드포갈릭","엽기떡볶이","죠스떡볶이","이삭토스트","고봉민","두끼","KFC","써브웨이","맥도날드"
,"롯데리아","버거킹","범맥주","인생맥주","한신포차","역전할머니맥주","경성주막","하노이별","생어거스틴","미스사이공","포메인","소담촌","베스킨라빈스","왕가탕후루","뚜레쥬르","파리바게트","설빙","스타벅스","투썸","메가커피","빽다방","이디야");
        
         //카테고리
        cate_suggestions= Arrays.asList("치킨","피자","족발/보쌈","일식","중식","한식","양식","분식","패스트푸드","주점","아시안","디저트","카페");
 
       //해당 브랜드에 카테고리 처리
        BrandData = new HashMap<>();
        int groupsize=5;
        for(int i=0; i<Brand_suggestions.size(); i++) {
        	int groupindex =i/groupsize ; 
            BrandData.put(Brand_suggestions.get(i),  cate_suggestions.get(groupindex));
        }

   //각각의 카테고리에 브랜드 처리
  CateData= new HashMap<>();      
 for(String category :  cate_suggestions) {   //category 값 뽑아내기
	 List <String>BrandInCategory=new ArrayList<String>();
   for(String Brand : Brand_suggestions) {  //brand값 뽑아내기
	   if(BrandData.get(Brand).equals(category)) {   
		   //BrandData Map의 value값이랑 category값이랑 비교후 같으면
		   //BrandInCategory list에 브랜드 이름 넣기
		   BrandInCategory.add(Brand);
	    }
      }
   CateData.put(category,BrandInCategory);
     }
   }
   
  //해당 주소의 매장
    public Map<String, Object> getStoreNameByAddress(String storeAddress){
          	List<String> tableNames = Arrays.asList("chicken", "pizza","fastFood","jokbal_Bossam","japanese", "chinese", "korean", "western", "snack", "pub", "asian", "dessert", "cafe");  
	    Map<String, Object> parameters = new HashMap<>();
	       Map<String,String>  brandData;
		 
	    parameters.put("tableNames", tableNames);
	    parameters.put("storeAddress", "%" + storeAddress + "%");
           // < --쿼리문-->
           List<String> storename = sqlSession.selectList("getStoreNameByAddress", parameters);
           //< --쿼리문-->
           
           Set<String> uniqueBrandNames = new HashSet<String>();
           
           for(String brandname : storename) {
        	   String uniqueBrandname=extractBrandName(brandname);
        	   uniqueBrandNames.add(uniqueBrandname);
           }
  
           Map<String,Object> districtbranddata=new HashMap<String, Object>();
           
    	   for(String brandname : uniqueBrandNames) {		
    		   brandData=  brandData=new HashMap<>();
    		   brandData.put("imageUrl", sangkwongData.image(brandname));
    		   brandData.put("findcate", find(brandname));
    		   brandData.put("brandname", brandname);   
    		   districtbranddata.put(brandname,  brandData);
    	   }
    	   
    	   Map<String,Object> DTBD=new HashMap<String, Object>();
    	   
    	   DTBD.put("DTBD", districtbranddata);
           return DTBD;
    }
    
  //해당 주소의 동까지 추출
    public Map<String,Integer> getNeighborhoodByCityname(String storeAddress){
    	List<String> tableNames = Arrays.asList("chicken", "pizza","fastFood","jokbal_Bossam","japanese", "chinese", "korean", "western", "snack", "pub", "asian", "dessert", "cafe");  
	    Map<String, Object> parameters = new HashMap<>();
	    Set<String> uniquecity =new HashSet<String>();
	    parameters.put("tableNames", tableNames);
	    parameters.put("storeAddress",storeAddress);
	//    System.out.println(storeAddress);
	      // < --쿼리문-->
           List<String> city = sqlSession.selectList("getNeighborhoodByCityname", parameters);
           //< --쿼리문-->
      //  System.out.println(city);
           Map<String,Integer> uniquecitydata=new HashMap<String, Integer>();
          for(String cityname : city) {
    //  System.out.println(cityname);
      
            uniquecitydata.put(cityname, uniquecitydata.getOrDefault(cityname, 0) + 1);
            // getOrDefalut는 해당 map의 키의 값이  없으면  반환값을 지정해주는 메서드로 매개변수 첫번쨰는
            //키  두번째는 반환값으로 처리한다 그래서 위의 코드는 키의 값이 없으면 0으로 반환시키고 1을 더한다는 말로
            //키의 값이 있으면 해당 키의 값을 가져오고 1을 더한다.
          }
           return uniquecitydata;
    }
    

 // 시,구 별 매장 개수 조회
    public int getStoreAddressCount(String storeAddress) {
        int totalCount = 0;
        List<String> tableNames = Arrays.asList("chicken", "pizza","fastFood","jokbal_Bossam","japanese", "chinese", "korean", "western", "snack", "pub", "asian", "dessert", "cafe");  
        // 서울특별시를 검색한 경우, 모든 구의 매장 수를 합산하여 반환
        if (storeAddress.equals("서울특별시")) {
            for (String suggestion : suggestions) {
                if (suggestion.startsWith("서울특별시 ")) { 
                	// startsWith는 () 안의 문자열이 접두사로 있을경우 boolean값으로 반환한다.
                	//()안에 서을특별시라는 문자열 다음에 띄고 공백이 들어가있는데 공백도 문자열로 취급을 해주기에
                	//suggestion list의 구조를 참고하여 구를 구분해주었다.
                	//여기서 접두사란 ~~ 123 라는 문자열이 있으면 123문자열에 띄어쓰기 전의 ~~를 접두사로 가르킨다.
                    totalCount += getStoreAddressCount(suggestion); 
                    // 재귀 메서드를 사용 (재귀 메서드란  메서드안에 자기 자신 메서드를 호출함 으로써 코드를 간결하게 해주고
                    //한 메서드 안에 같은 문제를 분할하여서 사용할 때 쓰인다.
                }
            }
        } else {
            // 매개변수 설정
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableNames", tableNames);
            parameters.put("storeAddress", "%" + storeAddress + "%"); 
            // MyBatis 쿼리문
            List<Integer> counts = sqlSession.selectList("getStoreAddressCount", parameters);
            
            // 각 테이블의 결과를 합산하여 총 개수 계산
            for (Integer count : counts) {
                totalCount += count;
            }
        }   
        return totalCount;
    }

    // 시,구 자동완성
    public List<String> getAutoCompleteSuggestions(String searchString) {
        List<String> autoCompleteSuggestions = new ArrayList<>();
        
        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().contains(searchString.toLowerCase())) {
                autoCompleteSuggestions.add(suggestion);
            }
        }
        return autoCompleteSuggestions;
    }
    
    //동의 이름으로 받을때 해당 동의 관련 번지들을 해당 동으로 변환후 번지들의 갯수와 해당 동으로 반환
    public Map<String,Integer>getNBDDSuggestions(String storeAddress) {
     	List<String> tableNames = Arrays.asList("chicken", "pizza","fastFood","jokbal_Bossam","japanese", "chinese", "korean", "western", "snack", "pub", "asian", "dessert", "cafe");  
	    Map<String, Object> parameters = new HashMap<>();
	    Set<String> uniquecity =new HashSet<String>();
	    parameters.put("tableNames", tableNames);
	    parameters.put("storeAddress",storeAddress);
	    
           List<String> dong = sqlSession.selectList("getNeighborhoodByNeighborhood", parameters);     
           List<String> neighborhoodnamelist =new ArrayList<String>();
    	 List<String> donglist=new ArrayList<String>();
    	 if(checkbyNBN(storeAddress)) {
    		  for(String dongname : dong) {
    	    		 // System.out.println(dongname);
    	    		  String[] parts = dongname.split("\\s+", 5);
    	    		  if(parts[2].endsWith("가")) {
    	    			  parts[2]=parts[2].replaceAll("\\d+가", ""); //replace는 한개를 받고 
    	    			  // replaceall은 한개이상받을떄 쓰인다.
    	    			  // \\d는 모든숫자를 표현한 정규식이고 +은 연속한다는뜻으로 한개 이상이라는 표현이다.
    	    			  neighborhoodnamelist.add(parts[0]+" "+parts[1]+" "+parts[2]);
    	    			 // System.out.println(neighborhoodnamelist);
    	    		  }else {
    	    			  neighborhoodnamelist.add(parts[0]+" "+parts[1]+" "+parts[2]);
    	    		  }
    	    	    	} 
    	 }else if(checkNeighborhood(storeAddress)) {
    		  for(String dongname : dong) {
 	    		 // System.out.println(dongname);
 	    		  String[] parts = dongname.split("\\s+", 5);
 	    		 neighborhoodnamelist.add(parts[0]+" "+parts[1]+" "+parts[2]+" "+parts[3]) ;
    	      }
    	 }
    	  Map<String,Integer> uniqueneighborhooddata=new HashMap<String, Integer>();
    	  for(String cityname : neighborhoodnamelist) {
    		  uniqueneighborhooddata.put(cityname, uniqueneighborhooddata.getOrDefault(cityname, 0) + 1);
    	  }
    	  return uniqueneighborhooddata;
    	   	}
    	 
    
 // 브랜드 연관검색
    public Map<String, Map<String, String>> getAutoCompleteBrandSuggestions(String searchString) {
        Map<String, Map<String, String>> autoCompleteSuggestions = new HashMap<>();
       
        for (String suggestion : Brand_suggestions) {
            if (suggestion.toLowerCase().contains(searchString.toLowerCase())) {
                Map<String, String> brandInfo = new HashMap<>();
                brandInfo.put("imageUrl", sangkwongData.image(suggestion));
                brandInfo.put("findcate", find(suggestion));
                autoCompleteSuggestions.put(suggestion, brandInfo);
            }
        }
        return autoCompleteSuggestions;
    }
    
    // 카테고리 연관검색
       public   Map<String, Map<String, Map<String,String>>> getAutoCompleteCateSuggestions(String searchString) {

    	   Map<String, Map<String, Map<String,String>>> autoCompleteSuggestions = new HashMap<>();
        
           Map<String, Map<String,String>> brandInfo = new HashMap<>();
          
           List<String> list=findCatedata(searchString);
           
           for (String suggestion :  cate_suggestions) {
               if (suggestion.toLowerCase().equals(searchString.toLowerCase())) {
            	    //   System.out.println(list);	   
            	   for(String brandname : list) {
            		   Map<String,String> brandData=new HashMap<>();
            		    //   System.out.println("list 값  "+brandname);
            		   brandData.put("imageUrl", sangkwongData.image(brandname));
            		   brandData.put("findcate", find(brandname));
            		   brandData.put("brandname", brandname);
            	       brandInfo.put(brandname, brandData);
                         //	System.out.println(brandInfo);
            	    }
            	   autoCompleteSuggestions.put(suggestion, brandInfo);     
            	 //  System.out.println(autoCompleteSuggestions);      	    
               }
           }
           return autoCompleteSuggestions;
       }
         
    //해당 브랜드 카테고리 value값 꺼내오는 메서드
    public String find(String key) {
        for (Map.Entry<String, String> entry : BrandData.entrySet()) {  
        	//entry는 map의 키와 value값으로 이루어져있는 한쌍의 요소를 가르킨다.
        	//그래서 entryset()의 메서드의 의미는 해당 map의 엔트리(하나의 쌍의 요소)를 가져온다. 
        	//Map.Entry는 map의 엔트리를 가르키는것으로 해당 맵에 엔트리를 넣어줄때 쓴다.
            if (entry.getKey().equals(key)) {
              return entry.getValue();
            }
        }
        return null;
    }

    //카테고리에 해당 브랜드들을 List로 value값 꺼내오는 메서드
    public List findCatedata(String key) {
        for (Map.Entry<String, List> entry :  CateData.entrySet()) {  
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    //카테고리리스트
    public List getCatelist() {
        return  cate_suggestions;
    }
    
    //시,구 리스트
    public List getDistrictsuggestionslist() {	  
        return  suggestions;
    }
    
    //서을특별시를 뺴고 서울특별시의 구가 들어간것만 추출한 리스트
    public List getDistrictsuggestionsgulist() {	  
     List<String> gulist=new ArrayList<String>();
    for(String gu : suggestions) {
    		if(gu.startsWith("서울특별시 ")) {
    			//공백이 중요하다 공백도 문자로 취급하기에 서울특별시[공백] 라는 문자열이 된다. 
    			//그래서 공백을 안넣으면 서울특별시가 포함되어있는것으로 간주하기 list에 다 담기는데 공백이 있으면 
    			//서울특별시 구로구라고 치면 서울특별시[공백]이 들어가 있기에  서울특별시[공백]것이 들어 간것으로 찾는다.
    			gulist.add(gu);
    		}
    	}
        return  gulist;
    }
    
    //구의 이름만 추출한 리스트에서 searchString이 리스트에 해당되는지 체크하는 메서드
    public Boolean checkcitynameSuggestionsgulist(String storeaddres) {
    	 List<String> gulist=new ArrayList<String>();
    	    for(String gu : suggestions) {
    	    		if(gu.startsWith("서울특별시 ")) {
    	    			//공백이 중요하다 공백도 문자로 취급하기에 서울특별시[공백] 라는 문자열이 된다. 	
    	    			  String[] parts = gu.split("\\s+", 2);  //spilt는 문자열을 분리하는 메서드로 첫번쨰 변수는 어떤 기준으로를 말하고
    	    			  //2번쨰 매개변수는 그 기준에 최대 반복되는 개수를 말한다.
    	    			  String cityname=parts[1].replace("구", ""); //replace 메서드는 첫번쨰 매개변수는 해당 문자를 말하고
    	    			  //두번쨰 매개변수는 첫번째 매개변수를 어떻게 바꿔야 할지 값을 정한다.
    	    	            gulist.add(cityname); // 분리할떄 []이런식으로 인덱스로 분리를하기에 0부터 시작이된다.
    	    	       }
    	    	}
    	   // System.out.println(gulist);  
    	      for(String cityname : gulist) {
    	    	//  System.out.println(storeaddres);
    	    	 // System.out.println(cityname);
    	        if(cityname.equals(storeaddres)) {
    	        //	System.out.println("체크 됨"+storeaddres);
    		      return true;
    	         }
    	       }
    	   return false;
    }

    //구를 추출한 리스트에서 searchString이 리스트에 해당되는지 체크하는 메서드
    public Boolean checkcitySuggestionsgulist(String storeaddres) {
    	 List<String> gulist=new ArrayList<String>();
    	    for(String gu : suggestions) {
    	    		if(gu.startsWith("서울특별시 ")) {
    	    			//공백이 중요하다 공백도 문자로 취급하기에 서울특별시[공백] 라는 문자열이 된다. 	
    	    			  String[] parts = gu.split("\\s+", 2);  //spilt는 문자열을 분리하는 메서드로 첫번쨰 변수는 어떤 기준으로를 말하고
    	    			  //2번쨰 매개변수는 그 기준에 최대 반복되는 개수를 말한다.  
    	    	            gulist.add(parts[1]); // 분리할떄 []이런식으로 인덱스로 분리를하기에 0부터 시작이된다.
    	    	            //System.out.println(gulist);
    	    	       }
    	    	}
    	      for(String cityname : gulist) {
    	        if(cityname.equals(storeaddres)) {
    		      return true;
    	         }
    	       }
    	   return false;
    }
    
    //동의 이름만 추출한 리스트에서 searchString이 리스트에 해당되는지 체크하는 메서드
    public Boolean checkbyNBN(String storeAddress) {
     	List<String> tableNames = Arrays.asList("chicken", "pizza","fastFood","jokbal_Bossam","japanese", "chinese", "korean", "western", "snack", "pub", "asian", "dessert", "cafe");  
	    Map<String, Object> parameters = new HashMap<>();
	    Set<String> uniquecity =new HashSet<String>();
	    parameters.put("tableNames", tableNames);
	    parameters.put("storeAddress",storeAddress);
           List<String> dong = sqlSession.selectList("getNeighborhoodByNeighborhood", parameters);
           //System.out.println(dong);
           //< --쿼리문--> 
    	 List<String> donglist=new ArrayList<String>();
    	 List<String> neighborhoodnamelist =new ArrayList<String>();
    	  for(String dongname : dong) {
    		 // System.out.println(dongname);
    		  String[] parts = dongname.split("\\s+",4);
    	//System.out.println(neighborhoodname);
    	    	 if(parts[2].endsWith("가")) {
       			  parts[2]=parts[2].replaceAll("\\d+가", ""); //replace는 한개를 받고 
       			  // replaceall은 한개이상받을떄 쓰인다.
       			  parts[2]=parts[2].replace("동", "");
       			  // \\d는 모든숫자를 표현한 정규식이고 +은 연속한다는뜻으로 한개 이상이라는 표현이다.
       			  neighborhoodnamelist.add(parts[2]);
    	   	}else if(parts[2].endsWith("동")) {
    	   	  parts[2]=parts[2].replaceAll("동", "");
    	   	 neighborhoodnamelist.add(parts[2]);
    	   	}
    	    	 for(String name : neighborhoodnamelist) {
    	    		 if(name.equals(storeAddress)) {
    	    			 //System.out.println("성공");
    	    			 return true;
    	    		 }else {
    	    			 return false;
    	    		 }
    	    	 } 
          }
    	  return  false;
    }
    
  //동으로 추출한 리스트에서 searchString이 리스트에 해당되는지 체크하는 메서드
    public Boolean checkNeighborhood(String storeAddress) {
     	List<String> tableNames = Arrays.asList("chicken", "pizza","fastFood","jokbal_Bossam","japanese", "chinese", "korean", "western", "snack", "pub", "asian", "dessert", "cafe");  
	    Map<String, Object> parameters = new HashMap<>();
	    Set<String> uniquecity =new HashSet<String>();
	    parameters.put("tableNames", tableNames);
	    parameters.put("storeAddress",storeAddress);
           List<String> dong = sqlSession.selectList("getNeighborhoodByNeighborhood", parameters);
           //System.out.println(dong);
           //< --쿼리문-->
    	 List<String> donglist=new ArrayList<String>();
    	 List<String> neighborhoodnamelist =new ArrayList<String>();
    	  for(String dongname : dong) {
    		  String[] parts = dongname.split("\\s+",5);
       			  neighborhoodnamelist.add(parts[2]);
    	    	 for(String name : neighborhoodnamelist) {
    	    		 if(name.equals(storeAddress)) {
    	    			 //System.out.println("성공");
    	    			 return true;
    	    		 }else {
    	    			 return false;
    	    		 }
    	    	 } 
          }
    	  return  false;
    }
     
	//브랜드명만  추출
    public String extractBrandName(String storeName) {
    	
	    if (storeName == null) {
	        return ""; // 또는 다른 기본값을 지정
	    }   
	    Pattern lifebeerPattern = Pattern.compile("인쌩맥주|인생맥주|인생 맥주");
	    Matcher lifebeerMatcher = lifebeerPattern.matcher(storeName);
	    //BHC가  한글로 되어있는 데이터 구조가 있어서 처리해준다 BHC로 처리해준다
	    Pattern bhcPattern = Pattern.compile("BHC|비에이치시|비에치씨|비에이치");
	    Matcher bhcMatcher = bhcPattern.matcher(storeName);
	    //족발은 띄어쓰기 되어있는거 처리 
	    Pattern jok1Pattern=Pattern.compile("가장맛있는족발|가장 맛있는 족발");
	    Matcher jok1Matcher = jok1Pattern.matcher(storeName);
	    Pattern jok2Pattern=Pattern.compile("귀한족발|귀한 족발");
	    Matcher jok2Matcher = jok2Pattern.matcher(storeName);
	    if (bhcMatcher.find()) {
	        return "BHC";
	    } else if(jok1Matcher.find()){
	    	return "가장맛있는족발";
	    }   else if(jok2Matcher.find()){
	    	return "귀한족발";
	    }  else if(lifebeerMatcher.find()){
	    	return "인생맥주";
	    }else {
	    // 정규표현식으로 브랜드 이름 추출
	    Pattern pattern = Pattern.compile("(BBQ|네네치킨|교촌치킨|BHC|푸라닭|도미노|파파존스|피자헛|피자스쿨|피자알볼로|원할머니|장충동|마왕족발|미소야|백소정|아비꼬|소노야|쿠우쿠우|홍콩반점|교동짬뽕|경성양꼬치|소림마라|홍짜장|본죽|두찜|박가부대|팔각도|큰맘할매순대국|빕스|애슐리|아웃백|매드포갈릭|서가앤쿡|엽기떡볶이|죠스떡볶이|이삭토스트|고봉민|두끼|범맥주|한신포차|역전할머니맥주|경성주막|맥도날드|롯데리아|버거킹|KFC|써브웨이|하노이별|포메인|생어거스틴|미스사이공|소담촌|베스킨라빈스|설빙|왕가탕후루|뚜레쥬르|파리바게트|스타벅스|이디야|메가커피|빽다방|투썸)");
	    Matcher matcher = pattern.matcher(storeName);
	             if (matcher.find()) {
	               return matcher.group(1);
	               } else {
	                   return storeName;
	         }
	    }
	}
    
}
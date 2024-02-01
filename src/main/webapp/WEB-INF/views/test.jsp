<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>브랜드 정보</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: white;
            margin: 0;
            padding: 0;
        }

        .help_area {
            background-color: white;
            max-width: 600px;
            padding: 20px;
            border-radius: 8px;
        }

        .help_area h3 {
            font-size: 18px;
            color: #333;
            margin: 10px 0;
        }

        hr {
            border: 1px solid #000;
            margin: 10px 0;
        }

        button {
            background-color: black;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: black;
        }
       </style> 
</head>

<body>

<div data-v-581bdaec="" align="center" class="help_area">
        <div data-v-581bdaec="">
        <h2 data-v-581bdaec="" data-v-5ee806c3="">브랜드 정보</h2>
        <hr color="black" width="300">
        
                    <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">브랜드명 : ${brandName}</h3>
                    </div>
                    
                    
                   <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">인테리어비용 : ${interiorCost}</h3>
                   </div>
            
                    <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">면적 : ${storeArea}</h3>
                    </div>
                    
                    <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">평당 인테리어비용 : ${squareInteriorCost}</h3>
                    </div>
                    
                    
                    <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">면적단위평균매출금액 : ${storeAreaSales}</h3>
                    </div>
                    
                    <div data-v-5ee806c3="" data-v-581bdaec="">
                         <h3 data-v-581bdaec="" data-v-5ee806c3="">평균매출금액 : ${averageSales}</h3>
                    </div>
                    
                    
                      <div data-v-5ee806c3="" data-v-581bdaec="">
                         <h3 data-v-581bdaec="" data-v-5ee806c3="">보증금액 : ${DepositAmount}</h3>
                    </div>

                     <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">교육금액 : ${education}</h3>
                    </div>
                    
                     <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">기타금액 : ${OtherCosts}</h3>
                    </div>
                    
                    <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">가맹금액 : ${FranchiseAmount}</h3>
                    </div>
                    
                    <div data-v-5ee806c3="" data-v-581bdaec="">
                        <h3 data-v-581bdaec="" data-v-5ee806c3="">합계금액 : ${totalAmount}</h3>
                    </div>
                    
                     <hr color="black" width="300"><br><br>
                    
                    
                    <button data-v-0a6aebaa="" data-v-581bdaec=""
                        style="width: 40%; cursor: pointer;" onclick="closeWindow()"
                        class="btn full solid disabled">닫기</button>


        </div>
    </div>
    
    
    <script>
        function closeWindow() {
        window.close();
    }
    </script>   
    
</body>

</html>    
    
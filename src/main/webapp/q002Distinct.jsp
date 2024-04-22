<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "dao.*" %>
<%
	ArrayList<Integer> list = EmpDAO.selectDeptNoList();
	ArrayList<HashMap<String,Integer>> listCnt = EmpDAO.selectDeptNoCntList();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
	<select name= "deptNo">
		<option value = "">:::선택:::</option>
		<%
			for (Integer i : list ){
		%>
			<option value = "<%=i%>"><%=i %></option>
		<%		
			}
		%>
		
	</select>
	<h1>※ Distinct 대신 group by를 사용해야만 하는 경우</h1>
	<select name = "dept">
		<option value = "">:::선택:::</option>
		<%
			for(HashMap<String, Integer> m : listCnt){
		%>
				<option value = '<%=m.get("deptNo")%>'>
					<%=m.get("deptNo") %>
					(<%=m.get("cnt") %>명)
				</option>
		<%		
			}
		%>
	</select>
		
</body>
</html>
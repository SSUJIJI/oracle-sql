<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%
	ArrayList<HashMap<String,String>> caseList = EmpDAO.selectJobCaseList();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
<h1>case 식을 이용한 정렬</h1>
<table border = 1>
<%
	for(HashMap<String,String> m : caseList){
%>
	<tr>
		<td>
			<%=m.get("ename") %>
		</td>
		<td>
			<%=m.get("job") %>
		</td>
	</tr>
	
<%
	}
%>
</table>
</body>
</html>
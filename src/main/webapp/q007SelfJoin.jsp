<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%
	ArrayList<HashMap<String,Object>> selfList = EmpDAO.selectSelfJoin();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
<h1>SELf JOIN</h1>
<table border = 1>
	<tr>
		<th>empno</th>
		<th>ename</th>
		<th>grade</th>
		<th>mgrName</th>
		<th>mgrGrade</th>
	</tr>
	<%
		for(HashMap<String,Object> m : selfList){
	%>
			<tr>
				<td><%=(Integer)(m.get("empno")) %></td>
				<td><%=(String)(m.get("ename")) %></td>
				<td>
				<%
					for(int i=0; i<(Integer)(m.get("grade")); i=i+1){
				%>
						&#128420;		
				<%
					}
				%>
				</td>
				<td><%=(String)(m.get("mgrName")) %></td>
				<td>
				<%
					for(int i=0; i<(Integer)(m.get("mgrGrade")); i=i+1){
				%>
						&#128420;		
				<%
					}
				%>
				</td>
			</tr>
	<%		
		}
	%>


</table>
</body>
</html>
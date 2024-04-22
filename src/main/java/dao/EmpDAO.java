package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import vo.Emp;

public class EmpDAO {
	//q003Case.jsp
	public static ArrayList<HashMap<String,String>> selectJobCaseList() throws Exception{
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		String sql = "select ename,"
				+ "        job,"
				+ "        CASE "
				+ "        WHEN job = 'PRESIDENT' Then '빨강'"
				+ "        WHEN job = 'MANAGER' Then '주황'"
				+ "        WHEN job = 'ANALYST' Then '노랑'"
				+ "        WHEN job = 'CLERK' THEN '초록'"
				+ "        ELSE '파랑' END color /*3)*/"
				+ " from emp"
				+ " order by (case"
				+ "        when color = '빨강' then 1"
				+ "        when color = '주황' then 2"
				+ "        when color = '노랑' then 3"
				+ "        when color = '초록' then 4"
				+ "        else 5 end) asc";
		
		Connection conn = DBHelper.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String,String> m = new HashMap<String,String>();
			m.put("ename", rs.getString("ename"));
			m.put("job", rs.getString("job"));
			m.put("color", rs.getString("color"));
			list.add(m);
		}
		
		conn.close();
		return list;
				
	}
	
	// DEPTNO 뒤에 부서별 인원 같이 조회하는 메서드
	public static ArrayList<HashMap<String,Integer>> selectDeptNoCntList() throws Exception{
		ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String,Integer>>();
		
		String sql = "select deptno deptNo, count(*) cnt"
				+ " From emp"
				+ " where deptno is not null"
				+ " group by deptno"
				+ " order by deptno asc";
		
		Connection conn = DBHelper.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			HashMap<String,Integer> m = new HashMap<String, Integer>();
			m.put("deptNo", rs.getInt("deptNo"));
			m.put("cnt", rs.getInt("cnt"));
			list.add(m);
		}
		
		conn.close();
		return list;
	}
	
	// 중복을 제외한 DEOTNO 목록을 출력하는 메서드
	public static ArrayList<Integer> selectDeptNoList() throws Exception {
		ArrayList<Integer> list = new ArrayList<>();
		
		Connection conn =DBHelper.getConnection();
		String sql = "select distinct deptno deptNo"
				+ " from emp"
				+ " where deptno is not null"
				+ " order by deptno asc";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Integer i = rs.getInt("deptNo"); // Auto Boxing 
			list.add(i);
		}
		
		conn.close();
		return list;
		
	}
	
	
	
	
	// 조인으로 Map을 사용하는 경우
	public static ArrayList<HashMap<String,Object>> selectEmpAndDeptList() throws Exception{
		ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		
		Connection conn = DBHelper.getConnection();
		String sql = "Select"
				+ " emp.empno empNo, emp.ename ename, emp.deptno deptNo,"
				+ " dept.dname dname"
				+ " from emp inner join dept"
				+ " ON emp.deptno = dept.deptno";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String,Object>();
			m.put("empNo", rs.getInt("empNo"));
			m.put("ename", rs.getString("ename"));
			m.put("deptNo", rs.getInt("deptNo"));
			m.put("dname", rs.getString("dname"));
			list.add(m);
		}
		return list;
	}
	
	
	
	//VO사용
	public static ArrayList<Emp> selectEmpList() throws Exception {
		ArrayList<Emp> list = new ArrayList<>();// Dept[]과 같다 
		
		String sql = "select empno empNo,"
				+ "ename, sal "
				+ " from emp";
		Connection conn = DBHelper.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEmpno(rs.getInt("empNo"));
			e.setEname(rs.getString("ename"));
			e.setSal(rs.getDouble("sal"));
			list.add(e);
		}
		return list;
	}
}

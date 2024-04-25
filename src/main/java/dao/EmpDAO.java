package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import vo.Emp;

public class EmpDAO {
	//q007SelfJoin.jsp
	public static ArrayList<HashMap<String,Object>> selectSelfJoin() throws Exception {
		ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		
		Connection conn = DBHelper.getConnection();
		String sql = "select"
				+ " e1.empno"
				+ ", e1.ename"
				+ ", e1.grade"
				+ ", nvl(e2.ename, '관리자없음') mgrName"
				+ ", nvl(e2.grade,0) mgrGrade"
				+ " from emp e1 left outer join emp e2"
				+ " on e1.mgr = e2.empno"
				+ " order by e1.empno asc";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String,Object> m = new HashMap<String,Object>();
			m.put("empno", rs.getInt("empno"));
			m.put("ename", rs.getString("ename"));
			m.put("grade", rs.getInt("grade"));
			m.put("mgrName", rs.getString("mgrName"));
			m.put("mgrGrade", rs.getInt("mgrGrade"));
			list.add(m);
		}
		conn.close();
		return list;
	}
	//q006GroupBy.jsp
	public static ArrayList<HashMap<String,Integer>> selectEmpSalStats()
	throws Exception{
		ArrayList<HashMap<String,Integer>> list = new ArrayList<>();
		
		Connection conn = DBHelper.getConnection();
		String sql = "select"
				+ " grade"
				+ ", count(*) count"
				+ ", sum(sal) sum"
				+ ", avg(sal) avg"
				+ ", max(sal) max"
				+ ", min(sal) min"
				+ " from emp"
				+ " group by grade"
				+ " order by grade asc";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String,Integer> m = new HashMap<String,Integer>();
			m.put("grade", rs.getInt("grade"));
			m.put("count", rs.getInt("count"));
			m.put("sum", rs.getInt("sum"));
			m.put("avg", rs.getInt("avg"));
			m.put("max", rs.getInt("max"));
			m.put("min", rs.getInt("min"));
			list.add(m);
		}
		conn.close();
		return list;
	}
	//q005OrderBy.jsp
	public static ArrayList<Emp> selectEmpListSort(String col, String sort) throws Exception{
		//매개값 디버깅
		System.out.println(col + "<--EmpDAO.selectEmpListSort param col");
		System.out.println(sort + "<--EmpDAO.selectEmpListSort param sort"); 
		
		ArrayList<Emp> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		
		/*
		 * 동적 쿼리(쿼리 문자열이 매개값에 분기되어 차이가 나는 경우)
		 * 없다
		 * empno asc
		 * empno desc
		 * ename asc 
		 * ename desc
		 */
		
		String sql = "select empno, ename"
				+ " from emp";
		if(col != null && sort != null) {
			sql = sql + " order by " +col+" " + sort;
		}
		PreparedStatement stmt = conn.prepareStatement(sql);
		System.out.println(stmt);
		
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEmpno(rs.getInt("empno"));
			e.setEname(rs.getString("ename"));
			list.add(e);
		}
		
		
		conn.close();
		return list;
	}
	//q004WhereIn.jsp
	public static ArrayList<Emp> selectEmpListByGrade(ArrayList<Integer> ckList) throws Exception {
		ArrayList<Emp> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		String sql = "select ename, grade"
				+ " from emp"
				+ "	where grade in ";
		PreparedStatement stmt = null;
		
		if(ckList.size() == 1 ) {
			sql = sql + "(?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,ckList.get(0));
		}else if(ckList.size() == 2) {
			sql = sql + "(?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,ckList.get(0));
			stmt.setInt(2,ckList.get(1));
		}else if(ckList.size() == 3){
			sql = sql + "(?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,ckList.get(0));
			stmt.setInt(2,ckList.get(1));
			stmt.setInt(3,ckList.get(2));
		}else if(ckList.size() == 4) {
			sql = sql + "(?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,ckList.get(0));
			stmt.setInt(2,ckList.get(1));
			stmt.setInt(3,ckList.get(2));
			stmt.setInt(4,ckList.get(3));
		}else if(ckList.size() == 5) {
			sql = sql + "(?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,ckList.get(0));
			stmt.setInt(2,ckList.get(1));
			stmt.setInt(3,ckList.get(2));
			stmt.setInt(4,ckList.get(3));
			stmt.setInt(5,ckList.get(4));
		}
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEname(rs.getString("ename"));
			e.setGrade(rs.getInt("grade"));
			list.add(e);
		}
		conn.close();
		return list;
	}
	
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

package dao;
import java.sql.*;
import java.util.*;
import vo.Dept;

public class DeptDAO {
	//Map 사용
	public static ArrayList<HashMap<String,Object>> selectDeptListOnOff() throws Exception {
		ArrayList<HashMap<String,Object>> list = new ArrayList<>();// Dept[]과 같다 
		
		Connection conn = DBHelper.getConnection();
		String sql = "select"
				+ " deptno deptNo, dname, loc, 'ON' onOff"
				+ " from dept";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String,Object> m = new HashMap<>();
			m.put("deptNo", rs.getInt("deptNo"));
			m.put("dname", rs.getString("dname"));
			m.put("loc", rs.getString("loc"));
			m.put("onOff", rs.getString("onOff"));
			list.add(m);
		}
		return list;
	}
	//VO 사용
	public static ArrayList<Dept> selectDeptList() throws Exception {
		ArrayList<Dept> list = new ArrayList<>();// Dept[]과 같다 
		
		String sql = "select * from dept";
		Connection conn = DBHelper.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Dept d = new Dept();
			d.deptNo = rs.getInt("deptNo");
			d.dname = rs.getString("dname");
			d.loc = rs.getString("loc");
			list.add(d);
		}
		return list;
	}
}

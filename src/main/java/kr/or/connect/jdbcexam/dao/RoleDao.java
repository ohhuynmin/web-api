package kr.or.connect.jdbcexam.dao;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import kr.or.connect.jdbcexam.dto.Role;

public class RoleDao {
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=UTC";
	private static String dbUser = "connectuser";
	private static String dbpasswd = "connect123!@#";

	public List<Role> getRoles() {
		List<Role> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT description, role_id FROM role order by role_id desc";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery();) {

				while (rs.next())
					list.add(new Role(rs.getInt("role_id"),rs.getString(1)));
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.reverse(list);
		return list;
	}

	public void updateRole(Role role) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "update role set description = ? where role_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, role.getDescription());
			ps.setInt(2, role.getRoleId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void deleteRole(Role role) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "DELETE FROM role WHERE role_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, role.getRoleId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int addRole(Role role) {
		int insertCount = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "INSERT INTO role (role_id, description) VALUES (?,?)";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, role.getRoleId());
			ps.setString(2, role.getDescription());

			insertCount = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return insertCount;
	}

	public Role getRole(Integer roleId) {
		Role role = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "SELECT description,role_id FROM role WHERE role_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roleId);
			rs = ps.executeQuery();

			if (rs.next()) {
				String des = rs.getString(1);
				int id = rs.getInt("role_id");
				role = new Role(id, des);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return role;
	}
}

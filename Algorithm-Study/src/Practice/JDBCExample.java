package pknu.it;

import java.sql.*;

public class JDBCExample {

	public static void main(String[] args) {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@db.pknu.ac.kr:1521:xe", "USER",
					"PASSWD");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from student");

			while (rs.next()) {
				int no = rs.getInt("sno"); // getInt(1)
				String name = rs.getString("sname"); // getString(2)
				int year = rs.getInt("year"); // getInt(3)
				String dept = rs.getString(4); // getString("dept")
				System.out.format("�й�: %d �̸�: %s �г�:%d �а�:%s \n", no, name, year, dept);
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException se) {
			System.err.println("SQL ������ ������ �߻��߽��ϴ�." + se.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.err.println("����̹� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
		} finally {

		}

	}

}

package xyz.theefficientcodes.sampledata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

public class SampleDataInsertion {
	public static void main(String...strings){
		String url = "jdbc:postgresql://localhost:5432/postgres";
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","postgres");
		try {
			Connection conn = DriverManager.getConnection(url, props);
			Statement stmt=conn.createStatement();
			Random random=new Random();
			for(int id=1;id<=1000;++id){
				int universe=random.nextInt(100);
				int family_id=random.nextInt(20);
				int power=random.nextInt(3)*(int)Math.pow(-1,random.nextInt());
				stmt.executeUpdate("INSERT INTO multiverse("+
									"universe, family_id, power)"+
									"VALUES ("+universe+","+family_id+","+power+");");
				System.out.println(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



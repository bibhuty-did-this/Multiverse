package xyz.theefficientcodes.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import xyz.theefficientcodes.model.Family;
import xyz.theefficientcodes.model.FamilyBalance;
import xyz.theefficientcodes.model.Person;

public class DataService {
	private String URL;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public DataService() throws SQLException{
		URL="jdbc:postgresql://localhost/postgres";
		Properties properties = new Properties();
		properties.setProperty("user","postgres");
		properties.setProperty("password","postgres");
		connection = DriverManager.getConnection(URL, properties);
		statement=connection.createStatement();
		
	}
	
	public List<Family> getFamiliesOfParticularUniverse(String universe) throws SQLException{		
		String SQL="SELECT UNIVERSE, FAMILY_ID,SUM(POWER) "
				+ "FROM MULTIVERSE "
				+ "WHERE UNIVERSE = "+universe
				+ "GROUP BY UNIVERSE, FAMILY_ID "
				+ "ORDER BY FAMILY_ID";
		resultSet=statement.executeQuery(SQL);
		List<Family> multiverseData=new ArrayList<>();
		while(resultSet.next()){
			int UNIVERSE=resultSet.getInt("UNIVERSE");
			int FAMILY_ID=resultSet.getInt("FAMILY_ID");
			int POWER=resultSet.getInt(3);
			multiverseData.add(new Family(UNIVERSE, FAMILY_ID, POWER));
		}
		return multiverseData;
	}
	
	public Person insertData(Person person) throws SQLException{
		int universe=person.getUNIVERSE();
		int family_id=person.getFAMILY_ID();
		int power=person.getPOWER();
		String sql="INSERT INTO multiverse("+
				"universe, family_id, power)"+
				"VALUES ("+universe+","+family_id+","+power+");";
		statement.executeUpdate(sql);
		String SQL="SELECT MAX(ID)"
				+ "FROM MULTIVERSE ";
		resultSet=statement.executeQuery(SQL);
		if(resultSet.next())person.setID(resultSet.getInt(1));
		return person;
	}
	
	public List<Person> bulkInsertData(String jsonString) throws ParseException, SQLException{
		JSONParser parser=new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
		List<Person> persons=new ArrayList<Person>();
		for(int i=0; i<jsonArray.size(); i++)
        {
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            int FAMILY_ID=Integer.parseInt(jsonObject.get("FAMILY_ID").toString());
            int POWER=Integer.parseInt(jsonObject.get("POWER").toString());
            int UNIVERSE=Integer.parseInt(jsonObject.get("UNIVERSE").toString());
            Person person=new Person(i, UNIVERSE, FAMILY_ID, POWER);
            persons.add(insertData(person));
        }
		return persons;
	}
	
	public Person getData(int id) throws SQLException{
		String sql="SELECT * FROM MULTIVERSE WHERE ID="+id+";";
		resultSet=statement.executeQuery(sql);
		int universe = 0,family_id = 0,power = 0;
		if(resultSet.next()){
			universe=resultSet.getInt("UNIVERSE");
			family_id=resultSet.getInt("FAMILY_ID");
			power=resultSet.getInt("POWER");
		}
		Person person=new Person(id, universe, family_id, power);
		return person;
		
	}
	
	public List<FamilyBalance> checkFamilyBalance() throws SQLException{
		List<FamilyBalance> list=new ArrayList<>();
		ArrayList<Integer> universeSet=new ArrayList<>();
		ArrayList<Integer> familySet=new ArrayList<>();
		universeSet=totalUniverseSet();
		familySet=totalFamilySet();
		int universeSize=universeSet.size();
		for(int family:familySet){
			int totalPowerOfFamily=totalPowerOfFamily(family);
			if(totalPowerOfFamily%universeSize==0){
				int each=totalPowerOfFamily/universeSize;
				if(isBalancedFamily(family, each))
					list.add(new FamilyBalance(family, "balanced"));
				else 
					list.add(new FamilyBalance(family, "unbalanced"));
			}else list.add(new FamilyBalance(family, "unbalanced"));
		}
		return list;
	}
	
	public List<Person> balanceTheUniverse() throws SQLException{
		List<Person> addedPersons=new ArrayList<>();
		ArrayList<Integer> universeSet=new ArrayList<>();
		ArrayList<Integer> familySet=new ArrayList<>();
		universeSet=totalUniverseSet();
		familySet=totalFamilySet();
		int universeSize=universeSet.size();
		for(int family:familySet){
			int totalPowerOfFamily=totalPowerOfFamily(family);
			int each=totalPowerOfFamily/universeSize;
			for(int universe:universeSet){
				int familyPower=familyPowerInUniverse(family, universe);
				if(familyPower!=each){
					Person person=new Person(0,universe,family,each-familyPower);
					addedPersons.add(insertData(person));
				}
			}
		}
		
		return addedPersons;
	}	
	
	public int familyPowerInUniverse(int family,int universe) throws SQLException{
		String sql="SELECT SUM(POWER) FROM MULTIVERSE WHERE FAMILY_ID="+family+" AND UNIVERSE="+universe+";";
		resultSet=statement.executeQuery(sql);
		int power=0;
		if(resultSet.next())
			power=resultSet.getInt(1);
		return power;
	}	
	
	public boolean isBalancedFamily(int family_id,int each) throws SQLException{
		String sql="SELECT DISTINCT SUM(POWER) FROM MULTIVERSE WHERE FAMILY_ID=+"+family_id +" GROUP BY UNIVERSE;";
		resultSet=statement.executeQuery(sql);
		while(resultSet.next()){
			if(resultSet.getInt(1)!=each)
				return false;
		}
		return true;
	}
	
	public int totalPowerOfFamily(int family_id) throws SQLException{
		String sql="SELECT SUM(POWER) FROM MULTIVERSE WHERE FAMILY_ID = "+family_id+";";
		resultSet=statement.executeQuery(sql);
		int power = 0;
		if(resultSet.next())
			power=resultSet.getInt(1);
		return power;
	}
	
	public ArrayList<Integer> totalUniverseSet() throws SQLException{
		ArrayList<Integer> universeSet=new ArrayList<>();
		String sql="SELECT DISTINCT UNIVERSE FROM MULTIVERSE;";
		resultSet=statement.executeQuery(sql);
		while(resultSet.next())
			universeSet.add(resultSet.getInt(1));
		return universeSet;
	}
	
	public ArrayList<Integer> totalFamilySet() throws SQLException{
		ArrayList<Integer> familySet=new ArrayList<>();
		String sql="SELECT DISTINCT FAMILY_ID FROM MULTIVERSE;";
		resultSet=statement.executeQuery(sql);
		while(resultSet.next())
			familySet.add(resultSet.getInt(1));
		return familySet;
	}
}

package xyz.theefficientcodes.resources;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import xyz.theefficientcodes.exception.DataNotFoundException;
import xyz.theefficientcodes.model.Family;
import xyz.theefficientcodes.model.FamilyBalance;
import xyz.theefficientcodes.model.Person;
import xyz.theefficientcodes.service.DataService;

@Path("postgres")
@Produces(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
@Consumes(MediaType.APPLICATION_JSON)
public class DataResource {		 

	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!!";
    }
	
	@GET
	@Path("/universes/{universe}")
	public List<Family> getFamilyDataFromAParticularUniverse(@PathParam("universe")String universe) throws SQLException{		
		List<Family> persons=new DataService().getFamiliesOfParticularUniverse(universe);
		if(persons.size()==0){
			throw new DataNotFoundException("Universe with identifier "+universe+" doesn't exists");
		}
		return persons;
	}

	@GET
	@Path("/ids/{id}")
	public Person getPerson(@PathParam("id")int id, @Context UriInfo uriInfo) throws SQLException{		
		Person person=new DataService().getData(id);
		String uri=uriInfo.getAbsolutePathBuilder()
				.build()
				.toString();
		if(person.getID()<0){
			throw new DataNotFoundException("No person exist with id : "+id);
		}
		person.setLink(uri);
		return person;
	}
	
	@POST
	@Path("/objectInsert")
	public Response addPerson(Person person,@Context UriInfo uriInfo) throws SQLException{
		Person createdPerson=new DataService().insertData(person);
		int newId=createdPerson.getID();
		URI uri=uriInfo.getAbsolutePathBuilder().path(Integer.toString(newId)).build();
		return Response
				.created(uri)
				.entity(createdPerson)
				.build();
	}
	
	@POST
	@Path("/arrayInsert")
	public List<Person>  initialInsert(String jsonString) throws Exception{
		List<Person> persons=new DataService().bulkInsertData(jsonString);
		return persons;
	}
	
	@GET
	@Path("/familyBalanceStatus")
	public List<FamilyBalance> familyBalanceStatus() throws SQLException{
		List<FamilyBalance> list=new DataService().checkFamilyBalance();
		return list;
	}
	@GET
	@Path("/balanceTheUniverse")
	public List<Person> balanceTheUniverse() throws SQLException{
		List<Person> list=new DataService().balanceTheUniverse();
		return list;
	}

}

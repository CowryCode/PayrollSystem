/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.resources;

import com.cowrycode.entities.Employee;
import com.cowrycode.service.QueryService;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author JavaEE
 */
@Path("employees")
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResource {
    @Inject
    Logger logger;
    
    @Context
    private UriInfo uriInfo;
     //employees GET List of employees
    //employees/{id} GET
    //employees POST - employees/98
    @Inject
    QueryService queryService;
    
    public Response getEmployees(@Context HttpHeaders httpHeaders){
        //        Collection<Employee> employees = new ArrayList<>();
//
//        Employee employee = new Employee();
//        employee.setFullName("John Mahama");
//        employee.setSocialSecurityNumber("SSF12343");
//        employee.setDateOfBirth(LocalDate.of(1986, Month.APRIL, 10));
//        employee.setBasicSalary(new BigDecimal(60909));
//        employee.setHiredDate(LocalDate.of(2018, Month.JANUARY, 24));
//
//
//        Employee employee1 = new Employee();
//        employee1.setFullName("Donald Trump");
//        employee1.setSocialSecurityNumber("SKJBHJSBDKJ");
//        employee1.setDateOfBirth(LocalDate.of(1900, Month.JULY, 31));
//        employee1.setBasicSalary(new BigDecimal(250000));
//        employee1.setHiredDate(LocalDate.of(2016, Month.NOVEMBER, 7));
//
//        employees.add(employee);
//        employees.add(employee1);
//        MediaType mediaType = httpHeaders.getAcceptableMediaTypes().get(0);
       return Response.ok(queryService.getEmployees()).status(Response.Status.OK).build();
        
    }
    
    @GET
    @Path("employee/{id: \\d+}")//api/v1/employees/employee/1  GET Method {username: }@{domain: }.{company}
    public Response getEmployeeById(@PathParam("id") @DefaultValue("0") Long id, @Context Request request){
        Employee employee = queryService.findEmployeeById(id);
        //Implementing Cach
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(1000);
        
        EntityTag entityTag = new EntityTag(UUID.randomUUID().toString());
        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(entityTag);
        
        if(responseBuilder != null){
            responseBuilder.cacheControl(cacheControl);
            return responseBuilder.build();
        }
        responseBuilder = Response.ok(employee);
        responseBuilder.tag(entityTag);
        responseBuilder.cacheControl(cacheControl);
        return responseBuilder.build();
        //        return Response.ok().status(Response.Status.OK).build();
    }
    
    @GET
    @Path("id")//?id=27 /id?id=95
    public Employee findEmployeeById(@QueryParam("id") @DefaultValue("0") Long id){
        return queryService.findEmployeeById(id);
    }
    
    
    @POST //api/v1/employees POST Request
    @Path("employees") //api/v1/employees/new - POST Request
//    @Consumes("application/xml")
    public Response createEmployee(@Valid Employee employee) {
        persistenceService.saveEmployee(employee);

        URI uri = uriInfo.getAbsolutePathBuilder().path(employee.getId().toString()).build();
        return Response.created(uri).status(Response.Status.CREATED).build();
    }


    @POST
    @Path("upload") //employees/upload?id=9
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpeg", "image/jpg"})
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadPicture(File picture, @QueryParam("id") @NotNull Long id) {

        Employee employee = queryService.findEmployeeById(id);

        try (Reader reader = new FileReader(picture)) {

            employee.setPicture(Files.readAllBytes(Paths.get(picture.toURI())));
            persistenceService.saveEmployee(employee);

            int totalsize = 0;
            int count = 0;
            final char[] buffer = new char[256];
            while ((count = reader.read(buffer)) != -1) {
                totalsize += count;
            }
            return Response.ok(totalsize).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }


    @GET
    @Path("download") //employees/download?id=9
    @Produces({MediaType.APPLICATION_OCTET_STREAM, "image/jpg", "image/png", "image/jpeg"})
    public Response getEmployeePicture(@QueryParam("id") @NotNull Long id) throws IOException {


        NewCookie userId = new NewCookie("userId", id.toString());

        Employee employee = queryService.findEmployeeById(id);
        if (employee != null) {
            return Response.ok().entity(Files.write(Paths.get("pic.png"), employee.getPicture()).toFile()).cookie(userId).build();
        }

        return Response.noContent().build();
    }


    @DELETE
    @Path("{id: \\d+}") //api/v1/employees/34 - DELETE
    public Response terminateEmployee(@PathParam("id") @NotNull Long id) {

        return Response.ok().build();
    }
    
}

package edu.ap.jaxrs;

import java.io.*;
import java.util.*;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.xml.bind.*;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;




@RequestScoped
@Path("/products")
public class ProductResource {
	
	public static final String JSON_FILE="/Users/UITLEEN/Desktop/Webtech3/Product.json";
	
	
	
	@GET
	@Produces({"text/html"})
	public String getProductsHTML() throws IOException {
		
		String htmlString = "<html><body>";
		
		
		try {
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsXML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
			InputStream fis = new FileInputStream(JSON_FILE);
			JsonReader jsonReader = Json.createReader(fis);
			JsonObject jsonObject = jsonReader.readObject();
//			jsonObject.toString();
			jsonReader.close();
			fis.close();
			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller.unmarshal(fis);
			
			
//			File XMLfile = new File("/Users/UITLEEN/Desktop/Webtech3/Product.json");
			
			 Product emp = new Product();
	         
		        emp.setId(jsonObject.getInt("id"));
		        emp.setPrice(jsonObject.getString("price"));
		        emp.setName(jsonObject.getString("name"));
		        emp.setBrand(jsonObject.getString("brand"));
		        emp.setDescription(jsonObject.getString("description"));
			
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			for(Product product : listOfProducts) {
				
				htmlString += "Id : " + product.getId() + "<br>";
				htmlString += "Price : " + product.getPrice() + "<br>";
				htmlString += "Name : " + product.getName() + "<br>";
				htmlString += "Brand : " + product.getBrand() + "<br>";
				htmlString += "Description : " + product.getDescription() + "<br>";
				htmlString += "<br><br>";
			}
			
			
			
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
		return htmlString;
		
	}
	
	@GET
	@Produces({"application/json"})
	public String getProductsJSON() throws IOException {
		String jsonString = "{\"products\" : [";
		try {
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsXML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
//			File XMLfile = new File("/Users/UITLEEN/Desktop/Webtech3/Product.json");
			InputStream fis = new FileInputStream(JSON_FILE);
			JsonReader jsonReader = Json.createReader(fis);
			JsonObject jsonObject = jsonReader.readObject();
			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller.unmarshal(fis);
//			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller.unmarshal(XMLfile);
			
			Product emp = new Product();
	         
	        emp.setId(jsonObject.getInt("id"));
	        emp.setPrice(jsonObject.getString("price"));
	        emp.setName(jsonObject.getString("name"));
	        emp.setBrand(jsonObject.getString("brand"));
	        emp.setDescription(jsonObject.getString("description"));
			
			
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			for(Product product : listOfProducts) {
				jsonString += "{\"name\" : \"" + product.getName() + "\",";
				jsonString += "\"id\" : " + product.getId() + ",";
				jsonString += "\"brand\" : \"" + product.getBrand() + "\",";
				jsonString += "\"description\" : \"" + product.getDescription() + "\",";
				jsonString += "\"price\" : " + product.getPrice() + "},";
			}
			jsonString = jsonString.substring(0, jsonString.length()-1);
			jsonString += "]}";
			
			jsonReader.close();
			fis.close();
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
		return jsonString;
	}
	
	@GET
	@Produces({"text/xml"})
	public String getProductsXML() {
		String content = "";
//		File XMLfile = new File("/Users/UITLEEN/Desktop/Webtech3/Product.json");
		try {
			content = new Scanner(XMLfile).useDelimiter("\\Z").next();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return content;
	}

	@GET
	@Path("/{name}")
	@Produces({"application/json"})
	public String getProductJSON(@PathParam("name") String name) throws IOException {
		String jsonString = "";
		try {
			// get all products
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsXML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
//			File XMLfile = new File("/Users/UITLEEN/Desktop/Webtech3/Product.json");
			InputStream fis = new FileInputStream(JSON_FILE);
			JsonReader jsonReader = Json.createReader(fis);
			JsonObject jsonObject = jsonReader.readObject();
			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller.unmarshal(fis);
//			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller.unmarshal(XMLfile);
			
			Product emp = new Product();
	         
	        emp.setId(jsonObject.getInt("id"));
	        emp.setPrice(jsonObject.getString("price"));
	        emp.setName(jsonObject.getString("name"));
	        emp.setBrand(jsonObject.getString("brand"));
	        emp.setDescription(jsonObject.getString("description"));
			
			
			
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			// look for the product, using the name
			for(Product product : listOfProducts) {
				if(name.equalsIgnoreCase(product.getName())) {
					jsonString += "{\"name\" : \"" + product.getName() + "\",";
					jsonString += "\"id\" : " + product.getId() + ",";
					jsonString += "\"brand\" : \"" + product.getBrand() + "\",";
					jsonString += "\"description\" : \"" + product.getDescription() + "\",";
					jsonString += "\"price\" : " + product.getPrice() + "}";
				}
				jsonReader.close();
				fis.close();
			}
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		   
		   
		}
		return jsonString;
	}
	
	@GET
	@Path("/{name}")
	@Produces({"text/json"})
	public String getProductXML(@PathParam("name") String name) throws IOException {
		String xmlString = "";
		try {
			// get all products
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsXML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
//			File XMLfile = new File("/Users/UITLEEN/Desktop/Webtech3/Product.json");
			InputStream fis = new FileInputStream(JSON_FILE);
			JsonReader jsonReader = Json.createReader(fis);
			JsonObject jsonObject = jsonReader.readObject();
			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller.unmarshal(fis);
//			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller.unmarshal(XMLfile);
			
			Product emp = new Product();
	         
	        emp.setId(jsonObject.getInt("id"));
	        emp.setPrice(jsonObject.getString("price"));
	        emp.setName(jsonObject.getString("name"));
	        emp.setBrand(jsonObject.getString("brand"));
	        emp.setDescription(jsonObject.getString("description"));
			
			
			
			
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			// look for the product, using the name
			for(Product product : listOfProducts) {
				if(name.equalsIgnoreCase(product.getName())) {
					JAXBContext jaxbContext2 = JAXBContext.newInstance(Product.class);
					Marshaller jaxbMarshaller = jaxbContext2.createMarshaller();
					StringWriter sw = new StringWriter();
					jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
					jaxbMarshaller.marshal(product, sw);
					xmlString = sw.toString();
				}
				
				jsonReader.close();
				fis.close();
			}
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
		return xmlString;
	}
	
	@POST
	@Consumes({"text/json"})
	public void processFromXML(String productXML) {
		
		/* newProductXML should look like this :
		 *  
		 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		 <product>
        	<brand>BRAND</brand>
        	<description>DESCRIPTION</description>
        	<id>123456</id>
        	<price>20.0</price>
        	<name>name</name>
        	<sku>SKU</sku>
		 </product>
		 */
		
		try {
			// get all products
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsXML.class);
			Unmarshaller jaxbUnmarshaller1 = jaxbContext1.createUnmarshaller();
			File XMLfile = new File("/Users/UITLEEN/Desktop/Webtech3/Product.json");
//			ProductsXML productsXML = (ProductsXML)jaxbUnmarshaller1.unmarshal(XMLfile);
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			// unmarshal new product
			JAXBContext jaxbContext2 = JAXBContext.newInstance(Product.class);
			Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
			StringReader reader = new StringReader(productXML);
			Product newProduct = (Product)jaxbUnmarshaller2.unmarshal(reader);
			
			// add product to existing product list 
			// and update list of products in  productsXML
			listOfProducts.add(newProduct);
			productsXML.setProducts(listOfProducts);
			
			// marshal the updated productsXML object
			Marshaller jaxbMarshaller = jaxbContext1.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(productsXML, XMLfile);
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
	}
}
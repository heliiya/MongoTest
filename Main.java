package main;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {
	
	private final static String DATABASE_NAME = "JavaUser";
	private final static String COLLECTION_NAME = "users";
	private final static String USER_NAME_KEY = "username";
	private final static String PASSWORD_KEY = "password";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		MongoClient client = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = client.getDatabase(DATABASE_NAME);
		MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
		
		System.out.println("Are you registered?");
		String input = sc.next();
		if("yes".equals(input)){
			// login ...
			System.out.println("Enter username:");
			String userName = sc.next();
			System.out.println("Enter password:");
			String password = sc.next();
			Document userFound = collection.find(and(eq(USER_NAME_KEY, userName), eq(PASSWORD_KEY, password))).first();
			if(userFound == null){
				System.out.println("your username or password is wrong.");
			}else{
				System.out.println("Welcome back");
			}
		}else{
			// register ...
			System.out.println("Enter username to register:");
			String userName = sc.next();
			System.out.println("Make new password:");
			String password = sc.next();
			Document userFound = collection.find(and(eq(USER_NAME_KEY, userName), eq(PASSWORD_KEY, password))).first();
			if(userFound == null){
				Document userDocument = new Document();
				userDocument.append(USER_NAME_KEY, userName);
				userDocument.append(PASSWORD_KEY, password);
				collection.insertOne(userDocument);
				System.out.println("Welcome");
			}else{
				System.out.println("You have already registered.");
			}
		}
	}
}
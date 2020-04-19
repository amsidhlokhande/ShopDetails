This project for managing Shop and their Address using GEO longitude and latitude.

Minimum requirements to set-up this project are:
1)Java 8 <BR> 
2)Maven 3.0 <BR>
3)Gradle 4.0 <BR>

**************************************************************
Import the project from GitHub to your local machine.

We can setup this project by two ways
1) Using Gradle:
  open the command prompt and goto the project import directory and run the following commands.  
   
   >grale init

   >gradle clean bootRun
   
   <BR>That is it. Your project is up and running.

2) Using Maven:
      open the command prompt and go to the project import directory and run the following commands.  
   
   >mvn clean spring-boot:run
   
   <BR> Now your project up and running now.
************************************************************
We can test the application with the help of following url.

To get All shops with individual shop and their address links.

http://localhost:8181/ShopDetails/shops

Get shop details with with address link by shopId 
http://localhost:8181/ShopDetails/shops/{shopId}

Get all the shops near to a particular pincode
http://localhost:8181/ShopDetails/addresses/pincode/{pinCode}

Get all address
http://localhost:8181/ShopDetails/addresses

For adding a shop along with address we should follow two step.
 1) Add a shop by giving a addressId
     http://localhost:8181/ShopDetails/shops -- Http Post method
      
     sample data-
       {
		"shopId": 25,
		"shopName": "Lakeside Chalet, Mumbai - Marriott Executive Apartments",
		"addressId": 25
	  }
	  
	  if address not saved then shop added with empty address.
 2) add address with that addressId.
      --This call has GEO calling service to get longitude and latitude of a given address 
      means the address should have a geological location.
      http://maps.googleapis.com/maps/api/geocode/json?address='Lakeside Chalet, Mumbai - Marriott Executive Apartments'&sensor=false
      
      
      http://localhost:8181/ShopDetails/addresses  --Http Post Method.
      {
		"addressId": 25,
		"location": "#2 & 3B, Near Chinmayanand Ashram, Powai, Mumbai, Maharashtra 400087, India",
		"shopId": 25
	  }
	  
	  Now you can search the shop by shopId.
      
      
 
  

  

# Swift Codes Docker Image

This repository contains a Docker image for the `swift-codes` application. You can easily pull, run, and test the image on your local machine. Below are the steps to do that.

## Prerequisites

Before you begin, make sure you have the following installed:

- [Docker](https://www.docker.com/get-started) - Platform to build, ship, and run applications inside containers.

## Steps to Download, Run, and Test the Docker Image

### 1. **Pull the Docker Image**

First, you need to pull the Docker image from GitHub Container Registry. Run the following command in your terminal:

docker pull ghcr.io/adpawel/swift-codes:latest

### 2. **Run the Docker Image**

After successfully pulling the image, you can run it with the following command:

docker run -p 8080:8080 ghcr.io/adpawel/swift-codes:latest

This command starts the swift-codes application inside a Docker container and maps port 8080 on your local machine to port 8080 inside the container.
### 3. **Test the Application**

Once the application is running, you can test it by using the following endpoints. These can be accessed through curl or any HTTP client:
a. Get Swift Codes by Country ISO2 Code

Fetch the list of swift codes for a given country by its ISO2 code:

curl http://localhost:8080/v1/swift-codes/country/{countryISO2code}

Replace {countryISO2code} with the appropriate two-letter country code (e.g., US for the United States, GB for Great Britain).
b. Create a New Swift Code

To create a new swift code, use the following POST request with the required JSON body:

curl -X POST http://localhost:8080/v1/swift-codes \
-H "Content-Type: application/json" \
-d '{"address": "{address}", "bankName": "{name}", "countryISO2": "{countryISO2code}", "countryName": "{countryName}", "headquarter": "{true/false}", "swiftCode": "{swift-code}"}'

Replace the placeholders with the actual values:

    {address}: Bank address
    {name}: Bank name
    {countryISO2code}: Two-letter country code (e.g., US)
    {countryName}: Full country name (e.g., United States)
    {true/false}: Indicates whether the bank is the headquarter (true or false)
    {swift-code}: The swift code for the bank (e.g., BOFAUS3N)

c. Get Swift Code by Code

Fetch details about a specific swift code:

curl http://localhost:8080/v1/swift-codes/{swift-code}

Replace {swift-code} with the swift code you want to fetch information for (e.g., BOFAUS3N).
d. Delete a Swift Code

To delete a swift code from the system, use the following DELETE request:

curl -X DELETE http://localhost:8080/v1/swift-codes/{swift-code}

Replace {swift-code} with the swift code you want to delete.
### 4. **Stop the Docker Container**

Once you are done testing, you can stop the Docker container by pressing Ctrl+C in your terminal, or you can remove it with the following commands:

docker ps  # Find the container ID
docker stop <container_id>  # Stop the container
docker rm <container_id>  # Remove the container














# Swift Codes Docker Image

This repository contains a Docker image for the `swift-codes` application. You can easily pull, run, and test the image on your local machine. Below are the steps to do that.

## Prerequisites

Before you begin, make sure you have the following installed:

- [Docker](https://www.docker.com/get-started) - Platform to build, ship, and run applications inside containers.
- [Docker Compose](https://docs.docker.com/compose/) - Tool for defining and running multi-container Docker applications.

---

## Steps to Download, Run, and Test the Docker Image

### **1. Clone the Repository (Optional)**

If you have access to this repository, you can clone it to get the `docker-compose.yml` file:

git clone https://github.com/adpawel/swift-codes.git
cd swift-codes

If you do not have access to the repository, create a docker-compose.yml file manually (see below).
### **2. Pull the Docker Image**

First, you need to pull the Docker image from GitHub Container Registry. Run the following command in your terminal:

docker pull ghcr.io/adpawel/swift-codes:latest

### **3. Run the Application with a Database**

The swift-codes application requires a PostgreSQL database. To start both the application and the database, use Docker Compose.
Option 1: Using docker-compose.yml (Recommended)

If you have the docker-compose.yml file, simply run:

docker-compose up -d

This command will:

    Start a PostgreSQL database container (db service).
    Start the swift-codes application (app service) and link it to the database.

Option 2: Manually Running PostgreSQL and the Application

If you don’t have docker-compose.yml, you can start PostgreSQL manually:

docker run -d \
  --name swift-db \
  -e POSTGRES_USER=user \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=swiftcodes \
  -p 5432:5432 \
  postgres:15

Then, run the application while passing the database connection details:

docker run -p 8080:8080 \
  -e DATABASE_URL=postgres://user:password@swift-db:5432/swiftcodes \
  --network host \
  ghcr.io/adpawel/swift-codes:latest

### **4. Test the Application**

Once the application is running, you can test it using the following API endpoints:
a. Get Swift Codes by Country ISO2 Code

curl http://localhost:8080/v1/swift-codes/country/{countryISO2code}

Replace {countryISO2code} with a two-letter country code (e.g., US for the United States).
b. Create a New Swift Code

curl -X POST http://localhost:8080/v1/swift-codes \
  -H "Content-Type: application/json" \
  -d '{
    "address": "{address}",
    "bankName": "{name}",
    "countryISO2": "{countryISO2code}",
    "countryName": "{countryName}",
    "headquarter": "{true/false}",
    "swiftCode": "{swift-code}"
  }'

Replace placeholders with actual values.
c. Get Swift Code by Code

curl http://localhost:8080/v1/swift-codes/{swift-code}

Replace {swift-code} with the swift code you want to fetch.
d. Delete a Swift Code

curl -X DELETE http://localhost:8080/v1/swift-codes/{swift-code}

Replace {swift-code} with the code to delete.
### **5. Stop and Remove Containers**

To stop and remove the application and database, run:

docker-compose down

Or, if running without docker-compose:

docker stop swift-db
docker rm swift-db
docker stop <container_id>
docker rm <container_id>

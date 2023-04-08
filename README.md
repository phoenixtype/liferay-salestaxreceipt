# Sales Tax Receipt Generator

The Sales Tax Receipt Generator is a Java application that calculates the total cost of items, including applicable sales taxes. It supports tax-exempt items, imported items, and items with varying quantities. This application is designed to be easily extensible and configurable, following object-oriented design principles.

## How it works
The application reads input from the user, which includes item descriptions, prices, and quantities. It calculates the sales tax for each item, taking into consideration whether the item is imported and/or tax-exempt. The application then generates a receipt displaying the total cost of each item, the total sales tax, and the overall total cost.

### Key components
- SalesTaxApp: Main class responsible for reading user input and generating the receipt.
- TaxCalculator: Interface for calculating sales taxes.
- DefaultTaxCalculator: Default implementation of TaxCalculator based on the given configuration.
- Item: Class representing an item with a name, price, and quantity.
- Receipt: Class representing a receipt containing a list of items, total sales tax, and total cost.
- ReceiptGenerator: Class responsible for generating a Receipt object from a list of Item objects.
- Config: Class for reading and storing configuration values from a properties file (config.properties).
- ItemPriceProvider: Interface for providing item prices. This can be implemented to fetch prices from a database or an external service.

### Prerequisites
- Java Development Kit (JDK) 11 or later
- IntelliJ IDEA or another Java IDE (optional)
- Maven

### Testing Frameworks
- JUnit
- Mockito


### Running the application

1. Clone this repository or download the source code.

2. Open a terminal/command prompt in the project's root directory (where the pom.xml file is located).

3. Compile and package the application using the following command:
````
   mvn clean package
````
This command will create a JAR file named liferay-salestaxreceipt-1.0-SNAPSHOT.jar in the target folder.

4. Run the application using the following command:
````
    java -jar target/liferay-salestaxreceipt-1.0-SNAPSHOT.jar
````
5. Enter item descriptions, prices, and quantities line by line. Press Enter after each line. To finish the input and generate the receipt, press Enter on an empty line. Example input:

````
   1 book at 12.49
   1 music CD at 14.99
   1 chocolate bar at 0.85
````
6. The application will output the receipt, displaying the total cost of each item, the total sales tax, and the overall total cost.


## Customizing the application
To customize the application, you can modify the config.properties file in the src/main/resources folder. This file contains the following properties:

- basicTaxRate: The basic sales tax rate for non-exempt items (e.g., 0.10 for a 10% tax rate).
- importTaxRate: The import tax rate for imported items (e.g., 0.05 for a 5% tax rate).
- roundingFactor: The factor used for rounding up the calculated tax (e.g., 0.05 for rounding to the nearest 0.05).
You can also extend the application by implementing new TaxCalculator and ItemPriceProvider classes to customize the tax calculation logic and item price fetching mechanism, respectively.

## Containerization

1. create a Dockerfile and follow these steps:
    - Install the Java Development Kit (JDK) in the container.
    - Copy the project files into the container.
    - Build the project using Maven.
    - Set the entry point to run your Java application
````
FROM maven:3.8.3-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src/ /app/src/
RUN mvn clean package -DskipTests
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/liferay-salestaxreceipt-1.0-SNAPSHOT.jar /app/salestaxreceipt.jar
ENTRYPOINT ["java", "-jar", "salestaxreceipt.jar"]
````

2. Build the Docker image, navigate to your project's root directory and run:

````
docker build -t salestaxreceipt .
````

3. Run the Docker container, execute

````
docker run -it --rm salestaxreceipt
````

### Deploy using Kubernetes

- Push the Docker image to a container registry (e.g., Docker Hub, Google Container Registry, or AWS ECR).
1. Push the Docker image to a container registry:

````
docker login
````

2. Tag the image with your registry username and desired image name

````
docker tag salestaxreceipt your-username/salestaxreceipt:1.0
````

3. Push the image:

````
docker push your-username/salestaxreceipt:1.0
````

- Create a Kubernetes deployment configuration.

````
apiVersion: apps/v1
kind: Deployment
metadata:
  name: salestaxreceipt-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      app: salestaxreceipt
  template:
    metadata:
      labels:
        app: salestaxreceipt
    spec:
      containers:
        - name: salestaxreceipt
          image: your-username/salestaxreceipt:1.0
          ports:
            - containerPort: 8080
````

- Apply the configuration to your Kubernetes cluster

````
kubectl apply -f deployment.yml
````
This will create a deployment with one replica of your application. You can scale the deployment by changing the replicas value in the deployment.yml file and re-applying the configuration. Please note that to apply the configuration, you need to have kubectl installed and configured to communicate with your Kubernetes cluster.
To configure kubectl and to get it to communicate with your Kubernetes cluster. Here:


##### kubeconfig.yaml template

````
apiVersion: v1
clusters:
- cluster:
    certificate-authority-data: <base64-encoded-ca-cert>
    server: https://<cluster-api-server-address>
  name: my-cluster
contexts:
- context:
    cluster: my-cluster
    user: my-user
  name: my-context
current-context: my-context
kind: Config
preferences: {}
users:
- name: my-user
  user:
    client-certificate-data: <base64-encoded-client-cert>
    client-key-data: <base64-encoded-client-key>
````

- Set the KUBECONFIG environment variable to point to the file:

````
export KUBECONFIG=/path/to/your/kubeconfig.yaml
````

Now you can use kubectl to interact with your Kubernetes cluster.

# Data Weaver API  

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)  
[![Spring Boot](https://img.shields.io/badge/SpringBoot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)  

**Data Weaver API** is an API built with **Spring Boot** that provides services for creating and extracting dashboards and reports. It enables developers to integrate data generation and analysis features into their applications easily and efficiently.  

---

## Features  

- **Dashboard Creation**:  
  Allows the creation and management of custom dashboards with user-provided data.  

- **Report Generation**:  
  Supports the generation of reports in various formats, such as JSON, PDF, or Excel.  

- **Data Extraction**:  
  Provides endpoints for querying and extracting organized and filtered data.  

- **Seamless Integration**:  
  RESTful API that simplifies integration with other applications and services.  

---

## Technologies Used  

- **Java 21**  
- **Spring Boot 3.1.5**  
- **Maven** for dependency management  
- **H2 Database** (or another database of choice) for data persistence  
- **Swagger** for interactive API documentation  

---

## Prerequisites  

Make sure you have the following installed before starting:  

- **JDK 21**  
- **Maven 3.x**  
- A tool for HTTP requests (e.g., Postman or cURL)  

---

## Installation and Setup  

1. Clone the repository:  
   ```bash
   git clone https://github.com/luiz-august0/data-weaver-api.git
   cd data-weaver-api
   ```

2. Build the project using Maven:  
   ```bash
   mvn clean install
   ```

3. Run the application:  
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=localhost
   ```

4. Access the API documentation in your browser:  
   ```
   http://localhost:8080/swagger-ui.html
   ```  

---

## Contribution  

Contributions are welcome! To contribute:  

1. Fork the repository.  
2. Create a branch for your feature or fix:  
   ```bash
   git checkout -b feature/your-feature-name
   ```  
3. Make the necessary changes and commit them.  
4. Open a pull request for review.  

---

## License  

This project is licensed under the **MIT License**. See the [LICENSE](./LICENSE) file for more details.  

---

## Contact  

Created and maintained by **[Luiz Augusto](https://github.com/luiz-august0)**.  

If you have any questions or suggestions, feel free to open an *issue* or get in touch.  

---  

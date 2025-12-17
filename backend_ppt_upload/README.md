Backend (Spring Boot + MyBatis) for PPT upload demo.

Steps to run:
1. Create MySQL database `pptdb` and run schema SQL in db/schema.sql.
2. Update spring.datasource username/password in application.yml.
3. Build with: mvn clean package
4. Run jar: java -jar target/ppt-upload-0.0.1-SNAPSHOT.jar

## Taming Thymeleaf

The code in this repo is taken from Wim Deblauwe's book, [Taming Thymeleaf](https://www.wimdeblauwe.com/books/taming-thymeleaf/). It took me about 2 months to work through this doing about four hours per week. This guide sets out how to get the project running locally.

### Software needed
- Docker 
- Java 20 
- Maven

### Steps for Intellij
1. Create a `.env` file in the project root with three variables: 
    - POSTGRES_DATABASE (the db name)
    - POSTGRES_USER
    - POSTGRES_PASSWORD
2. Create an `application-local.properties` file under `src/main/resources` with these variables:
   - spring.datasource.url=jdbc:postgresql://localhost/<POSTGRES_DATABASE> (must match `.env` file)
   -  spring.datasource.username=<POSTGRES_USER> (must match `.env` file)
   -  spring.datasource.password=<POSTGRES_PASSWORD> (must match `.env` file)
   -  spring.datasource.driver-class-name=org.postgresql.Driver
   -  spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   -  spring.jpa.hibernate.ddl-auto=validate
   -  spring.thymeleaf.cache=false
   -  spring.web.resources.chain.cache=false
3. Run `docker compose` in the project root to spin up the database
4. Run the `ThymeWizardApplication` with two configured profiles, `local, init-db`, this will seed the database. You will see a list of created users appear in the Java console.
5. On second and subsequent runs, just use the `local` profile
6. Whilst the application is running navigate to the project root and run `npm run build && npm run watch`.
7. A browser will open at `localhost:3000`, you can log in using any email from the `tt_user` table, the password will be the user's first name. For example, if a user has the email 'calvin.harris@gmail.com' their password will be 'Calvin'.
8. You're in!

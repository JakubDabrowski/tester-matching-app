I. Prepare before starting project
	1. Download and install pgAdmin4 https://www.pgadmin.org/download/
	2. Download and install Postgres 12.1 on port 5433. Download link: https://www.postgresql.org/download/
	3. Create role "tester" with password "testerMatching"
	4. Create database on port 5433 with name "tester" and role "tester"
	5. Download and install maven, npm, Java 8, angular cli
	
II. Fetch project - using Intellij (instruction below) or download ZIP and unpack
	1. Navigate to File -> New -> Project from Version Control -> Git 
	2. In Git Repository URL put value: https://github.com/JakubDabrowski/tester-matching-app
	3. Select Parent Directory
	4. Click Clone
	5. Wait for project indexing to end

III. Run Backend (using Intellij)
	1. Add tester-backend module to the project
		- File -> New -> Module from Existing Sources... 
		- Select project tester-backend folder -> Ok
		- Select Maven -> Next -> Next -> Next -> Check the only import project option -> Next
		- Select Java SDK 1.8 -> Finish
	2. Setup SDK 
		- Go to Project Structure (Ctrl + Shift + Alt + S)
		- In Project tab select SDK 1.8 and Java 8 - Lambdas, type annotations etc.
		- Click Apply -> Ok
	2. Build app using maven (using project terminal)
		cd tester-backend
		mvn clean install
	3. Configure Init Run Configuration
		- Click the Add new Run Configuration button
		- Click + icon and select Spring Boot
		- Name it whatever name you want(for e.g Tester Backend)
		- Set Main class: pl.applause.jakub.dabrowski.tester.matching.backend.TesterBackendApplication
		- Set module: "tester-backend"
		- Set Active Profiles: "init"
		- Click Apply -> Ok 
		- Click Run/Debug button
	The following setup will run the application and init the data from .csv files. 
	4. After initalizing data remember to remove profile "init" and run/debug the application without it!

IV. Run Frontend (using Intellij)
	1. Go to angular frontend folder
		- Open new terminal window
		cd web
	2. Build app using maven
		mvn clean install
	3. Navigate to Angular app directory:
		cd src/main/resources
	4. Run the command to download the dependencies through npm:
		npm install
	5. Start Angular app:
		npm start
		
V. Checking the application:
	1. Go to http://localhost:8002/
	2. Type admin/admin123 to login
	3. There you can search for results in whole table(first input) or select Countries/Devices to filter results.
=== Shogi Project Setup Instructions ===

----------------------------
1. PROJECT DOWNLOAD
----------------------------
Download the source code through the zip file provided. 

After opening the project in Papyrus, ensure the folder structure is preserved:
SE_Team/
  └── Shogi/
      ├── src/
      │   ├── Client/
      │   ├── Database/
      │   ├── GameLogic/
      │   ├── images/
      │   ├── LobbyManagement/
      │   ├── Server/
      │   ├── ShogiTest/
      │   └── UserManagement/
  └── batFiles/
      ├── server.bat
      └── client.bat
  └── DesignDocument/

----------------------------
2. DATABASE SETUP
----------------------------
Before running the server or client, the database must be configured.

STEP 1: Launch your MySQL command line client and log in:
mysql -h localhost -u student -p

STEP 2: Run the SQL setup file:
source [FULL_PATH_TO]\SE_Team\Shogi\src\Database\project.sql

*Replace [FULL_PATH_TO] with the actual path to the file on your system.*

This will create the necessary schema and populate any required tables.

----------------------------
3. RUNNING THE APPLICATION
----------------------------

STEP 1: Start the Server
Double-click server.bat or run it from a terminal:
server.bat

This will launch the Shogi server and wait for client connections.

STEP 2: Start the Client
After the server is running, launch the client by double-clicking client.bat or running:
client.bat

The client will open the GUI where you can create an account or log in to an existing account. Logging in or creating an account will allow you to browse lobbies that have been created already, or create a new one.

----------------------------
4. NOTES
----------------------------
- Ensure MySQL is running before launching the server.
- The server must be started before the client.

----------------------------
5. CONTACT
----------------------------
For issues, contact any member of the team or leave an issue on the GitHub repository.

# ZIO Mpesa: RESTful Web Service

This is a RESTful ZIO Web Service that connects to PostgreSQL database. It integrates with Safaricom M-Pesa Mobile Money Payment Gateway (i.e exposed API endpoints for accessing M-Pesa services by Kenyan Telco called "Safaricom") and enables customers to pay for utilities like water, PayTv, electricity from their phone wallets. The Kenyan Telco "Safaricom" has provided M-Pesa API endpoints for B2C, C2B and B2B (https://developer.safaricom.co.ke/Documentation). Currently this ZIO Web Service supports C2B "Customer to Business", the other features will be added soon.

This RESTful web service using ZIO uses
- [ZIO HTTP](https://dream11.github.io/zio-http/) for the HTTP server
- [ZIO JSON](https://zio.github.io/zio-json/) for the JSON serialization
- [ZIO Logging](https://zio.github.io/zio-logging/) for integrating logging with slf4j
- [ZIO Config](https://zio.github.io/zio-config/) for loading configuration data
- [quill-zio](https://github.com/zio/zio-quill/) for integrating with PostgreSQL database
- [PostgreSQL](https://www.postgresql.org/) for providing a database for application use

## Running The RESTful web service

First, open the console and clone the project using `git` (or you can simply download the project) and then change the directory:

```scala
git clone https://github.com/lastemp/zio-mpesa-restful.git 
cd zio-mpesa-restful
```

```PostgreSQL
Open folder "zio-mpesa-restful\src\main\resources\sql" that contains the SQL files to create the tables in your local PostgreSQL database server.
Please ensure you execute the SQL statements found in the files in your PostgreSQL database server.
```

```Config file
Open file "application.conf" located in path "zio-mpesa-restful\src\main\resources" and then 
change the configurations to match the credentials of your PostgreSQL database server.
```

Once you are inside the project directory, run the application:

```scala
sbt run
```

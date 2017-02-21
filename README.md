# chica

Chica was the name of my dog, she just died, but I'm honoring her with this project which includes a mix of different technologies like Spring MVC, Spring Security, Apache Tiles as view resolver, Spring-JDBC, Derby and Cassandra databases, RabbitMQ with the 6 common use cases and iText 5 where I'm just *testing* how to generate a PDF for a dynamic book where you can customize the main character's look.

For RabbitMQ, it implements the cases listed below, always passing an Object to the queue and serializing it as JSON and getting it back from JSON to object.
1. Simple Queue
2. Work Queue
3. Publish/Subscribe
4. Routing
5. Topics
6. RPC

## Requirements
- Java 8
- Gradle 3
- Cassandra 3 locally in a single node
- Derby 10
- RabbitMQ 3
- iText 5

## Steps

### 1. Create the databases
- Start Derby in server mode
```
connect 'jdbc:derby://localhost:1527//Users/topiltzin/Tools/db/databases/[MY_SCHEMA];create=true;user=[MY_USER];password=[MY_PASSWORD]';
```
Remember also to create a datasource with name __jdbc/taleme__.

- Start Cassandra and create keyspace
```
cqlsh> CREATE KEYSPACE taleme_ks WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};
cqlsh> USE taleme_ks;
```
To enable the app to connect to Cassandra, please check __/src/main/resources/conf/cassandra/cassandra.properties__

### 2. Run DDL

- For Derby run:
derby-chica-ddl.sql

- For Cassandra run:
cassandra-chica-ddl.sql

### 3. Run DML
chica_dml.sql

- For Derby run:
derby-chica-dml.sql

- For Cassandra run:
cassandra-chica-dml.sql


[My site](http://topi.cafeconleche.xyz)

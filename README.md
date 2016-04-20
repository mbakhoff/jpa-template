# JPA and Hibernate ORM

## Object-relational impedance mismatch

*"The object-relational impedance mismatch is a set of conceptual and 
technical difficulties that are often encountered when a relational 
database management system (RDBMS) is being used by a program written 
in an object-oriented programming language or style, particularly when 
objects or class definitions are mapped in a straightforward way to 
database tables or relational schemata."* - Wikipedia

Writing objects to files or databases (and reading them back) is a real pain. 
Storing object into files using DataOutputStream requires lots of custom code to write out each field. 
Similarly, using JDBC databases requires custom code for SQL statements and parameter binding. 
Both of these tasks are very repetitive, tedious and follow simple generic rules, which makes them a good candidate for automation.  
In this practice we'll take a look at Hibernate ORM (object-relational mapper) - a Java library that can automatically store objects into a database and retreive them.

Hibernate has a mostly decent user guide available at 
https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/Hibernate_User_Guide.html

## JPA (Java Persistence API) examples

Hibernate implements JPA, an API for storing, updating, deleting and querying objects. 
Most of the operations are performed through the *javax.persistence.EntityManager* class. 

### Storing an object into the database

    Vet vet = new Vet();
    vet.setFirstName("first name");
    vet.setLastName("last name");
    entityManager.persist(vet);

### Retreiving an object by id
    
    Long id = 42;
    Vet vet = entityManager.find(Vet.class, id);

### Retreiving objects using parametric queries

    Vet vet = entityManager.createQuery("from Vet v where v.id = :id", Vet.class)
        .setParameter("id", id)
        .getSingleResult();

### Updating an object

    Vet vet = entityManager.find(Vet.class, 42);
    vet.addSpecialty(speciality);

## Persistence context

You might have noticed that the *Updating an object* example doesn't call *persist(vet)* in the end. 
This is not a typo - the changes in the Vet object are saved into the database even without calling *persist*. 
How is this possible, you might ask. 

Internally, the EntityManager keeps track of all the objects that it has seen. 
The EntityManager remembers each object that it returns through queries (either *entityManager.find()* or *entityManager.createQuery()*) as well as any objects you pass to it using the *persist()* method.
You can imagine that it an EntityManager contains a field *List\<Object\> managedObjects*. 
When you start work with an empty EntityManager, the list is empty. 
During the work you query and persist objects using the EntityManager and the EntityManager collects references to all such objects. 
Finally when you finish working with the EntityManager, it inspects each object that it is managing and inserts/updates it in the database. 
The objects that the EntityManager is tracking are called **managed entities** and the EntityManager combined with the managed entities is called the **persistence context**. 

## Managing EntityManagers

### Unit of work

Consider an application that uses a single EntityManager and runs for days at a time. 
The application keeps querying and updating different objects and each of these objects is remembered by the EntityManager. 
Given enough time and queries, the EntityManager might load almost all the objects that are stored in the database! 
All these objects would be kept in managed entity list where they would take up memory, even though they might not be needed any time soon. 
Also, if other applications are updating the same database, the objects stored in-memory in the EntityManager would soon be out of sync with the state in the database. 

In practice, applications don't use just a single EntityManager. 
Instead, an application would create a new EntityManager for each request submitted by the user. 
That might mean a single button click by the user, submitting a form, using a mobile app's function etc. 
This way the old EntityManagers would be disposed and the memory they were using could be freed up. 
Also, each new user request would load fresh data from the database. 

### JPA lifecycle

Using JPA in your application usually looks like the following:  

+ Configure the database settings in META-INF/persistence.xml
+ Create a single EntityManagerFactory based on the persistence.xml configuration
+ For each user request, create a new EntityManager using the EntityManagerFactory, do work with the EntityManager and then close the EntityManager   
+ Close the EntityManagerFactory

The persistence.xml configuration file must contain all the information required by Hibernate to connect to the database and find your application's classes.  
The two required properties are the database driver's class name and the database connection url. 

The setup code looks something like this: 

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("appName");
    try {
      while (applicationRunning) {
        // accept the next task
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
          // do the task using entityManager
        } finally {
          entityManager.close();
        }
      }
    } finally {
      entityManagerFactory.close();
    }    

## A word about transactions and syncing with the database

All database operations made with an EntityManager must happen within a transaction.
A transaction is *"a series of data manipulation statements that must either fully complete or fully fail, leaving the database in a consistent state"* (https://stackoverflow.com/a/974604).
Transactions are useful for keeping a sane state in the database in case of exceptions in the application code.

An example to make it clear:

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      // insert A
      // update B
      // update C
      transaction.commit(); // save all changes or nothing at all if something fails
    } catch (Exception e) {
      transaction.rollback(); // discard pending changes and undo what was already changed
      throw e;
    }

Transactions are also important for understanding when the data is actually saved to the database. 
When persisting new objects and updating existing objects, the changes are not immediately sent to the database. 
The performance is much better when all the changes are gathered first and then sent out all at once. 

The data is actually written to the database (flushed) on the following events: 

+ prior to committing a Transaction
+ prior to executing a JPQL query that overlaps with the queued entity actions
+ before executing any native SQL query 
        
## Tradeoffs when using ORMs

If writing SQL queries can be automated with magic libraries, then why isn't everybody doing it? 
As always, magic has it's upsides and downsides. 
Many projects use Hibernate or some other ORM library, but just as many don't use it. 
Some reasons for not using an ORM: 

+ much more difficult to initially learn
+ lots of magic; when something goes wrong, good luck debugging it
+ endless number of quirks, pitfalls and gotchas
+ doesn't fully hide SQL - still need to write JPQL for the queries 
+ doesn't make the database disappear - you still need to understand how SQL and ORM work to use them efficiently; you just need to write less queries manually 

# Practice tasks

Complete the following tasks as an excercise. 
When something is unspecified then do what makes most sense.    
When confused, ask a lot of questions. 

+ Create an action for adding an owner
+ Create an action for adding a pet to an owner
+ Annotate the Pet class so that the pets are stored in a database table called "all_pets" and the pet name is stored in a database column called "pet_name". See the @Table and @Column annotations.  
+ Create an action for viewing all pets
+ Create an action for viewing pets of the specified owner
+ Create an action for editing a pet's name
+ Create a new class Visit. A Visit contains a Pet, a Vet and the description of the visit. Pets and vets can have multiple visits. 
+ Create an action for adding visits
+ Create an action for viewing all visits of the specified pet
+ Create an action for deleting the specified Pet

*Sample application inspired by https://github.com/spring-projects/spring-petclinic*

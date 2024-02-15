# Calendar Service
- A breif documentation on endpoints is given in ``endpoint_doc.md``
- Java version used: 17 and mvn version used: 3.9.6
- The database used is a small postgres cloud server

## Run through ``mvn``
- Change the current directory to api
```
cd ./api
```
- Build and run the jar
```
mvn install; java -jar ./target/api-0.0.1-SNAPSHOT.jar
```

## Things to add (Higher Priority)
Due to time limitation, completeness was focused. But here are few things I'd change/build if futher extended

- modifications and deletions to events/users are not implemented.
- better code management at repository layer, especially managing custom queries better.
- improve conflicting events api into sending complete data about the events, instead of just IDs.
- service layer between repository and controller layer.
- pagination wherever applicable.

## Things to add (Lower Priority)
- usage of more DTOs and not using Models to directly exchange the data with the caller.
- exception handling and better response messages and bulk APIs.
- create docker image for shipping the code instead of jar files.
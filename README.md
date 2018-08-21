europeana-client
================

This project implements a java client for the Europeana Search Api.

The project was forked from Europeana4J (http://code.google.com/p/europeana4j/) mavenized and refactored.

====== integration in Eclipse workspace ======

git clone ....

File => Import => Existing Maven Projects ...

update compilation level and the java version in the facets to 1.6 (through Window/Project => Preferences => Java Compiler) 

copy src/main/resources/europeana-client.properties.template src/main/resources/europeana-client.properties

== set the values in the europeana-client.properties file

copy src/main/resources/log4j.xml.template src/main/resources/log4j.xml

== make sure you never commit the europeana-client.properties and log4j.xml files into repository

Run unit tests: SimpleSearchTest, ComplexSearchTest, ThumbnailsForCollectionAccessorTest

== Check the code in the *Test classes to see  

## License

Licensed under the EUPL V.1.1.

For full details, see [LICENSE.md](LICENSE.md).

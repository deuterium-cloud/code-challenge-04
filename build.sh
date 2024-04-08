#!/bin/bash

mvn -f account/pom.xml clean &&
mvn -f account/pom.xml package -DskipTests &&

mvn -f character/pom.xml clean &&
mvn -f character/pom.xml package -DskipTests &&

mvn -f combat/pom.xml clean &&
mvn -f combat/pom.xml package -DskipTests
.DEFAULT_GOAL := build

build:
	mvn package -DskipTests

test:
	mvn test

.PHONY: build
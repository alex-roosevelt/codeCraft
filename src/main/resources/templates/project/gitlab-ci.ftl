stages:
- build
- test
- deploy

variables:
MAVEN_OPTS: "-Duser.home=/build/cache"
MAVEN_CACHE_DIR: "/build/cache"

before_script:
- echo "Using Java 17"
- apt-get update && apt-get install -y openjdk-17-jdk

build:
stage: build
script:
- mvn clean install -DskipTests

test:
stage: test
script:
- mvn test

deploy:
stage: deploy
script:
- mvn spring-boot:run

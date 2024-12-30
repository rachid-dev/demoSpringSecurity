#!/bin/bash

# Check if the user has provided an environment argument
if [ -z "$1" ]; then
  echo "Usage: $0 <environment>"
  echo "Available environments: dev, staging, prod"
  exit 1
fi

# Set the environment name
ENVIRONMENT=$1

# Create Procfile
echo "Creating Procfile"
touch Procfile

# Use Elastic Beanstalk CLI to switch environments
case "$ENVIRONMENT" in
  dev)
    echo "Defining the process that will be run in demoSpring-dev"
    echo "web: java -jar demoSpringSecurity-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev" >> Procfile
    echo "Building my app for dev environment: demoSpring-dev"
    mvn clean package -Dspring.profiles.active=dev
    echo "Switching to Elastic Beanstalk environment: demoSpring-dev"
    eb use demoSpring-dev
    ;;
  staging)
    echo "Defining the process that will be run in demoSpring-staging"
    echo "web: java -jar demoSpringSecurity-0.0.1-SNAPSHOT.jar --spring.profiles.active=staging" >> Procfile
    echo "Building my app for dev environment: demoSpring-staging"
    mvn clean package -Dspring.profiles.active=staging
    echo "Switching to Elastic Beanstalk environment: demoSpring-staging"
    eb use demoSpring-staging
    ;;
  prod)
    echo "Defining the process that will be run in demoSpring-prod"
    echo "web: java -jar demoSpringSecurity-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod" >> Procfile
    echo "Building my app for dev environment: demoSpring-prod"
    mvn clean package -Dspring.profiles.active=prod
    echo "Switching to Elastic Beanstalk environment: demoSpring-prod"
    eb use demoSpring-prod
    ;;
  *)
    echo "Unknown environment: $ENVIRONMENT"
    echo "Available environments: dev, staging, prod"
    rm Procfile
    echo "Procfile Deleted"
    exit 1
    ;;
esac

# Copy artifact to root folder
echo "Coping my app archive to the root directory"
cp target/demoSpringSecurity-0.0.1-SNAPSHOT.jar ./

# Add files to git staging
echo "Add artifact to staging(git)"
git add demoSpringSecurity-0.0.1-SNAPSHOT.jar
echo "Add Procfile to staging(git)"
git add Procfile

# Deploy to the selected environment
echo "Deploying to $ENVIRONMENT environment..."
eb deploy --staged

# clean up 
echo "Deleting artifact..."
rm demoSpringSecurity-0.0.1-SNAPSHOT.jar
echo "Deleting Procfile..."
rm Procfile
echo "Cleaning up git index"
git restore --staged demoSpringSecurity-0.0.1-SNAPSHOT.jar
git restore --staged Procfile
echo "Done!"

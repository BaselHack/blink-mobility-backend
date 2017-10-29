# Instructions
1. Compile the project
````
gradle build
````
2. Start a mongo database
````
docker run -d -p 27017:27017 mongo
````
3. Start the service (will listen to 8080 by default)
````
gradle bootRun
````
4. (optional) Expose the service publicly using ngrok (if running on your laptop)
````
ngrok http  8080
````
  

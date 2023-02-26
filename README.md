# forecast
This is my first time to do android development so many things could be optimized 

The Application implement following functionalities:
* Retrieve weather from openWeather API for user's current location and the following citys: New York, Singapore, Mumbai, Delhi, Sydney, Melbourne
* Store the above information in App-specific storage
* When no network avaialbe, read stored information from the storage and indicates that current network status
* Refresh the view once the user connects to the network
* When the use has network connection, refreshes the data every 10 minutes


The whole project is done based on MVC pattern(mostly):
* AppActivity is the only activity runs in the application. It has a timer task to call various controllers to retrieve GPS data, request weather data from openWeather, and then update these information to Viewer.
* Model package contains various model class: CityWeather stores all weather information of a city, which is retrieved from openWeather API. Ohter model class are sub-model of the CityWeather.
* Controller package contains various controller classes:  
GPScontroller is used to get user current location.  
The GPScontroller will call RetrieveDataFromAPIController to retrieve weahter information of the city or user current location from openWeather API.  
WeatherAPIController is used in RetrieveDataFromAPIController. It will send HTTP request to openWeather API and get the jsonobject back.   
After the RetrieveDataFromAPIController retrieves weather information, it will call IOController to write the result to Internal storage. 
The ViewController will used to update the APP view. It will be called when the application starts to load stored data from the storage, and when the new weather information is retrieved from API  
* Viewer package contains view classes. The overall view reuses the card view.

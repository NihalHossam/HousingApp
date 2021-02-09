# Housing App (Android)

This app was done using the following:

- Kotlin
- Kotlin Coroutines
- View Binding and Data Binding
- MVVM design pattern
- ViewModel and LiveData
- Room (for local database)
- Dagger Hilt (for dependency injection)
- Retrofit
- Navigation 

Unit and Instrumentation testings were done using JUnit, Mockito and Espresso.

## App flow

- Home Fragment: the user has to be connected to the internet so that the data from the API can be accessed. The home fragment shows a list of houses received from the API’s response. Upon click, the Details Fragment is shown which displays more information about the house. If there's no internet connection, an error will be displayed.

- Details Fragment: shows more information about the house that was clicked. A map view is also shown which shows the location of the house. Upon the click on the map, Google Maps app will open to start navigation to this house. A save button is also there so the user can save houses in the Saved Fragment.

- Saved Fragment: This fragment contains the houses which the user saved. The user doesn't need internet connection to display these houses and their information as these houses are saved into a local database using Room. 

- Search Fragment: The user can search for a house using the zip code or city.

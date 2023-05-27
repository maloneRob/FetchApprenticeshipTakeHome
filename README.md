# FetchApprenticeshipTakeHome
Developed in Java on the Android studio platform using Android API level 33 tested using a Pixel 5 AVD
Tested using JUNIT4 unit tests

Android application written in Java for Fetch Android apprenticeship take home test

Display this list of items to the user based on the following requirements:

Display all the items grouped by "listId"
Sort the results first by "listId" then by "name" when displaying.
Filter out any items where "name" is blank or null.
The final result should be displayed to the user in an easy-to-read list.

Includes a single activity, when launched, converts the included json located in the assets folder to a string in the JsonParser class.
The string is then parsed, and the information is store in the data class "Data" filtering out blank and null names.
The resulting list of Data objects is then sorted by "listId" and then "name" and displayed in a ListView using a custom Adapter "ListAdapter".

Included are unit tests in the ExampleInstrumentedTest class



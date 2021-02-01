# Singapore Live Traffic Demo

This app follows MVVM architecture.

Implemented Unit Test Cases For ViewModel


-------------------------
I have used following tools


 - Timber ⏺ (For Log purpose)
 - Coroutines 🚀 ( For Async Operation)
 - DaggerHilt 💉 ( Dependancy Injection)
 - Retrofit 📲 (Network Call)
 - LoggingInterceptor ⏺︎ (Api Logs)
 - NetworkResponseAdapter (Handle Network Response)
 - Truth (performing assertions in tests)
 - MockK (Mocking objects)
-------------------------
**Video Link**
[![Everything Is AWESOME](https://github.com/panchalamitr/SG_Live_Traffic_Demo/blob/main/screenshots/YoutubeScreen.png)](https://youtu.be/au13nsR-yiQ "Everything Is AWESOME")

-------------------------

**Screenshots**
![News App Screenshot](https://github.com/panchalamitr/SG_Live_Traffic_Demo/blob/main/screenshots/screens.jpg)

-------------------------

**Description**

1) Map Screen
Fetch data from the https://data.gov.sg/ and shows camera location on Singapore Map. </br>
This api is being called every minute to get updated image. </br>
Instead of removing and adding marker every minute, I have just updated it's camera Uri (which is updating every minute)



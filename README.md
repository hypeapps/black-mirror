# Black Mirror
A voice controlled smart mirror powered by Raspberry Pi3B+ and AndroidThings. Mirror is capable to display or hide widgets such as weather, news, time, calendar when the correct voice command is given.

## Commands
At the moment Black Mirror supporting commands in polish language only. If you would like to change the language, see the setup section below. Mirror listen the command only after "wakeup mirror" keyword spotted, this action will be indicated by mic animation. 

1. Showing/hiding weather widget:
> "Pokaż pogodę dla miasta (Lokalizacja)"</br>
> "Zamknij widok pogody"
2. Showing/hiding news widget.
> "Pokaż wiadomości ze świata"</br>
> "Pokaz wiadomości z Polsatu"</br>
> "Pokaż wiadomości z TVNu"</br>
> "Zamknij widok wiadomości"
3. Showing/hiding time widget.
> "Pokaż czas dla kraju (Lokalizacja)"</br>
> "Zamknij/Ukryj zegar"
4. Showing/hiding/changing calendar month.
> "Pokaż kalendarz"</br>
> "Pokaż kolejny miesiąc"</br>
> "Pokaż poprzedni miesiąc"</br>
> "Zamknij widok kalendarza"

## Setup
#### API keys
Paste your api keys in <a href="https://github.com/hypeapps/black-mirror/blob/master/gradle.properties">gradle.properties</a>. You need to register in OpenWeatherMap, TimeZoneDb, GoogleGeoApi to obtain api keys. It's completly free.
#### Google Cloud Speech Cloud Credentials
To work with google speech recognition you need credentials.json file placed in /res/raw directory. 
More info at: <a href="https://github.com/GoogleCloudPlatform/android-docs-samples/tree/master/speech/Speech">https://github.com/GoogleCloudPlatform/android-docs-samples/tree/master/speech/Speech</a>
#### Keyword changing
If you want to change default keyword "wakeup mirror", you should change line in <a href="https://github.com/hypeapps/black-mirror/blob/master/app/src/main/java/pl/hypeapps/blackmirror/speechrecognition/sphinx/PocketSphinx.java">PocketSphinx.java (line:24)</a>
```java 
private static final String ACTIVATION_KEYPHRASE = "wakeup mirror";
```
#### Language change
1. Set your language code in <a href="https://github.com/hypeapps/black-mirror/blob/master/app/src/main/java/pl/hypeapps/blackmirror/speechrecognition/googlespeechapi/SpeechService.java">SpeechService.java (line: 262)</a>
```java 
 mRequestObserver.onNext(StreamingRecognizeRequest.newBuilder()
                .setStreamingConfig(StreamingRecognitionConfig.newBuilder()
                        .setConfig(RecognitionConfig.newBuilder()
                                // Change this line
                                .setLanguageCode("pl-PL")
                                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                                .setSampleRateHertz(sampleRate)
                                .build())
```
2. Change all vocabularies in <a href="https://github.com/hypeapps/black-mirror/blob/master/app/src/main/java/pl/hypeapps/blackmirror/speechrecognition/TextCommandInterpreter.java">TextCommandInterpreter</a>. For example:
```java 
private final ArrayList<String> countryVocabulary = new ArrayList<>(Arrays.asList("kraj", "kraju", "krajowi", "krajom"));
```
change to:
```java
private final ArrayList<String> countryVocabulary = new ArrayList<>(Arrays.asList("country", "countries", "land", "state"));
```
#### Run on android smartphone
Comment or delete this in <a href="">AndroidManifest.xml</a>
```xml
<intent-filter>
     <action android:name="android.intent.action.MAIN"/>
     <!-- Comment this <category android:name="android.intent.category.IOT_LAUNCHER"/> -->
     <category android:name="android.intent.category.DEFAULT"/>
</intent-filter>
```
#### Permissions
You need to remember to grant permission via adb or android settings for:
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
```
## Pictures
<img src="https://github.com/hypeapps/black-mirror/blob/master/img/mirror.jpg?raw=true" alt="Welcome screen" height="420"/></a>
<img src="https://github.com/hypeapps/black-mirror/blob/master/img/img2.jpg?raw=true" alt="Welcome screen" height="420"/></a>
<img src="https://github.com/hypeapps/black-mirror/blob/master/img/img3.jpg?raw=true" alt="Welcome screen" height="420"/></a>
## Video
<a href="https://youtu.be/viZlAA0J6LI">YouTube video</a>
## License MIT
>MIT License

>Copyright (c) 2017 Przemek 

>Permission is hereby granted, free of charge, to any person obtaining a copy
>of this software and associated documentation files (the "Software"), to deal
>in the Software without restriction, including without limitation the rights
>to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
>copies of the Software, and to permit persons to whom the Software is
>furnished to do so, subject to the following conditions:

>The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

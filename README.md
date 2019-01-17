# Cashback-Explorer :moneybag::mag:

Simple cashback explorer using Google maps. Utilized RxJava, Retrofit, Dagger2 along with MVVM design pattern. 

__1. LoginScreen (LoginActivity)__

![alt text](https://github.com/wasabi-lee/Cashback-Explorer/blob/master/screenshots/resized_3.png?raw=true)


__2. ExplorerScreen (MainActivity)__

![alt text](https://github.com/wasabi-lee/Cashback-Explorer/blob/master/screenshots/resized_2.png?raw=true)

![alt text](https://github.com/wasabi-lee/Cashback-Explorer/blob/master/screenshots/resized_1.png?raw=true)


## Getting Started

__1. Make sure that 'Google Play services' is installed.__

  * In Android Studio, go to __SDK Manager -> SDK Tools -> Make sure 'Google Play services' is checked__
 
  * In the AVD Manager, your emulator should show '(Google APIs)' next to the target field. __(i.e. Android 6.0(Google APIs))__  

__2. If the emulator doesn't show the map try the following after checking the internet connection of the emulator :__

 * Option 1: If you are under x86_64 Mac OS, use x86_64 version of emulator image instead of universal x86. 
 
 * Option 2: Create an emulator and change Emulated Performace -> Graphics setting into __Hardware - GLES 2.0__ 
 
 * Option 3: Try disabling hardware acceleration: Add the attribute to the <application> tag of the AndroidManifest.xml
 ```xml
 <application android:hardwareAccelerated="false" ...>
 ```
 
 * Fast & easy way: Use a real device 
